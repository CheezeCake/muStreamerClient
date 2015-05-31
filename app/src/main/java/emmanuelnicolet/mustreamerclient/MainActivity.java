package emmanuelnicolet.mustreamerclient;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Ice.Communicator;
import Ice.Current;
import Ice.Identity;
import Ice.InitializationData;
import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;
import Player.MusicServerInfo;
import Player.Song;
import Player._ISongMonitorDisp;
import emmanuelnicolet.mustreamerclient.filechooser.FileChooser;


public class MainActivity extends ActionBarActivity
{
	public final static String SEARCH_STRING = "emmanuelnicolet.mustreamerclient.SEARCH_STRING";
	public final static String SEARCH_TYPE = "emmanuelnicolet.mustreamerclient.SEARCH_TYPE";

	public final static String MEDIA = "emmanuelnicolet.musicstreamerclient.MEDIA";
	public final static int CHOOSE_FILE_REQUEST = 1;
	public final static int SETTINGS_ACTIVITY = 2;

	private static String metaServerEndpointStr = null;

	private SpeechRecognizer recognizer = null;

	private String chosenFile = null;
	private View addDialogView = null;

	public static String getMetaServerEndpointStr()
	{
		return metaServerEndpointStr;
	}

	private static void setMetaServerEndpointStr()
	{
		metaServerEndpointStr = "MetaServer:default -h " + Settings.metaServerHostname + " -p " + Settings.metaServerPort;
		Log.d("MainActivity", "setMetaSeverEndpointStr = " + metaServerEndpointStr);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loadSettings();

		if (IceData.iceCommunicator == null)
			IceData.iceCommunicator = Ice.Util.initialize();
	}

