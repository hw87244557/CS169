package com.example.weihuang.audioapplication;

public class ToneCreator {

    private final int duration = 3; // seconds
    private final int sampleRate = 44100;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private final double freqOfTone = 20000; // hz

    private final byte generatedSnd[] = new byte[2 * numSamples];

    public ToneCreator() {

        // Use a new tread as this can take a while
//        final Thread thread = new Thread(new Runnable() {
//            public void run() {
//                genTone();
//            }
//        });
//        thread.start();
        genTone();

    }

    void genTone() {
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
//        int idx = 0;
//        for (final double dVal : sample) {
//            // scale to maximum amplitude
//            final short val = (short) ((dVal * 32767));
//            // in 16 bit wav PCM, first byte is the low order byte
//            generatedSnd[idx++] = (byte) (val & 0x00ff);
//            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
//        }

        for (int d = 0; d < duration; d++) {
            double EmptyRatio = 0.5;
            for (int s = d*sampleRate; s < sampleRate*EmptyRatio + d*sampleRate; s++) {
                generatedSnd[s] = 0;
            }
            for (int s = (int) (sampleRate*EmptyRatio) + d * sampleRate; s < sampleRate*(d+1); s=s+2) {
                // scale to maximum amplitude
                final short val = (short) ((sample[s] * 12767));
                // in 16 bit wav PCM, first byte is the low order byte
                generatedSnd[s] = (byte) (val & 0x00ff);
                generatedSnd[s+1] = (byte) ((val & 0xff00) >>> 8);
            }
        }
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getNumSamples() {
        return numSamples;
    }

    public byte[] getGeneratedSnd() {
        return generatedSnd;
    }

}
