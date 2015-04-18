package emmanuelnicolet.mustreamerclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class CommandParserClient
{
	public static void parse(String command) throws IOException
	{
		URL url;
		InputStream is = null;
		BufferedReader br;
		String line;
		String json = "";

		try {
			url = new URL(Settings.commmandParserWebServiceURL);
			is = url.openStream();
			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null)
				json = json + line;
		}
		finally {
			if (is != null)
				is.close();
		}
	}
}
