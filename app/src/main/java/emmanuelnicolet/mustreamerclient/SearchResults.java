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
import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;


public class SearchResults extends AbstractResultActivity
{
	private String searchText;
	private String searchType;
	private List<MediaInfo> mediainfos = new ArrayList<MediaInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		searchText = intent.getStringExtra(MainActivity.SEARCH_STRING);
		searchType = intent.getStringExtra(MainActivity.SEARCH_TYPE);

		new FetchResults().execute(new String[] {
				MainActivity.getMetaServerEndpointStr(),  searchText });
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
					throw new Error("Invalid proxy");

				if (searchType.equals("everything"))
					medias = srv.find(strs[1]);
				else if (searchType.equals("artist"))
					medias = srv.findByArtist(strs[1]);
				else
					medias = srv.findByTitle(strs[1]);

			} catch (Ice.LocalException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println(e);
			}

			return medias;
		}
	}
}