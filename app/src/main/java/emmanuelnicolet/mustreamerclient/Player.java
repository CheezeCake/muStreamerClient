package emmanuelnicolet.mustreamerclient;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;
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
	private IMetaServerPrx srv = null;

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
		System.out.println("STOP");
		stopStream();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (mediaPlayer != null) {
			try {
				mediaPlayer.pause();
			} catch (Exception e) {
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
		System.out.println("PLAY");

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
						} catch (Exception e) {
							mediaPlayer.release();
							mediaPlayer = null;
							e.printStackTrace();
							playEnabled = true;
						}
					} catch (Exception e) {
						mediaPlayer = null;
						e.printStackTrace();
						playEnabled = true;
					}

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
		System.out.println("PAUSE");
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
			} catch (Exception e) {
				System.err.print(e);
			}

			mediaPlayer = null;
			mediainfo = null;

			try {
				srv.stop(token);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setupStream()
	{
		Ice.Communicator ic = IceData.iceCommunicator;

		try {
			Ice.ObjectPrx base = ic.stringToProxy(MainActivity.getMetaServerEndpointStr());
			srv = IMetaServerPrxHelper.checkedCast(base);
			if (srv == null)
				throw new Error("Invalid proxy");

			token = srv.setupStreaming(mediainfo);
			System.out.println("STREAM URL: " + token.streamingURL);
		} catch (Ice.LocalException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		playButton.setEnabled(true);
		stopButton.setEnabled(true);
	}
}