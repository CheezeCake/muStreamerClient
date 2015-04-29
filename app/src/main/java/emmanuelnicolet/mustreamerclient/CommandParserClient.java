package emmanuelnicolet.mustreamerclient;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CommandParserClient extends AsyncTask<String, Void, JSONObject>
{
	@Override
	public JSONObject doInBackground(String... command)
	{
		URL url;
		URLConnection con;
		InputStream is = null;
		BufferedReader br;
		String line;
		String json;
		StringBuilder jsonBuffer = new StringBuilder();

		try {
			String param = URLEncoder.encode(command[0], "UTF-8");
			String urlString =
					"http://" + Settings.commmandParserWebServiceURL + "?command=" + param;
			Log.d("cmdParseClient", "url = " + urlString);
			url = new URL(urlString);
			con = url.openConnection();

			is = con.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null)
				jsonBuffer.append(line);
		}
		catch (IOException e) {
			Log.e("cmdParserClient", e.toString());
			return null;
		}

		try {
			is.close();
		}
		catch (IOException e) {
			Log.e("cmdParserClient", e.toString());
		}

		json = jsonBuffer.toString();
		Log.d("fetchJSON", json);

		JSONObject ret = null;
		try {
			ret = new JSONObject(json);
		}
		catch (JSONException e) {
			ret = null;
		}

		return  ret;
	}

	@Override
	public void onPostExecute(JSONObject json)
	{
		String jsonObjectToString = (json != null) ? json.toString() : "null";
		Log.d("cmdParserClient post", jsonObjectToString);
		// TODO: execute action !
	}
}