	private void unsubscribe()
	{
		if (IceData.topic != null && IceData.songMonitorProxy != null) {
			IceData.topic.unsubscribe(IceData.songMonitorProxy);
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					Toast.makeText(MainActivity.this, "Successfully unsubscribed to \"SongEvents\"", Toast.LENGTH_SHORT)
							.show();
				}
			});
		}
	}

	private void initIceStorm()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try {
					unsubscribe();

					InitializationData initData = new InitializationData();
					initData.properties = Ice.Util.createProperties();
					initData.properties
							.setProperty("Ice.Default.Router", "Glacier2/router:tcp -h " + Settings.glacier2Hostname + " -p " + Settings.glacier2Port);
					initData.properties.setProperty("Ice.ACM.Client", "0");
					initData.properties.setProperty("Ice.RetryIntervals", "-1");
					initData.properties
							.setProperty("CallbackAdapter.Router", "Glacier2/router:tcp -h " + Settings.glacier2Hostname + " -p " + Settings.glacier2Port);

					Communicator communicator = Ice.Util.initialize(initData);

					Ice.RouterPrx defaultRouter = communicator.getDefaultRouter();
					Glacier2.RouterPrx router = Glacier2.RouterPrxHelper.checkedCast(defaultRouter);
					router.createSession(Settings.glacier2User, Settings.glacier2Password);

					Ice.ObjectPrx obj = communicator
							.stringToProxy("IceStorm/TopicManager:tcp -h " + Settings.iceStormHostname + " -p " + Settings.iceStormPort);
					IceStorm.TopicManagerPrx topicManager = IceStorm.TopicManagerPrxHelper
							.checkedCast(obj);
					Ice.ObjectAdapter adapter = communicator
							.createObjectAdapterWithRouter("SongMonitorAdapter", router);
					SongMonitor songMonitor = new SongMonitor();
					IceData.songMonitorProxy = adapter
							.add(songMonitor, new Identity("default", router
									.getCategoryForClient())).ice_twoway();
					adapter.activate();

					IceData.topic = topicManager.retrieve("SongEvents");
					Map<String, String> qos = new HashMap<>();
					qos.put("reliability", "ordered");
					IceData.topic.subscribeAndGetPublisher(qos, IceData.songMonitorProxy);

					MainActivity.this.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							Toast.makeText(MainActivity.this, "Icestorm : monitoring topic \"SongEvents\"", Toast.LENGTH_LONG)
									.show();
						}
					});
				}
				catch (final Exception e) {
					MainActivity.this.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							Toast.makeText(MainActivity.this, e.getClass().getName() + " : " + e
									.toString(), Toast.LENGTH_LONG).show();
						}
					});

					Log.e("IceStorm", e.getClass().getName() + " : " + e.toString());
					IceData.songMonitorProxy = null;
					IceData.topic = null;
				}
			}
		}).start();
	}

	private void loadSettings()
	{
		SharedPreferences settings = getSharedPreferences(SettingsActivity.PREFERENCES_NAME, 0);

		String oldMetaServerHostname = Settings.metaServerHostname;
		String oldMetaServerPort = Settings.metaServerPort;

		Settings.metaServerHostname = settings
				.getString(SettingsActivity.PREFERENCES_METASERVER_HOSTNAME, "");
		Settings.metaServerPort = settings
				.getString(SettingsActivity.PREFERENCES_METASERVER_PORT, "");

		if (!Settings.metaServerHostname.equals(oldMetaServerHostname) || !Settings.metaServerPort
				.equals(oldMetaServerPort))
			setMetaServerEndpointStr();


		// Glacier2, IceStorm
		String[] oldGlacier2Settings = { Settings.glacier2Hostname, Settings.glacier2Port, Settings.glacier2User, Settings.glacier2Password };

		Settings.glacier2Hostname = settings
				.getString(SettingsActivity.PREFERENCES_GLACIER2_HOSTNAME, "");
		Settings.glacier2Port = settings.getString(SettingsActivity.PREFERENCES_GLACIER2_PORT, "");
		Settings.glacier2User = settings.getString(SettingsActivity.PREFERENCES_GLACIER2_USER, "");
		Settings.glacier2Password = settings
				.getString(SettingsActivity.PREFERENCES_GLACIER2_PASSWORD, "");

		String[] glacier2Settings = { Settings.glacier2Hostname, Settings.glacier2Port, Settings.glacier2User, Settings.glacier2Password };

		String oldIceStormHostname = Settings.iceStormHostname;
		String oldIceStormPort = Settings.iceStormPort;

		Settings.iceStormHostname = settings
				.getString(SettingsActivity.PREFERENCES_ICESTORM_HOSTNAME, "");
		Settings.iceStormPort = settings.getString(SettingsActivity.PREFERENCES_ICESTORM_PORT, "");

		boolean iceStormReInit = false;
		for (int i = 0; i < glacier2Settings.length && !iceStormReInit; i++) {
			if (!glacier2Settings[i].equals(oldGlacier2Settings[i])) {
				initIceStorm();
				iceStormReInit = true;
			}
		}

		if (!iceStormReInit && (!oldIceStormHostname
				.equals(Settings.iceStormHostname) || !oldIceStormPort
				.equals(Settings.iceStormPort))) {
			initIceStorm();
			iceStormReInit = true;
		}

		if (!iceStormReInit && (IceData.songMonitorProxy == null || IceData.topic == null))
			initIceStorm();
		// Glacier2, IceStorm


		Settings.speechRecognitionSystem = SpeechRecognitionFactory.System.get(settings
				.getInt(SettingsActivity.PREFERENCES_SPEECH_RECOGNITION, SpeechRecognitionFactory.System.ANDROID
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
		else if (id == R.id.action_icestorm_reconnect && (IceData.topic == null || IceData.songMonitorProxy == null)) {
			initIceStorm();
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

		unsubscribe();
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
		realAdd(view, "", "");
	}

	public void realAdd(View view, String artist, String title)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		final View v = inflater.inflate(R.layout.add_song_dialog, null);
		addDialogView = v;

		final TextView artistTextView = (TextView)v.findViewById(R.id.artist);
		artistTextView.setText(artist);
		final TextView titleTextView = (TextView)v.findViewById(R.id.title);
		titleTextView.setText(title);

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
					final String artist = artistTextView.getText().toString();
					final String title = titleTextView.getText().toString();

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
			Intent intent = new Intent(MainActivity.this, FileChooser.class);
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
				chosenFile = data.getStringExtra(FileChooser.FILE_SELECTED);
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
		ImageButton b = (ImageButton)v;

		if (AudioRecorder.isRecording()) {
			Log.d("MainActivity.talk", "stop recording");
			b.setImageResource(android.R.drawable.ic_btn_speak_now);
			AudioRecorder.stopRecording();

			SpeechRecognition sr = SpeechRecognitionFactory
					.create(Settings.speechRecognitionSystem, MainActivity.this, new SpeechRecognition.SpeechRecognitionListener()
					{
						@Override
						public void onResults(String result)
						{
							MainActivity.this.handleSpeechRecognitionResult(result);
						}
					});

			if (sr != null)
				sr.execute(AudioRecorder.getAudioData());

			AudioRecorder.release();
		}
		else if (Settings.speechRecognitionSystem != SpeechRecognitionFactory.System.ANDROID) {
			Log.d("MainActivity.talk", "start recording");
			b.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
			AudioRecorder.startRecording();
		}
		else {
			createRecognizer(v);
			Intent intent = new Intent();
			recognizer.startListening(intent);
			v.setEnabled(false);
		}
	}

	public void createRecognizer(final View v)
	{
		if (recognizer == null) {
			recognizer = SpeechRecognizer.createSpeechRecognizer(this);
			recognizer.setRecognitionListener(new RecognitionListener()
			{
				@Override
				public void onReadyForSpeech(Bundle params)
				{
					Log.d("android speech", "ready for speech");
				}

				@Override
				public void onBeginningOfSpeech()
				{
					Log.d("android speech", "on beginning of speech");
				}

				@Override
				public void onRmsChanged(float rmsdB)
				{
				}

				@Override
				public void onBufferReceived(byte[] buffer)
				{
				}

				@Override
				public void onEndOfSpeech()
				{
					Log.d("android speech", "on end of speech");
					MainActivity.this.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							v.setEnabled(true);
						}
					});
				}

				@Override
				public void onError(int error)
				{
					Log.d("android speech", "on error");
					final String message;

					switch (error) {
						case SpeechRecognizer.ERROR_AUDIO:
							message = "Audio recording error.";
							break;
						case SpeechRecognizer.ERROR_CLIENT:
							message = "Other client side errors.";
							break;
						case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
							message = "Insufficient permissions";
							break;
						case SpeechRecognizer.ERROR_NETWORK:
							message = "Other network related errors.";
							break;
						case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
							message = "Network operation timed out.";
							break;
						case SpeechRecognizer.ERROR_NO_MATCH:
							message = "No recognition result matched.";
							break;
						case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
							message = "RecognitionService busy.";
							break;
						case SpeechRecognizer.ERROR_SERVER:
							message = "Server sends error status.";
							break;
						case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
							message = "No speech input";
							break;
						default:
							message = "Error";
					}

					MainActivity.this.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
						}
					});
				}

				@Override
				public void onResults(Bundle results)
				{
					Log.d("android speech", "on results");
					ArrayList<String> r = results
							.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
					if (r != null && r.size() > 0) {
						String result = r.get(0);
						Log.d("speech", "result = " + result);
						MainActivity.this.handleSpeechRecognitionResult(result);
					}
				}

				@Override
				public void onPartialResults(Bundle partialResults)
				{
				}

				@Override
				public void onEvent(int eventType, Bundle params)
				{
				}
			});
		}
	}

	public void handleSpeechRecognitionResult(String result)
	{
		Log.d("MainActivity", "handleSpeechRecognitionResults");

		if (result == null || result.length() == 0) {
			Toast.makeText(this, "The speech recognition system returned an empty string !\nTry again.", Toast.LENGTH_LONG)
					.show();
		}
		else {
			Toast.makeText(this, "Recognized :\n" + result, Toast.LENGTH_LONG).show();

			new CommandParserClient(this, new CommandParserClient.CommandParserClientResultListener()
			{
				@Override
				public void add(String artist, String title)
				{
					realAdd(findViewById(R.id.add), artist, title);
				}

				@Override
				public void search(String search, String searchBy)
				{
					boolean artist = true;
					boolean title = true;

					if (searchBy.equals("artist"))
						title = false;
					else if (searchBy.equals("title"))
						artist = false;

					((CheckBox)findViewById(R.id.artist)).setChecked(artist);
					((CheckBox)findViewById(R.id.title)).setChecked(title);

					((TextView)findViewById(R.id.searchText)).setText(search);
				}

				@Override
				public void list()
				{
					listSongs(findViewById(R.id.list_songs));
				}
			}).execute(result);
		}
	}

	private class SongMonitor extends _ISongMonitorDisp
	{
		private int id = 0;

		@Override
		public void newSong(final Song s, Current __current)
		{
			Log.d("SongMonitor", "new song : " + s.artist + " - " + s.title);
			MainActivity.this.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					NotificationCompat.Builder builder =
							new NotificationCompat.Builder(MainActivity.this)
									.setSmallIcon(android.R.drawable.sym_def_app_icon)
									.setContentTitle("New song !\n")
									.setContentText(s.artist + " - " + s.title);

					NotificationManager mNotificationManager =
							(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					mNotificationManager.notify(id++, builder.build());
				}
			});
		}
	}
}
