package emmanuelnicolet.mustreamerclient;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class CommandParserClient
{
	public static JSONObject fetchJSON(String command) throws IOException, JSONException
	{
		URL url;
		InputStream is = null;
		BufferedReader br;
		String line;
		String json;
		StringBuffer jsonBuffer = new StringBuffer();

		try {
			url = new URL(Settings.commmandParserWebServiceURL);
			is = url.openStream();
			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null)
				jsonBuffer.append(line);
		}
		finally {
			if (is != null)
				is.close();

			json = jsonBuffer.toString();
		}

		Log.d("fetchJSON", json);

		return new JSONObject(json);
	}
}
