package emmanuelnicolet.mustreamerclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioRecord;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
{
	public final static String SEARCH_STRING = "emmanuelnicolet.mustreamerclient.SEARCH_STRING";
	public final static String SEARCH_TYPE = "emmanuelnicolet.mustreamerclient.SEARCH_TYPE";

    public final static String MEDIA_ENDPOINT_STR = "emmanuelnicolet.musicstreamerclient.MEDIA_ENDPOINT_STR";
    public final static String MEDIA_SONG_PATH = "emmanuelnicolet.musicstreamerclient.MEDIA_SONG_PATH";

	private final static String PREFERENCES_NAME = "emmanuelnicolet.musicstreamer.PREFERENCES_NAME";
	private final static String PREFERENCES_METASERVER_HOSTNAME =
			"emmanuelnicolet.musicstreamer.PREFERENCES_METASERVER_HOSTNAME";
	private String metaServerHostname = null;
	private final static String PREFERENCES_METASERVER_PORT =
			"emmanuelnicolet.musicstreamer.PREFERENCES_METASERVER_PORT";
	private String metaServerPort = null;
	private static String metaServerEndpointStr = null;

    private AudioRecord recorder = null;

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
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
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
							@Override
							public void onClick (DialogInterface dialog, int id)
							{
							}
					}).show();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy()
	{
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

    public void talk(View v)
    {
        if (recorder != null) {
            System.out.println("STOP RECORDING");
        }
        else {
            System.out.println("START RECORDING");
        }
    }
}
