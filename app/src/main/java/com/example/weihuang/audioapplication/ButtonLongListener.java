package com.example.weihuang.audioapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

/**
 * Created by libinjie on 11/15/15.
 */
public class ButtonLongListener implements Button.OnLongClickListener{
    Context mContext;
    TaskRecord recordTask = null;
    TaskTransmit taskTransmit = null;
    private Activity mParentActivity = null;

    public void setContext (Context context) {
        mContext = context;
    }
    public void setParentActivity (Activity parentActivity) {
        mParentActivity = parentActivity;
    }

    @Override
    public boolean onLongClick(View view) {
        if (recordTask != null) {
            recordTask.stopRecord();
            taskTransmit.stopTransmit();
        }
        return true;
    }
}
