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

	public static final String PREFERENCES_GLACIER2_HOSTNAME = "emmanuelnicolet.musicstreamer.PREFERENCES_GLACIER2_HOSTANME";
	public static final String PREFERENCES_GLACIER2_PORT = "emmanuelnicolet.musicstreamer.PREFERENCES_GLACIER2_PORT";
	public static final String PREFERENCES_GLACIER2_USER = "emmanuelnicolet.musicstreamer.PREFERENCES_GLACIER2_USER";
	public static final String PREFERENCES_GLACIER2_PASSWORD = "emmanuelnicolet.musicstreamer.PREFERENCES_GLACIER2_PASSWORD";

	public static final String PREFERENCES_ICESTORM_HOSTNAME = "emmanuelnicolet.musicstreamer.PREFERENCES_ICESTORM_HOSTANME";
	public static final String PREFERENCES_ICESTORM_PORT = "emmanuelnicolet.musicstreamer.PREFERENCES_ICESTORM_PORT";

	public static final String PREFERENCES_SPEECH_RECOGNITION = "emmanuelnicolet.musicstreamer.PREFERENCES_SPEECH_RECOGNITION";

	public static final String PREFERENCES_POCKETSPHINX_HOSTNAME = "emmanuelnicolet.musicstreamer.PREFERENCES_POCKETSPHINX_HOSTNAME";
	public static final String PREFERENCES_POCKETSPHINX_PORT = "emmanuelnicolet.musicstreamer.PREFERENCES_POCKETSPHINX_PORT";

	public static final String PREFERENCES_SPEERAL_HOSTNAME = "emmanuelnicolet.musicstreamer.PREFERENCES_SPEERAL_HOSTNAME";
	public static final String PREFERENCES_SPEERAL_PORT = "emmanuelnicolet.musicstreamer.PREFERENCES_SPEERAL_PORT";

	public static final String PREFERENCES_COMMAND_PARSER_WEB_SERVICE_URL = "emmanuelnicolet.musicstreamer.PREFERENCES_COMMAND_PARSER_WEB_SERVICE_URL";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		TextView tv = (TextView)findViewById(R.id.metaserver_hostname);
		tv.setText(Settings.metaServerHostname);
		tv = (TextView)findViewById(R.id.metaserver_port);
		tv.setText(Settings.metaServerPort);

		int id = R.id.android_radio_button;
		switch (Settings.speechRecognitionSystem) {
			case ANDROID:
				id = R.id.android_radio_button;
				break;
			case POCKETSPHINX:
				id = R.id.pocketsphinx_radio_button;
				break;
			case SPEERAL:
				id = R.id.speeral_radio_button;
				break;
		}
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

		tv = (TextView)findViewById(R.id.icestorm_hostname);
		tv.setText(Settings.iceStormHostname);
		tv = (TextView)findViewById(R.id.icestorm_port);
		tv.setText(Settings.iceStormPort);

		tv = (TextView)findViewById(R.id.glacier2_hostname);
		tv.setText(Settings.glacier2Hostname);
		tv = (TextView)findViewById(R.id.glacier2_port);
		tv.setText(Settings.glacier2Port);
		tv = (TextView)findViewById(R.id.glacier2_user);
		tv.setText(Settings.glacier2User);
		tv = (TextView)findViewById(R.id.glacier2_password);
		tv.setText(Settings.glacier2Password);
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
		RadioButton androidRadioButton = (RadioButton)findViewById(R.id.android_radio_button);
		editor.putInt(PREFERENCES_SPEECH_RECOGNITION, (pocketSphinxRadioButton
				.isChecked()) ? SpeechRecognitionFactory.System.POCKETSPHINX
				.getCode() : (androidRadioButton
				.isChecked()) ? SpeechRecognitionFactory.System.ANDROID
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

		tv = (TextView)findViewById(R.id.icestorm_hostname);
		editor.putString(PREFERENCES_ICESTORM_HOSTNAME, tv.getText().toString());
		tv = (TextView)findViewById(R.id.icestorm_port);
		editor.putString(PREFERENCES_ICESTORM_PORT, tv.getText().toString());

		tv = (TextView)findViewById(R.id.glacier2_hostname);
		editor.putString(PREFERENCES_GLACIER2_HOSTNAME, tv.getText().toString());
		tv = (TextView)findViewById(R.id.glacier2_port);
		editor.putString(PREFERENCES_GLACIER2_PORT, tv.getText().toString());
		tv = (TextView)findViewById(R.id.glacier2_user);
		editor.putString(PREFERENCES_GLACIER2_USER, tv.getText().toString());
		tv = (TextView)findViewById(R.id.glacier2_password);
		editor.putString(PREFERENCES_GLACIER2_PASSWORD, tv.getText().toString());

		editor.commit();

		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}
}
