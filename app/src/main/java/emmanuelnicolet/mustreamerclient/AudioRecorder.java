package emmanuelnicolet.mustreamerclient;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.util.Arrays;

class AudioRecorder
{
	private static final int REC_SR = 16000;
	private static final int REC_CHAN = AudioFormat.CHANNEL_IN_MONO;
	private static final int REC_ENC = AudioFormat.ENCODING_PCM_16BIT;
	private static final int BUFFER_SIZE = 2048;
	private static final int FULL_BUFFER_SIZE = BUFFER_SIZE * 70;
	private static AudioRecord recorder = null;
	private static boolean isRecording = false;
	private static short[] audioData = null;
	private static int audioDataLength = 0;

	public static void startRecording()
	{
		if (isRecording)
			stopRecording();

		recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, REC_SR, REC_CHAN, REC_ENC, BUFFER_SIZE);
		recorder.startRecording();
		isRecording = true;
		new Thread(new Runnable()
		{
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
		audioData = new short[FULL_BUFFER_SIZE];
		int offset = 0;
		int recorded;

		while (isRecording && offset <= FULL_BUFFER_SIZE - BUFFER_SIZE) {
			recorded = recorder.read(audioData, offset, BUFFER_SIZE);
			offset += recorded;
		}

		audioDataLength = offset;

		recorder.stop();
		recorder.release();
		recorder = null;
	}

	public static short[] getAudioData()
	{
		return Arrays.copyOf(audioData, audioDataLength);
	}

	public static void release()
	{
		audioData = null;
	}

	public static boolean isIsRecording()
	{
		return isRecording;
	}
}
