package com.example.weihuang.audioapplication;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;

public class TaskTransmit extends AsyncTask<Void, Integer, Void> {

    private ToneCreator mToneCreator;
    private int sampleRate;
    private int numSamples;
    private final byte generatedSnd[];

    public TaskTransmit(ToneCreator toneCreator) {
        mToneCreator = toneCreator;
        sampleRate = mToneCreator.getSampleRate();
        numSamples = mToneCreator.getNumSamples();
        generatedSnd = mToneCreator.getGeneratedSnd();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        playSound();
        return null;
    }

    void playSound(){
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }

}