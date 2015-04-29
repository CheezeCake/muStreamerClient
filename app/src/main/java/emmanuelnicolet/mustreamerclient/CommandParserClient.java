package emmanuelnicolet.mustreamerclient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class CommandParserClient extends AsyncTask<String, Void, String>
{
	protected Context context;
	private CommandParserClientResultListener listener;

	CommandParserClient(Context c, CommandParserClientResultListener listener)
	{
		context = c;
		this.listener = listener;
	}

	@Override
	public String doInBackground(String... command)
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

		return json;

	}

	@Override
	public void onPostExecute(String jsonStr)
	{
		try {
			JSONObject json = new JSONObject(jsonStr);
			if (json.has("error")) {
				Toast.makeText(context, json.getString("error"), Toast.LENGTH_LONG).show();
			}
			else if (json.has("type")) {
				String type = json.getString("type");
				if (type.equals("add"))
					parseAdd(json);
				else if (type.equals("search"))
					parseSearch(json);
				else if (type.equals("list"))
					listener.list();
				else
					Toast.makeText(context, "Unknown command type : " + type, Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(context, "No type in JSON data", Toast.LENGTH_LONG).show();
			}
		}
		catch (JSONException e) {
			Log.e("cmdParserClientPost", e.toString());
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void parseAdd(JSONObject json)
	{
		String artist = "";
		String title = "";

		try {
			artist = json.getString("artist");
		}
		catch (JSONException e) {
			artist = "";
		}

		try {
			title = json.getString("title");
		}
		catch (JSONException e) {
			title = "";
		}

		listener.add(artist, title);
	}

	private void parseSearch(JSONObject json)
	{
		String search = "";
		String searchBy = "everything";

		try {
			search = json.getString("search");
		}
		catch (JSONException e) {
			search = "";
		}

		try {
			searchBy = json.getString("searchBy");
		}
		catch (JSONException e) {
			searchBy = "everything";
		}

		listener.search(search, searchBy);
	}

	public interface CommandParserClientResultListener
	{
		void add(String artist, String title);
		void search(String search, String searchBy);
		void list();
	}
}
