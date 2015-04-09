package emmanuelnicolet.mustreamerclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;
import Player.MediaInfo;


public class SearchResults extends AbstractResultActivity
{
	private String searchText;
	private String searchType;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		searchText = intent.getStringExtra(MainActivity.SEARCH_STRING);
		searchType = intent.getStringExtra(MainActivity.SEARCH_TYPE);

		new FetchResults().execute(new String[] { MainActivity.getMetaServerEndpointStr(), searchText });
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent intent = new Intent(this, Player.class);
		MediaInfo m = mediainfos.get(position);

		intent.putExtra(MainActivity.MEDIA, m);
		startActivity(intent);
	}

	private class FetchResults extends AbstractFetchResults
	{
		protected MediaInfo[] doInBackground(String... strs)
		{
			Ice.Communicator ic = IceData.iceCommunicator;
			MediaInfo[] medias = null;

			try {
				Ice.ObjectPrx base = ic.stringToProxy(strs[0]);
				IMetaServerPrx srv = IMetaServerPrxHelper.checkedCast(base);
				if (srv == null)
					throw new Exception("Invalid proxy");

				if (searchType.equals("everything"))
					medias = srv.find(strs[1]);
				else if (searchType.equals("artist"))
					medias = srv.findByArtist(strs[1]);
				else
					medias = srv.findByTitle(strs[1]);

			}
			catch (Exception e) {
				e.printStackTrace();
				setResultActivityError(strs[0], e);
			}

			return medias;
		}
	}
}