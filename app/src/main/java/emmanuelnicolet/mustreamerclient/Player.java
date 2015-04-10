package emmanuelnicolet.mustreamerclient;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;
import Player.IMusicServerPrx;
import Player.IMusicServerPrxHelper;
import Player.MediaInfo;
import Player.StreamToken;

public class Player extends ActionBarActivity
{
	private static MediaInfo mediainfo = null;
	private static MediaPlayer mediaPlayer = null;
	Button pauseButton;
	Button playButton;
	Button stopButton;
	private StreamToken token = null;
	private IMusicServerPrx srv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		pauseButton = (Button)findViewById(R.id.pause);
		playButton = (Button)findViewById(R.id.play);
		stopButton = (Button)findViewById(R.id.stop);

		Intent intent = getIntent();
		mediainfo = (MediaInfo)intent.getSerializableExtra(MainActivity.MEDIA);

		TextView artistInfo = (TextView)findViewById(R.id.artist_info);
		artistInfo.setText(mediainfo.media.artist);
		TextView titleInfo = (TextView)findViewById(R.id.title_info);
		titleInfo.setText(mediainfo.media.title);
		TextView pathInfo = (TextView)findViewById(R.id.path_info);
		pathInfo.setText(mediainfo.media.path);

		if (token == null) {
			new Runnable()
			{
				@Override
				public void run()
				{
					setupStream();
				}
			}.run();
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.d("Player", "stop");
		stopStream();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (mediaPlayer != null) {
			try {
				mediaPlayer.pause();
			}
			catch (Exception e) {
			}
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		Button p = (Button)findViewById(R.id.pause);
		if (mediaPlayer != null && p != null && p.isEnabled())
			play(findViewById(R.id.play));
	}

	public void play(View v)
	{
		Log.d("Player", "play");

		if (srv == null || token == null)
			return;

		if (mediaPlayer == null) {
			playButton.setEnabled(false);

			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					boolean playEnabled = false;
					boolean pauseEnabled = false;
					Exception ex = null;

					try {
						srv.play(token);

						String url = token.streamingURL;
						mediaPlayer = new MediaPlayer();
						mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

						try {
							mediaPlayer.setDataSource(url);
							mediaPlayer.prepare();

							mediaPlayer.start();
							pauseEnabled = true;
						}
						catch (Exception e) {
							mediaPlayer.release();
							mediaPlayer = null;
							e.printStackTrace();
							playEnabled = true;
							ex = e;
						}
					}
					catch (Exception e) {
						mediaPlayer = null;
						e.printStackTrace();
						playEnabled = true;
						ex = e;
					}

					if (ex != null)
						Toast.makeText(Player.this.getBaseContext(), ex.getClass()
								.getName() + " : " + ex.getMessage(), Toast.LENGTH_SHORT).show();

					setPlayPause(playEnabled, pauseEnabled);
				}
			}).start();
		}
		else {
			mediaPlayer.start();
			playButton.setEnabled(false);
			pauseButton.setEnabled(true);
		}
	}

	private void setPlayPause(final boolean pl, final boolean pa)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				playButton.setEnabled(pl);
				pauseButton.setEnabled(pa);
			}
		});
	}

	public void pause(View v)
	{
		Log.d("Player", "pause");
		if (mediaPlayer != null) {
			v.setEnabled(false);
			playButton.setEnabled(true);
			mediaPlayer.pause();
		}
	}

	public void stop(View v)
	{
		finish();
	}

	public void stopStream()
	{
		if (mediaPlayer != null) {
			try {
				mediaPlayer.stop();
				mediaPlayer.release();
			}
			catch (Exception e) {
				System.err.print(e);
			}

			mediaPlayer = null;
			mediainfo = null;

			try {
				srv.stop(token);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setupStream()
	{
		Ice.Communicator ic = IceData.iceCommunicator;

		try {
			Ice.ObjectPrx base = ic.stringToProxy(MainActivity.getMetaServerEndpointStr());
			IMetaServerPrx meta = IMetaServerPrxHelper.checkedCast(base);
			if (meta == null)
				throw new Exception("Invalid proxy: " + MainActivity.getMetaServerEndpointStr());

			token = meta.setupStreaming(mediainfo);
			Log.d("Player.setupStream", "streaming URL: " + token.streamingURL);

			base = ic.stringToProxy(token.endpointStr);
			srv = IMusicServerPrxHelper.checkedCast(base);
			if (meta == null)
				throw new Exception("Invalid proxy: " + token.endpointStr);

		}
		catch (Exception e) {
			e.printStackTrace();

			String message = e.getMessage();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
		}

		playButton.setEnabled(true);
		stopButton.setEnabled(true);
	}
}