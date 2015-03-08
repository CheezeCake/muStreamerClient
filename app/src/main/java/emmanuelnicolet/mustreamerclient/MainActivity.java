package emmanuelnicolet.mustreamerclient;

import android.content.Intent;
import android.media.AudioRecord;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity
{
	public final static String SEARCH_STRING = "emmanuelnicolet.mustreamerclient.SEARCH_STRING";
	public final static String METASRV_ENDPOINT_STR = "MetaServer:default -h onche.ovh -p 10000";
    public final static String MEDIA_ENDPOINT_STR = "emmanuelnicolet.musicstreamerclient.MEDIA_ENDPOINT_STR";
    public final static String MEDIA_SONG_PATH = "emmanuelnicolet.musicstreamerclient.MEDIA_SONG_PATH";

    private AudioRecord recorder = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (IceData.iceCommunicator == null)
			IceData.iceCommunicator = Ice.Util.initialize(new String[] {""});
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

	public void onSearchClick(View v)
	{
		EditText searchText = (EditText)findViewById(R.id.searchText);
		String text = searchText.getText().toString();
		if (text.isEmpty())
			return;

		Intent intent = new Intent(this, SearchResults.class);
		intent.putExtra(SEARCH_STRING, text);
		startActivity(intent);
	}

    public void onTalkClick(View v)
    {
        if (recorder != null) {
            System.out.println("STOP RECORDING");
        }
        else {
            System.out.println("START RECORDING");
        }
    }
}
