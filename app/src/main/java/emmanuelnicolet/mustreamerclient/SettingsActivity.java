package emmanuelnicolet.mustreamerclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;


public class SettingsActivity extends Activity
{
	public final static String PREFERENCES_NAME = "emmanuelnicolet.musicstreamer.PREFERENCES_NAME";

	public static final String PREFERENCES_METASERVER_HOSTNAME = "emmanuelnicolet.musicstreamer.PREFERENCES_METASERVER_HOSTNAME";
	public static final String PREFERENCES_METASERVER_PORT = "emmanuelnicolet.musicstreamer.PREFERENCES_METASERVER_PORT";

	public static final String PREFERENCES_SPEECH_RECOGNITION = "emmanuelnicolet.musicstreamer.PREFERENCES_SPEECH_RECOGNITION";

	public static final String PREFERENCES_POCKETSPHINX_HOSTNAME = "emmanuelnicolet.musicstreamer.PREFERENCES_POCKETSPHINX_HOSTNAME";
	public static final String PREFERENCES_POCKETSPHINX_PORT = "emmanuelnicolet.musicstreamer.PREFERENCES_POCKETSPHINX_PORT";

	public static final String PREFERENCES_SPEERAL_HOSTNAME = "emmanuelnicolet.musicstreamer.PREFERENCES_SPEERAL_HOSTNAME";
	public static final String PREFERENCES_SPEERAL_PORT = "emmanuelnicolet.musicstreamer.PREFERENCES_SPEERAL_PORT";

	public static final String PREFERENCES_COMMAND_PARSER_WEB_SERVICE_URL = "emmanuelnicolet.musicstreamer.PREFERENCES_COMMAND_PARSER_WEB_SERVICE_URL";

	public static final String SAVED = "emmanuelnicolet.musicstreamer.SAVED";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		TextView tv = (TextView)findViewById(R.id.metaserver_hostname);
		tv.setText(Settings.metaServerHostname);
		tv = (TextView)findViewById(R.id.metaserver_port);
		tv.setText(Settings.metaServerPort);

		int id = (Settings.speechRecognitionSystem == SpeechRecognitionFactory.System.POCKETSPHINX) ? R.id.pocketsphinx_radio_button : R.id.speeral_radio_button;
		RadioButton speechRecognitionSystemRadioButton = (RadioButton)findViewById(id);
		speechRecognitionSystemRadioButton.setChecked(true);

		tv = (TextView)findViewById(R.id.pocketsphinx_hostname);
		tv.setText(Settings.pocketSphinxHostname);
		tv = (TextView)findViewById(R.id.pocketsphinx_port);
		tv.setText(Settings.pocketSphinxPort);

		tv = (TextView)findViewById(R.id.speeral_hostname);
		tv.setText(Settings.speeralHostname);
		tv = (TextView)findViewById(R.id.speeral_port);
		tv.setText(Settings.speeralPort);

		tv = (TextView)findViewById(R.id.command_parser_web_service_url);
		tv.setText(Settings.commmandParserWebServiceURL);
	}

	public void save(View v)
	{
		SharedPreferences settings = getSharedPreferences(SettingsActivity.PREFERENCES_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		TextView tv = (TextView)findViewById(R.id.metaserver_hostname);
		editor.putString(PREFERENCES_METASERVER_HOSTNAME, tv.getText().toString());
		tv = (TextView)findViewById(R.id.metaserver_port);
		editor.putString(PREFERENCES_METASERVER_PORT, tv.getText().toString());

		RadioButton pocketSphinxRadioButton = (RadioButton)findViewById(R.id.pocketsphinx_radio_button);
		editor.putInt(PREFERENCES_SPEECH_RECOGNITION, (pocketSphinxRadioButton
				.isChecked()) ? SpeechRecognitionFactory.System.POCKETSPHINX
				.getCode() : SpeechRecognitionFactory.System.SPEERAL.getCode());

		tv = (TextView)findViewById(R.id.pocketsphinx_hostname);
		editor.putString(PREFERENCES_POCKETSPHINX_HOSTNAME, tv.getText().toString());
		tv = (TextView)findViewById(R.id.pocketsphinx_port);
		editor.putString(PREFERENCES_POCKETSPHINX_PORT, tv.getText().toString());

		tv = (TextView)findViewById(R.id.speeral_hostname);
		editor.putString(PREFERENCES_SPEERAL_HOSTNAME, tv.getText().toString());
		tv = (TextView)findViewById(R.id.speeral_port);
		editor.putString(PREFERENCES_SPEERAL_PORT, tv.getText().toString());

		tv = (TextView)findViewById(R.id.command_parser_web_service_url);
		editor.putString(PREFERENCES_COMMAND_PARSER_WEB_SERVICE_URL, tv.getText().toString());

		editor.commit();

		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}
}
