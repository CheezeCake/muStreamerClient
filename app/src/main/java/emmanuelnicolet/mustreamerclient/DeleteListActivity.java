package emmanuelnicolet.mustreamerclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Player.MediaInfo;
import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;

public class DeleteListActivity extends AbstractResultActivity
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
		final MediaInfo m = mediainfos.get(position);

		new AlertDialog.Builder(this)
				.setTitle("Confirmation")
				.setMessage("Supprimer " + m.media.path + " ?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton)
					{
						String msg = "Successfully deleted";
						Ice.Communicator ic = IceData.iceCommunicator;

						try {
							Ice.ObjectPrx base = ic.stringToProxy(MainActivity.getMetaServerEndpointStr());
							IMetaServerPrx srv = IMetaServerPrxHelper.checkedCast(base);
							if (srv == null)
								throw new Error("Invalid proxy");

							srv.remove(m);

						} catch (Ice.LocalException e) {
							msg = "Error";
							e.printStackTrace();
						} catch (Exception e) {
							msg = "Error";
							System.err.println(e);
						}

						Toast.makeText(DeleteListActivity.this, msg, Toast.LENGTH_SHORT).show();
					}})
				.setNegativeButton(android.R.string.no, null).show();
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

