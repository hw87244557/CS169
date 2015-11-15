package com.example.weihuang.audioapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Button buttonRecord, buttonPlay, buttonStop,buttonRecord2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonRecord = (Button) findViewById(R.id.button_record);
        buttonRecord2 = (Button) findViewById(R.id.button_record2);
        buttonPlay = (Button) findViewById(R.id.button_play);
        buttonStop = (Button) findViewById(R.id.button_stop);
        ButtonListener buttonListener = new ButtonListener();
        buttonListener.setContext(this);
        buttonListener.setParentActivity(this);
        buttonListener.setToneCreator(new ToneCreator(1));
        buttonListener.setToneCreator2(new ToneCreator(2));
        buttonRecord.setOnClickListener(buttonListener);
        buttonRecord2.setOnClickListener(buttonListener);
        buttonPlay.setOnClickListener(buttonListener);
        buttonStop.setOnClickListener(buttonListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
