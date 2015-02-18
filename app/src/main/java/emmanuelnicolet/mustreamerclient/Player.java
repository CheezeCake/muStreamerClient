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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);


		if (mediainfo == null) {
            mediainfo = new MediaInfo();
            mediainfo.media = new Song();
        }

        Intent intent = getIntent();
        mediainfo.endpointStr = intent.getStringExtra(MainActivity.MEDIA_ENDPOINT_STR);
        mediainfo.media.path = intent.getStringExtra(MainActivity.MEDIA_SONG_PATH);

        if (token == null)
            new setupStream().execute();
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
        if (token == null || playing)
            return;

        Ice.Communicator ic = MainActivity.ic;

        try {
            Ice.ObjectPrx base = ic.stringToProxy(MainActivity.METASRV_ENDPOINT_STR);
            IMetaServerPrx srv = IMetaServerPrxHelper.checkedCast(base);
            if (srv == null)
                throw new Error("Invalid proxy");

            srv.play(token);




            String url = token.streamingURL;
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            playing = true;

            try {
                //mediaPlayer.setDataSource("http://onchevps.ddns.net:8090/" + url);
				mediaPlayer.setDataSource(url);
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Ice.LocalException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private class setupStream extends AsyncTask<Void, Void, StreamToken>
    {
        protected StreamToken doInBackground(Void... voids)
        {
            Ice.Communicator ic = MainActivity.ic;
            StreamToken t = null;

            try {
                Ice.ObjectPrx base = ic.stringToProxy(MainActivity.METASRV_ENDPOINT_STR);
                IMetaServerPrx srv = IMetaServerPrxHelper.checkedCast(base);
                if (srv == null)
                    throw new Error("Invalid proxy");

                t = srv.setupStreaming(mediainfo);

            } catch (Ice.LocalException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            return t;
        }

        protected void onPostExecute(StreamToken t)
        {
            token = t;
            System.out.println("ONCHE " + t.streamingURL);
        }
    }
}
