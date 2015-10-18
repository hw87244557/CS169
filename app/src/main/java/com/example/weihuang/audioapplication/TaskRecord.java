package com.example.weihuang.audioapplication;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class TaskRecord extends AsyncTask<Void, Integer, Void> {

    private Context mContext;
    private File audioFile;
    private static int[] mSampleRates = new int[] { 8000, 11025, 22050, 44100 };


    public TaskRecord(Context context) {
        mContext = context;
        try {
            //在这里我们创建一个文件，用于保存录制内容
            File fpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/data/files/");
            fpath.mkdirs();//创建文件夹

            //创建临时文件，注意这里的格式为.pcm
            audioFile = File.createTempFile("recording",".pcm",fpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            //开通输出流到指定的文件
            DataOutputStream dataOutputStream = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(audioFile)));
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
            //开始录制
            audioRecord.startRecording();
            int r = 0;//存储录制进度
            //定义循环，根据进度判断是否继续录制
            while (r < 100) {
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