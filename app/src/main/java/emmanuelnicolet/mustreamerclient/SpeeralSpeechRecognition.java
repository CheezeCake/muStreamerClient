package emmanuelnicolet.mustreamerclient;

import android.util.Log;

import speeral.SpeeralError;

public abstract class SpeeralSpeechRecognition extends SpeechRecognition
{
	@Override
	protected String doInBackground(short[]... params)
	{
		String response = null;

		try {
			Log.d("speeral", "connecting");
			Ice.ObjectPrx base = IceData.iceCommunicator
					.stringToProxy("SpeeralServer:default -h 188.226.241.233 -p 10000");
			speeral.ServerPrx speer = speeral.ServerPrxHelper.checkedCast(base);
			if (speer == null)
				throw new Exception("Invalid proxy");

			Log.d("speeral", "proxy ok");

			response = speer.decode(params[0], true);
		}
		catch (SpeeralError e) {
			setSpeechRecognitionError(e, e.reason);
			e.printStackTrace();
		}
		catch (Exception e) {
			setSpeechRecognitionError(e, e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
