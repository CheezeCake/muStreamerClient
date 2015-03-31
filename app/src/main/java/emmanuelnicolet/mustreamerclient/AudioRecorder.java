package emmanuelnicolet.mustreamerclient;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

class AudioRecorder
{
	private AudioRecord recorder = null;
	private Boolean isRecording = false;
    private AudioTrack track = null;
	private static final int REC_SR = 8000;
	private static final int REC_CHAN = AudioFormat.CHANNEL_IN_MONO;
	private static final int AUDIO_CHAN = AudioFormat.CHANNEL_OUT_MONO;
	private static final int REC_ENC = AudioFormat.ENCODING_PCM_16BIT;
	private static final int BUFFER_SIZE = 1024;

	public void startRecording()
	{
		recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
				REC_SR, REC_CHAN,
				REC_ENC, BUFFER_SIZE);
		recorder.startRecording();
		isRecording = true;
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				saveAudioData();
			}
		}).start();
	}

	public void stopRecording()
	{
		isRecording = false;
		recorder.stop();
		recorder.release();
		recorder = null;
	}

	private void saveAudioData()
	{
		track = new AudioTrack(AudioManager.STREAM_MUSIC, REC_SR,
				AUDIO_CHAN, REC_ENC, BUFFER_SIZE, AudioTrack.MODE_STATIC);
		short[] audioData = new short[BUFFER_SIZE];

		while (isRecording) {
			recorder.read(audioData, 0, BUFFER_SIZE);
			track.write(audioData, 0, BUFFER_SIZE);
		}
	}
}
