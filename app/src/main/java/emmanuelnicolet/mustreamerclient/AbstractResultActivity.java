package emmanuelnicolet.mustreamerclient;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Player.MediaInfo;

public abstract class AbstractResultActivity extends ListActivity
{
	private String searchText;
	private List<MediaInfo> mediainfos = new ArrayList<MediaInfo>();

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent intent = new Intent(this, Player.class);
		MediaInfo m = mediainfos.get(position);
		System.out.println("ONCHE " + m.endpointStr);
		System.out.println("ONCHE " + m.media.path);

		intent.putExtra(MainActivity.MEDIA_ENDPOINT_STR, m.endpointStr);
		intent.putExtra(MainActivity.MEDIA_SONG_PATH, m.media.path);
		startActivity(intent);
	}


	protected abstract class AbstractFetchResults extends AsyncTask<String, Void, MediaInfo[]>
	{
		@Override
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

					mediainfos.add(m);
				}

				SimpleAdapter adapter = new SimpleAdapter(AbstractResultActivity.this, data,
						android.R.layout.simple_list_item_2,
						new String[]{c1, c2},
						new int[]{android.R.id.text1, android.R.id.text2});
				setListAdapter(adapter);
			}
			else {
				AbstractResultActivity.this.finish();
			}
		}
	}
}
