package emmanuelnicolet.mustreamerclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import Player.Error;
import Player.IMusicServerPrx;
import Player.IMusicServerPrxHelper;
import Player.Song;

public abstract class FileUploadTask extends AsyncTask<String, Integer, Void>
{
	private String message;
	private ProgressDialog dialog;
	private long fileSize; // KB
	private String fileName;

	protected abstract Context getContext();

	@Override
	protected void onPreExecute()
	{
		dialog = new ProgressDialog(getContext());
		dialog.setTitle("Uploading file");
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setCancelable(true);
		dialog.setProgressNumberFormat("%1d/%2dKB");
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialog)
			{
				// cancel AsyncTask
				cancel(false);
			}
		});
	}

	@Override
	public Void doInBackground(String... params) // path, artist, title, endpoint
	{
		File file = new File(params[0]);
		fileSize = file.length() / 1024;
		fileName = file.getName();
		publishProgress(-1);

		message = "Successfully added";

		try {
			Ice.ObjectPrx base = IceData.iceCommunicator.stringToProxy(params[3]);
			IMusicServerPrx srv = IMusicServerPrxHelper.checkedCast(base);
			if (srv == null)
				throw new Exception("Invalid proxy");

			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[1024 * 512]; // 512KB
			int bytesRead;
			int offset = 0;

			Log.d("upload", "upload start");
			while ((bytesRead = in.read(buffer)) != -1) {
				byte[] data = Arrays.copyOf(buffer, bytesRead);
				Log.d("upload", "bytes read = " + bytesRead + ", data.length = " + data.length);
				srv.uploadFile(file.getName(), offset, data);
				offset += bytesRead;
				publishProgress(offset / 1024);
			}
			Log.d("upload", "upload done");

			srv.add(new Song(params[1], params[2], file.getName()));
		}
		catch (Error e) {
			message = e.what;
		}
		catch (Exception e) {
			message = "Error";
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void onProgressUpdate(Integer... values)
	{
		if (values[0] == -1) {
			dialog.setMessage("Uploading " + fileName);
			dialog.setMax((int)fileSize);
			dialog.show();
		}

		dialog.setProgress(values[0]);
	}

	@Override
	public void onPostExecute(Void result)
	{
		if (this.dialog != null) {
			this.dialog.dismiss();
		}

		Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
	}

	@Override
	protected void onCancelled()
	{
		if (dialog != null) {
			dialog.dismiss();
		}
	}
}
