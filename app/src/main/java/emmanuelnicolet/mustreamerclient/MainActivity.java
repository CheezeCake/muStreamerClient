package emmanuelnicolet.mustreamerclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


import Player.Error;
import Player.Song;
import Player.MusicServerInfo;
import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;
import Player.IMusicServerPrx;
import Player.IMusicServerPrxHelper;


public class MainActivity extends ActionBarActivity
{
	public final static String SEARCH_STRING = "emmanuelnicolet.mustreamerclient.SEARCH_STRING";
	public final static String SEARCH_TYPE = "emmanuelnicolet.mustreamerclient.SEARCH_TYPE";

	public final static String MEDIA = "emmanuelnicolet.musicstreamerclient.MEDIA";

	private final static String PREFERENCES_NAME = "emmanuelnicolet.musicstreamer.PREFERENCES_NAME";
	private final static String PREFERENCES_METASERVER_HOSTNAME =
			"emmanuelnicolet.musicstreamer.PREFERENCES_METASERVER_HOSTNAME";
	private String metaServerHostname = null;
	private final static String PREFERENCES_METASERVER_PORT =
			"emmanuelnicolet.musicstreamer.PREFERENCES_METASERVER_PORT";
	private String metaServerPort = null;
	private static String metaServerEndpointStr = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (IceData.iceCommunicator == null)
			IceData.iceCommunicator = Ice.Util.initialize(new String[] {""});

		loadPreferences();
	}

	public static String getMetaServerEndpointStr()
	{
		return metaServerEndpointStr;
	}

	private void loadPreferences()
	{
		SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
		metaServerHostname = settings.getString(PREFERENCES_METASERVER_HOSTNAME, "onche.ovh");
		metaServerPort = settings.getString(PREFERENCES_METASERVER_PORT, "10000");
		metaServerEndpointStr = "MetaServer:default -h " + metaServerHostname + " -p " + metaServerPort;
	}

	public void setPreferences(String hostname, String port)
	{
		SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREFERENCES_METASERVER_HOSTNAME, hostname);
		editor.putString(PREFERENCES_METASERVER_PORT, port);
		editor.commit();
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
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = getLayoutInflater();
			final View v = inflater.inflate(R.layout.settings_dialog, null);

			TextView tv = (TextView) v.findViewById(R.id.hostname);
			tv.setText(metaServerHostname);
			tv = (TextView) v.findViewById(R.id.port);
			tv.setText(metaServerPort);

			builder.setView(v)
					.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id)
						{
							TextView tv = (TextView) v.findViewById(R.id.hostname);
							String hn = tv.getText().toString();
							tv = (TextView) v.findViewById(R.id.port);
							String p = tv.getText().toString();

							setPreferences(hn, p);
						}

					})
					.setNegativeButton(R.string.cancel, null).show();

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
			} catch (Exception e) {
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

		try {
			Ice.ObjectPrx base = IceData.iceCommunicator.stringToProxy(metaServerEndpointStr);
			IMetaServerPrx srv = IMetaServerPrxHelper.checkedCast(base);
			if (srv == null)
				throw new Error("Invalid proxy");

			final MusicServerInfo[] serversInfo = srv.listMusicServers();
			String[] servers = new String[serversInfo.length];
			for (int i = 0; i < serversInfo.length; i++)
				servers[i] = serversInfo[i].hostname + ":" + serversInfo[i].listeningPort;


			final Spinner s = (Spinner) v.findViewById(R.id.server_spinner);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, servers);
			s.setAdapter(adapter);

			builder.setView(v)
					.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id)
						{
							final String srvEndpoint = serversInfo[s.getSelectedItemPosition()].endpointStr;
							final String artist = ((TextView) v.findViewById(R.id.artist)).getText().toString();
							final String title = ((TextView) v.findViewById(R.id.title)).getText().toString();
							final String path = ((TextView) v.findViewById(R.id.path)).getText().toString();

							if (!srvEndpoint.isEmpty() && !artist.isEmpty() && !title.isEmpty()
									&& !path.isEmpty()) {
								new Thread(new Runnable()
								{
									@Override
									public void run()
									{
										String msg = "Successfully added";

										try {
											Ice.ObjectPrx base = IceData.iceCommunicator.stringToProxy(srvEndpoint);
											IMusicServerPrx srv = IMusicServerPrxHelper.checkedCast(base);
											if (srv == null)
												throw new Error("Invalid proxy");

											srv.add(new Song(artist, title, path));

										}
										catch (Ice.LocalException e) {
											msg = "Error";
											e.printStackTrace();
										}
										catch (Error e) {
											msg = e.what;
										}
										catch (Exception e) {
											msg = "Error";
											System.err.println(e);
										}

										final String _msg = msg;

										MainActivity.this.runOnUiThread(new Runnable() {
											@Override
											public void run()
											{
												Toast.makeText(MainActivity.this, _msg, Toast.LENGTH_SHORT).show();
											}
										});
									}
								}).start();
							}
						}

					})
					.setNegativeButton(R.string.cancel, null)
					.show();
		}
		catch (Ice.LocalException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}

	public void delete(View v)
	{
		Intent intent = new Intent(this, DeleteListActivity.class);
		startActivity(intent);
	}

    public void talk(View v)
    {
        if (AudioRecorder.isIsRecording()) {
            Log.d("", "STOP RECORDING");
            AudioRecorder.stopRecording();
			//getdata
			//release
        }
        else {
            Log.e("", "START RECORDING");
            AudioRecorder.startRecording();
        }
    }
}
