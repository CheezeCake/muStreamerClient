package emmanuelnicolet.mustreamerclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Player.MediaInfo;
import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;

public class ListSongsResults extends AbstractResultActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		new FetchResults().execute(new String[] { MainActivity.getMetaServerEndpointStr() });
	}

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

				medias = srv.listSongs();

			} catch (Ice.LocalException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println(e);
			}

			return medias;
		}
	}
}

