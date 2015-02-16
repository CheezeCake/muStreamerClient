package emmanuelnicolet.mustreamerclient;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Player.MediaInfo;


public class SearchResults extends ListActivity
{
	private String searchText;
	private List<String> endpointsStrs = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		searchText = intent.getStringExtra(MainActivity.SEARCH_STRING);

		new FetchResults().execute(new String[] {MainActivity.METASRV_ENDPOINT_STR, searchText});
	}


	private class FetchResults extends AsyncTask<String, Void, MediaInfo[]>
	{
		protected MediaInfo[] doInBackground(String... strs)
		{
			Ice.Communicator ic = MainActivity.ic;
			MediaInfo[] medias = null;

			try {
				Ice.ObjectPrx base = ic.stringToProxy(strs[0]);
				Player.IMetaServerPrx srv = Player.IMetaServerPrxHelper.checkedCast(base);
				if (srv == null)
					throw new Error("Invalid proxy");

				medias = srv.find(strs[1]);

			} catch (Ice.LocalException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

			return medias;
		}

		protected void onPostExecute(MediaInfo[] medias)
		{
			final String c1 = "title";
			final String c2 = "artist";

			if (medias != null) {
				List data = new ArrayList<HashMap<String, String>>();

				for (MediaInfo m : medias) {
					HashMap<String, String> e = new HashMap<String, String>();

					e.put(c1, m.media.title);
					e.put(c2, m.media.artist);
					data.add(e);

					endpointsStrs.add(m.endpointStr);
				}

				SimpleAdapter adapter = new SimpleAdapter(SearchResults.this, data,
						android.R.layout.simple_list_item_2,
						new String[]{c1, c2},
						new int[]{android.R.id.text1, android.R.id.text2});
				setListAdapter(adapter);
			}
			else {
				SearchResults.this.finish();
			}
		}
	}
}