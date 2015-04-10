package emmanuelnicolet.mustreamerclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public abstract class SpeechRecognition extends AsyncTask<short[], Void, String>
{
	protected String speechRecognitionError = null;
	private ProgressDialog dialog;

	protected abstract Context getContext();

	protected void setSpeechRecognitionError(Exception e, String msg)
	{
		speechRecognitionError = e.getClass().getName();
		if (msg != null)
			speechRecognitionError = speechRecognitionError + " : " + msg;
	}

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
		if (this.dialog != null) {
			this.dialog.dismiss();
		}

		if (speechRecognitionError != null)
			Toast.makeText(getContext(), speechRecognitionError, Toast.LENGTH_SHORT).show();

		Log.d("speech", "response = " + result);
	}

	@Override
	protected void onCancelled()
	{
		if (dialog != null) {
			dialog.dismiss();
		}
	}
}
