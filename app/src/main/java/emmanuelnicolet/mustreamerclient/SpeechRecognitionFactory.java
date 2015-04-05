package emmanuelnicolet.mustreamerclient;

import android.content.Context;

public class SpeechRecognitionFactory
{
	public enum System { POCKETSPHINX, SPEERAL }

	public static SpeechRecognition create(System s, final Context c)
	{
		if (s == System.POCKETSPHINX) {
			return new PocketsphinxSpeechRecognition() {
				@Override
				protected Context getContext()
				{
					return c;
				}
			};
		}
		else if (s == System.SPEERAL) {
			return new SpeeralSpeechRecognition() {
				@Override
				protected Context getContext()
				{
					return c;
				}
			};
		}

		return null;
	}
}
