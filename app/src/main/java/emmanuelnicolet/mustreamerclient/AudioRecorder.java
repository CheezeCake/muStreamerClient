package emmanuelnicolet.mustreamerclient;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

class AudioRecorder
{
	private static AudioRecord recorder = null;
	private static boolean isRecording = false;
    private static AudioTrack track = null;
	private static short[] audioData = null;

	private static final int REC_SR = 8000;
	private static final int REC_CHAN = AudioFormat.CHANNEL_IN_MONO;
	private static final int AUDIO_CHAN = AudioFormat.CHANNEL_OUT_MONO;
	private static final int REC_ENC = AudioFormat.ENCODING_PCM_16BIT;
	private static final int BUFFER_SIZE = 1024;
	private static final int FULL_BUFFER_SIZE = BUFFER_SIZE * 150;

	public static void startRecording()
	{
		if (isRecording)
			stopRecording();

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

	public static void stopRecording()
	{
		isRecording = false;
	}

	private static void saveAudioData()
	{
		track = new AudioTrack(AudioManager.STREAM_MUSIC, REC_SR,
				AUDIO_CHAN, REC_ENC, BUFFER_SIZE, AudioTrack.MODE_STREAM);
		track.play();

		audioData = new short[FULL_BUFFER_SIZE];
		int offset = 0;
		int recorded;

		while (isRecording && offset <= FULL_BUFFER_SIZE - BUFFER_SIZE) {
			recorded = recorder.read(audioData, offset, BUFFER_SIZE);
			track.write(audioData, offset, recorded);
			offset += recorded;
		}

		recorder.stop();
		recorder.release();
		recorder = null;
	}

	public static short[] getAudioData()
	{
		return audioData;
	}

	public static void release()
	{
		audioData = null;
		track = null;
	}

	public static boolean isIsRecording()
	{
		return isRecording;
	}
}
