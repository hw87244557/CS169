package com.example.weihuang.audioapplication;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TaskPlay extends AsyncTask<Void, Integer, Void> {

    private Context mContext;
    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
    + "/recording.pcm";
    private File audioFile;

    public TaskPlay(Context context) {
        mContext = context;
        audioFile = new File(filePath);
        try {
            audioFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        int bufferSize = AudioTrack.getMinBufferSize(
                Constants.frequence,
                Constants.channelConfig,
                Constants.audioEncoding);
        short[] buffer = new short[bufferSize/4];
        try {
            //定义输入流，将音频写入到AudioTrack类中，实现播放
            DataInputStream dataInputStream = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(audioFile)));
            //实例化AudioTrack
            AudioTrack audioTrack = new AudioTrack(
                    AudioManager.STREAM_MUSIC,
                    Constants.frequence,
                    Constants.channelConfig,
                    Constants.audioEncoding,
                    bufferSize,
                    AudioTrack.MODE_STREAM);
            //开始播放
            audioTrack.play();
            //由于AudioTrack播放的是流，所以我们需要一边播放一边读取
            while (dataInputStream.available() > 0) {
                int i = 0;
                while (dataInputStream.available() > 0 && i < buffer.length) {
                    buffer[i] = dataInputStream.readShort();
                    i++;
                }
                //然后将数据写入到AudioTrack中
                audioTrack.write(buffer, 0, buffer.length);
            }
            //播放结束
            audioTrack.stop();
            dataInputStream.close();
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
    }
}
