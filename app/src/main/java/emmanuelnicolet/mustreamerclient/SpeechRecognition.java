package emmanuelnicolet.mustreamerclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public abstract class SpeechRecognition extends AsyncTask<short[], Void, String>
{
	private ProgressDialog dialog;

	protected abstract Context getContext();

	@Override
	protected void onPreExecute()
	{
		dialog = new ProgressDialog(getContext());
		dialog.setTitle("Veuillez patienter...");
		dialog.setMessage("Signal audio en cours de traitement...");
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialog)
			{
				// cancel AsyncTask
				cancel(false);
			}
		});

		dialog.show();
	}

	@Override
	protected void onPostExecute(String result)
	{
		//called on ui thread
		if (this.dialog != null) {
			this.dialog.dismiss();
		}

		Log.d("speech", "response = " + result);
	}

	@Override
	protected void onCancelled()
	{
		//called on ui thread
		if (dialog != null) {
			dialog.dismiss();
		}
	}
}
