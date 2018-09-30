package com.doraemon.sample.toucheventdispatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i(App.APP_TAG, "Activity # dispatchTouchEvent " + App.actionToString(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(App.APP_TAG, "Activity # onTouchEvent " + App.actionToString(event.getAction()));
        return super.onTouchEvent(event);
    }

}
