package com.example.weihuang.audioapplication;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;

public class TaskTransmit extends AsyncTask<Void, Integer, Void> {

    private int sampleRate;
    private int numSamples;
    private final byte generatedSnd[];
    private AudioTrack audioTrack;

    public TaskTransmit(ToneCreator toneCreator) {
        sampleRate = toneCreator.getSampleRate();
        numSamples = toneCreator.getNumSamples();
        generatedSnd = toneCreator.getGeneratedSnd();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        playSound();
        return null;
    }

    void playSound(){
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.setLoopPoints(0,generatedSnd.length/4,-1);
        audioTrack.play();
    }

    public void stopTransmit() {
        audioTrack.stop();
        audioTrack.release();
    }
}
