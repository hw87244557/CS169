package com.example.weihuang.audioapplication;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.os.AsyncTask;
import android.os.Environment;
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


    public TaskRecord(Context context) {
        mContext = context;

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
            //开通输出流到指定的文件
            DataOutputStream dataOutputStream = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(fpath, false)));
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
            //定义循环，根据进度判断是否继续录制
            while (r >= 0 && isRecording) {
                //从buffer中读取字节，返回读取的数据的个数
                int bufferNumRead = audioRecord.read(buffer, 0,buffer.length);
                //循环将buffer中的音频数据写入当OutputStream中
                for(int i = 0; i < bufferNumRead; i++) {
                    dataOutputStream.writeShort(buffer[i]);
                }
                r++;
            }
            //录制结束
            audioRecord.stop();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(Void result) {
        ((Activity) mContext)
                .findViewById(R.id.button_play)
                .setVisibility(View.VISIBLE);
        ((Activity) mContext)
                .findViewById(R.id.button_record)
                .setVisibility(View.VISIBLE);
        ((Activity) mContext)
                .findViewById(R.id.button_record2)
                .setVisibility(View.VISIBLE);
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
