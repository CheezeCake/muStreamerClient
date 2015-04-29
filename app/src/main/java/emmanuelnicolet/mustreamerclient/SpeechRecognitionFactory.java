package emmanuelnicolet.mustreamerclient;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class SpeechRecognitionFactory
{
	@Nullable
	public static SpeechRecognition create(System s, final Context c)
	{
		if (s == System.POCKETSPHINX) {
			return new PocketsphinxSpeechRecognition()
			{
				@Override
				protected Context getContext()
				{
					return c;
				}
			};
		}
		else if (s == System.SPEERAL) {
			return new SpeeralSpeechRecognition()
			{
				@Override
				protected Context getContext()
				{
					return c;
				}
			};
		}

		return null;
	}

	public enum System
	{
		POCKETSPHINX(0), SPEERAL(1), ANDROID(2);

		private static final Map<Integer, System> lookup = new HashMap<>();

		static {
			for (System w : EnumSet.allOf(System.class))
				lookup.put(w.getCode(), w);
		}

		private int code;

		System(int code)
		{
			this.code = code;
		}

		public static System get(int code)
		{
			return lookup.get(code);
		}

		public int getCode()
		{
			return code;
		}
	}
}
