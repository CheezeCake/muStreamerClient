package emmanuelnicolet.mustreamerclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;
import Player.MusicServerInfo;


public class MainActivity extends ActionBarActivity
{
	public final static String SEARCH_STRING = "emmanuelnicolet.mustreamerclient.SEARCH_STRING";
	public final static String SEARCH_TYPE = "emmanuelnicolet.mustreamerclient.SEARCH_TYPE";

	public final static String MEDIA = "emmanuelnicolet.musicstreamerclient.MEDIA";
	public final static int CHOOSE_FILE_REQUEST = 1;
	public final static int SETTINGS_ACTIVITY = 2;

	private static String metaServerEndpointStr = null;

	private String chosenFile = null;
	private View addDialogView = null;

	public static String getMetaServerEndpointStr()
	{
		return metaServerEndpointStr;
	}

	private static void setMetaServerEndpointStr()
	{
		metaServerEndpointStr = "MetaServer:default -h " + Settings.metaServerHostname + " -p " + Settings.metaServerPort;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (IceData.iceCommunicator == null)
			IceData.iceCommunicator = Ice.Util.initialize(new String[] { "" });

		loadSettings();
	}

	private void loadSettings()
	{
		SharedPreferences settings = getSharedPreferences(SettingsActivity.PREFERENCES_NAME, 0);

		Settings.metaServerHostname = settings
				.getString(SettingsActivity.PREFERENCES_METASERVER_HOSTNAME, "");
		Settings.metaServerPort = settings
				.getString(SettingsActivity.PREFERENCES_METASERVER_PORT, "");

		Settings.speechRecognitionSystem = SpeechRecognitionFactory.System.get(settings
				.getInt(SettingsActivity.PREFERENCES_SPEECH_RECOGNITION, SpeechRecognitionFactory.System.POCKETSPHINX
						.getCode()));

		Settings.pocketSphinxHostname = settings
				.getString(SettingsActivity.PREFERENCES_POCKETSPHINX_HOSTNAME, "");
		Settings.pocketSphinxPort = settings
				.getString(SettingsActivity.PREFERENCES_POCKETSPHINX_PORT, "");

		Settings.speeralHostname = settings
				.getString(SettingsActivity.PREFERENCES_SPEERAL_HOSTNAME, "");
		Settings.speeralPort = settings.getString(SettingsActivity.PREFERENCES_SPEERAL_PORT, "");

		Settings.commmandParserWebServiceURL = settings
				.getString(SettingsActivity.PREFERENCES_COMMAND_PARSER_WEB_SERVICE_URL, "");

		setMetaServerEndpointStr();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, SETTINGS_ACTIVITY);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		if (IceData.iceCommunicator != null) {
			try {
				IceData.iceCommunicator.destroy();
			}
			catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public void search(View v)
	{
		CheckBox artist = (CheckBox)findViewById(R.id.artist);
		boolean byArtist = artist.isChecked();
		CheckBox title = (CheckBox)findViewById(R.id.title);
		boolean byTitle = title.isChecked();
		EditText searchText = (EditText)findViewById(R.id.searchText);
		String text = searchText.getText().toString();

		if (!text.isEmpty() && (byArtist || byTitle)) {
			String searchType;

			if (byArtist) {
				if (byTitle)
					searchType = "everything";
				else
					searchType = "artist";
			}
			else {
				searchType = "title";
			}

			Intent intent = new Intent(this, SearchResults.class);
			intent.putExtra(SEARCH_STRING, text);
			intent.putExtra(SEARCH_TYPE, searchType);
			startActivity(intent);
		}
	}

	public void listSongs(View v)
	{
		Intent intent = new Intent(this, ListSongsResults.class);
		startActivity(intent);
	}

	public void add(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		final View v = inflater.inflate(R.layout.add_song_dialog, null);
		addDialogView = v;

		try {
			Ice.ObjectPrx base = IceData.iceCommunicator.stringToProxy(metaServerEndpointStr);
			IMetaServerPrx srv = IMetaServerPrxHelper.checkedCast(base);
			if (srv == null)
				throw new Exception("Invalid proxy");

			final MusicServerInfo[] serversInfo = srv.listMusicServers();
			String[] servers = new String[serversInfo.length];
			for (int i = 0; i < serversInfo.length; i++)
				servers[i] = serversInfo[i].hostname + ":" + serversInfo[i].listeningPort;


			final Spinner s = (Spinner)v.findViewById(R.id.server_spinner);
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, servers);
			s.setAdapter(adapter);

			builder.setView(v).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int id)
				{
					final String srvEndpoint = serversInfo[s.getSelectedItemPosition()].endpointStr;
					final String artist = ((TextView)v.findViewById(R.id.artist)).getText()
							.toString();
					final String title = ((TextView)v.findViewById(R.id.title)).getText()
							.toString();

					if (!srvEndpoint.isEmpty() && !artist.isEmpty() && !title.isEmpty() &&
							chosenFile != null) {
						new FileUploadTask()
						{
							@Override
							protected Context getContext()
							{
								return MainActivity.this;
							}
						}.execute(chosenFile, artist, title, srvEndpoint);
					}
				}

			}).setNegativeButton(R.string.cancel, null)
					.setOnDismissListener(new DialogInterface.OnDismissListener()
					{
						public void onDismiss(DialogInterface dialog)
						{
							addDialogView = null;
						}
					}).show();
		}
		catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	public void chooseFile(View v)
	{
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY
				.equals(state)) {

			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, CHOOSE_FILE_REQUEST);
		}
		else {
			Toast.makeText(this, "Error: cannot read on external storage", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK) {
			if (requestCode == CHOOSE_FILE_REQUEST) {
				android.net.Uri uri = data.getData();
				chosenFile = uri.getPath();
				Log.d("filechooser", "file: " + chosenFile);
				TextView fileNameText = (TextView)addDialogView.findViewById(R.id.File_name);
				fileNameText.setText(new File(chosenFile).getName());
			}
			else if (requestCode == SETTINGS_ACTIVITY) {
				loadSettings(); // reload settings
			}
		}
	}

	public void delete(View v)
	{
		Intent intent = new Intent(this, DeleteListActivity.class);
		startActivity(intent);
	}

	public void talk(View v)
	{
		if (AudioRecorder.isRecording()) {
			Log.d("MainActivity.talk", "stop recording");
			AudioRecorder.stopRecording();

			SpeechRecognition sr = SpeechRecognitionFactory
					.create(Settings.speechRecognitionSystem, MainActivity.this);

			if (sr != null)
				sr.execute(AudioRecorder.getAudioData());

			AudioRecorder.release();
		}
		else {
			Log.d("MainActivity.talk", "start recording");
			AudioRecorder.startRecording();
		}
	}
}