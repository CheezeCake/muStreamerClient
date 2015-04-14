package emmanuelnicolet.mustreamerclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class CommandParserClient
{
	public static final String commandParserServiceURL = "http://onche.ovh:8080/parse_command";

	public static void parse(String command)
	{
		URL url;
		InputStream is = null;
		BufferedReader br;
		String line;
		String json = new String();

		try {
			url = new URL(commandParserServiceURL);
			is = url.openStream();
			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null)
				json = json + line;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (is != null)
					is.close();
			}
			catch (IOException ioe) {
			}
		}
	}
}
