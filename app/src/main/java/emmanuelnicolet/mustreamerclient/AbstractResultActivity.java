package emmanuelnicolet.mustreamerclient;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Player.MediaInfo;

public abstract class AbstractResultActivity extends ListActivity
{
	protected List<MediaInfo> mediainfos = new ArrayList<MediaInfo>();
	protected String resultActivityError = null;

	protected void setResultActivityError(String srvStr, Exception e)
	{
		resultActivityError = e.getClass().getName() + " : Cannot connect to " + srvStr;
		String msg = e.getMessage();
		if (msg != null)
			resultActivityError = resultActivityError + " : " + e.getMessage();
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

				SimpleAdapter adapter = new SimpleAdapter(AbstractResultActivity.this, data, android.R.layout.simple_list_item_2, new String[] { c1, c2 }, new int[] { android.R.id.text1, android.R.id.text2 });
				setListAdapter(adapter);
			}
			else {
				if (resultActivityError != null)
					Toast.makeText(AbstractResultActivity.this, resultActivityError, Toast.LENGTH_SHORT)
							.show();

				AbstractResultActivity.this.finish();
			}
		}
	}
}
