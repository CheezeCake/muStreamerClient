package emmanuelnicolet.mustreamerclient;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import Player.MediaInfo;
import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;

public class ListSongsResults extends AbstractResultActivity
{
	private List<MediaInfo> mediainfos = new ArrayList<MediaInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		new FetchResults().execute(new String[] {MainActivity.METASRV_ENDPOINT_STR});
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

