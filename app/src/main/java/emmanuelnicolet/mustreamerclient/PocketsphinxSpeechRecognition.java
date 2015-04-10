package emmanuelnicolet.mustreamerclient;

import android.util.Log;

import PocketSphinxIce.IPocketSphinxServerPrx;
import PocketSphinxIce.IPocketSphinxServerPrxHelper;

public abstract class PocketsphinxSpeechRecognition extends SpeechRecognition
{
	@Override
	protected String doInBackground(short[]... params)
	{
		String response = null;

		try {
			Log.d("pocketsphinx", "connecting");
			Ice.ObjectPrx base = IceData.iceCommunicator
					.stringToProxy("PocketSphinxServer:default -h 188.226.241.233 -p 20000");
			IPocketSphinxServerPrx ps = IPocketSphinxServerPrxHelper.checkedCast(base);
			if (ps == null)
				throw new Exception("Invalid proxy");

			Log.d("pocketsphinx", "proxy ok");

			response = ps.decode(params[0]);
		}
		catch (PocketSphinxIce.Error e) {
			Log.d("ERROR", "e.what = " + e.what);
			setSpeechRecognitionError(e, e.what);
			e.printStackTrace();
		}
		catch (Exception e) {
			setSpeechRecognitionError(e, e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
