package emmanuelnicolet.mustreamerclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;
import Player.IMusicServerPrx;
import Player.IMusicServerPrxHelper;
import Player.MediaInfo;

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

		new AlertDialog.Builder(this).setTitle("Confirm").setMessage("Delete " + m.media.path + " ?").setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int whichButton)
			{
				String msg = "Successfully deleted";
				Ice.Communicator ic = IceData.iceCommunicator;

				try {
					Ice.ObjectPrx base = ic.stringToProxy(m.endpointStr);
					IMusicServerPrx srv = IMusicServerPrxHelper.checkedCast(base);
					if (srv == null)
						throw new Exception("Invalid proxy");

					srv.remove(m.media.path);

				}
				catch (Exception e) {
					Log.e("DeleteListActivity", e.toString());
					msg = e.getMessage();
					if (msg == null)
						msg = "Error";
				}

				Toast.makeText(DeleteListActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		}).setNegativeButton(android.R.string.no, null).show();
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

				medias = srv.listSongs();

			}
			catch (Exception e) {
				e.printStackTrace();
				setResultActivityError(strs[0], e);
			}

			return medias;
		}
	}
}

