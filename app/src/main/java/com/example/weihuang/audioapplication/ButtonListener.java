package com.example.weihuang.audioapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

public class ButtonListener implements Button.OnClickListener{
    Context mContext;
    private ToneCreator mToneCreator;

    public void setContext(Context context) {
        mContext = context;
    }

    public void setToneCreator(ToneCreator toneCreator) {
        mToneCreator = toneCreator;
    }

    @Override
    public void onClick(View view) {
        int button_id  = view.getId();
        switch (button_id) {
            case R.id.button_record:
                view.setVisibility(View.INVISIBLE);
                ((Activity) mContext).findViewById(R.id.button_play).setVisibility(View.INVISIBLE);
                TaskRecord recordTask = new TaskRecord(mContext);
                recordTask.execute();
                break;
            case R.id.button_play:
                view.setVisibility(View.INVISIBLE);
                ((Activity) mContext).findViewById(R.id.button_record).setVisibility(View.INVISIBLE);
                TaskPlay playTask = new TaskPlay(mContext);
                playTask.execute();
                break;
            case R.id.button_transmit:
                TaskTransmit taskTransmit = new TaskTransmit(mToneCreator);
                taskTransmit.execute();
                break;
        }
    }
}
