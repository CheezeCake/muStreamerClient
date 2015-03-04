package emmanuelnicolet.mustreamerclient;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import Ice.LocalException;
import Player.StreamToken;
import Player.IMetaServerPrx;
import Player.IMetaServerPrxHelper;
import Player.MediaInfo;
import Player.Song;

public class Player extends ActionBarActivity
{
    private MediaInfo mediainfo = null;
    private StreamToken token = null;
    private boolean playing = false;
    private MediaPlayer mediaPlayer = null;
    private IMetaServerPrx srv = null;

	Button pauseButton;
	Button playButton;
	Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		pauseButton = (Button)findViewById(R.id.pause);
		playButton = (Button)findViewById(R.id.play);
		stopButton = (Button)findViewById(R.id.stop);

		if (mediainfo == null) {
            mediainfo = new MediaInfo();
            mediainfo.media = new Song();
        }

        Intent intent = getIntent();
        mediainfo.endpointStr = intent.getStringExtra(MainActivity.MEDIA_ENDPOINT_STR);
        mediainfo.media.path = intent.getStringExtra(MainActivity.MEDIA_SONG_PATH);

        if (token == null) {
			new Runnable() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void play(View v)
    {
        System.out.println("PLAY");

        if (srv == null || token == null)
            return;

        if (!playing) {
			playing = true;
			playButton.setEnabled(false);

			new Thread(new Runnable() {
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
							mediaPlayer.prepare(); // might take long! (for buffering, etc)

							mediaPlayer.start();
							pauseEnabled = true;
						} catch (Exception e) {
							e.printStackTrace();
							playEnabled = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						playing = false;
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
		runOnUiThread(new Runnable() {
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
        if (playing) {
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
            mediaPlayer.stop();
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
		Ice.Communicator ic = MainActivity.ic;

		try {
			Ice.ObjectPrx base = ic.stringToProxy(MainActivity.METASRV_ENDPOINT_STR);
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