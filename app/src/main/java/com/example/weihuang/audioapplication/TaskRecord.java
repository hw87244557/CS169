package com.example.weihuang.audioapplication;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class TaskRecord extends AsyncTask<Void, Integer, Void> {

    private Context mContext;
    private String fpath;
    private static int[] mSampleRates = new int[] { 8000, 11025, 22050, 44100 };
    private boolean isRecording = false;
    private Activity mParentActivity = null;
    private boolean isOffline = false;

    public TaskRecord(Context context, Activity parentActivity) {
        mContext = context;
        mParentActivity = parentActivity;

        File folderPath = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/data/files/");
        folderPath.mkdirs();
        int fileNum = folderPath.listFiles().length;

        fpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        fpath += "/data/files/";
        fpath += "/recording" + Integer.toString(fileNum) + ".pcm";

    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            DataOutputStream dataOutputStream = null;
            if (isOffline) {
                //开通输出流到指定的文件
                dataOutputStream = new DataOutputStream(
                        new BufferedOutputStream(new FileOutputStream(fpath, false)));
            }
            //根据配置信息，来获得合适的缓冲大小
            int bufferSize = AudioRecord.getMinBufferSize(
                    Constants.frequence,
                    Constants.channelConfig,
                    Constants.audioEncoding);
            //实例化AudioRecord
            AudioRecord audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    Constants.frequence,
                    Constants.channelConfig,
                    Constants.audioEncoding,
                    bufferSize);
            //定义缓冲
            short[] buffer = new short[bufferSize];

//            AudioRecord audioRecord = findAudioRecord();


//            int sessionID = audioRecord.getAudioSessionId();
//            AcousticEchoCanceler acousticEchoCanceler = AcousticEchoCanceler.create(sessionID);
//            acousticEchoCanceler.setEnabled(false);
//            AutomaticGainControl automaticGainControl = AutomaticGainControl.create(sessionID);
//            automaticGainControl.setEnabled(false);
            int sessionID = audioRecord.getAudioSessionId();
            if (AcousticEchoCanceler.isAvailable()) {
                AcousticEchoCanceler acousticEchoCanceler = AcousticEchoCanceler.create(sessionID);
                acousticEchoCanceler.setEnabled(false);
            }
            if (AutomaticGainControl.isAvailable()) {
                AutomaticGainControl automaticGainControl = AutomaticGainControl.create(sessionID);
                automaticGainControl.setEnabled(false);
            }
            //开始录制
            audioRecord.startRecording();
            isRecording = true;
            int r = 0;//存储录制进度

            BlockerDetector blockerDetector = new BlockerDetector();

            boolean flag = false;
            //定义循环，根据进度判断是否继续录制
            while (r >= 0 && isRecording) {
                //从buffer中读取字节，返回读取的数据的个数
                int bufferNumRead = audioRecord.read(buffer, 0,buffer.length);


                if (isOffline) {
                    //循环将buffer中的音频数据写入当OutputStream中
                    for (int i = 0; i < bufferNumRead; i++) {
                        dataOutputStream.writeShort(buffer[i]);
                    }
                } else {
                    for (int i = 0; i < bufferNumRead; i++) {
                        if (blockerDetector.addValue((int) buffer[i])) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag) {
                    break;
                }
                r++;
            }

            if (flag) {
                int sampleRate;
                int numSamples;
                final byte generatedSnd[];
                AudioTrack audioTrack;
                ToneCreator toneCreator = new ToneCreator(1);

                sampleRate = toneCreator.getSampleRate();
                numSamples = toneCreator.getNumSamples();
                generatedSnd = toneCreator.getGeneratedSnd();

                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                        sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, numSamples,
                        AudioTrack.MODE_STATIC);
                audioTrack.write(generatedSnd, 0, generatedSnd.length);
                audioTrack.play();
            }

//            Vibrator vibrator = (Vibrator) mParentActivity.getSystemService(Context.VIBRATOR_SERVICE);
//            vibrator.vibrate(2000);

            //录制结束
            audioRecord.stop();
            if (isOffline) {
                dataOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(Void result) {
//        ((Activity) mContext)
//                .findViewById(R.id.button_play)
//                .setVisibility(View.VISIBLE);
        ((Activity) mContext)
                .findViewById(R.id.button_record)
                .setVisibility(View.VISIBLE);
//        ((Activity) mContext)
//                .findViewById(R.id.button_stop)
//                .setVisibility(View.GONE);
    }

    public void stopRecord() {
        isRecording = false;
    }

    public AudioRecord findAudioRecord() {
        for (int rate : mSampleRates) {
            for (short audioFormat : new short[] { AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT }) {
                for (short channelConfig : new short[] { AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO }) {
                    try {
                        Log.d("C.TAG", "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
                                + channelConfig);
                        int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, rate, channelConfig, audioFormat, bufferSize);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                return recorder;
                        }
                    } catch (Exception e) {
                        Log.e("C.TAG", rate + "Exception, keep trying.", e);
                    }
                }
            }
        }
        return null;
    }

}
