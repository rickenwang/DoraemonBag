package com.doraemon.sample.toucheventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by rickenwang on 2018/9/30.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class TouchEventView extends android.support.v7.widget.AppCompatTextView {

    public TouchEventView(Context context) {
        super(context);
    }

    public TouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i(App.APP_TAG, "View # dispatchTouchEvent " + App.actionToString(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(App.APP_TAG, "View # onTouchEvent " + App.actionToString(event.getAction()));
        return super.onTouchEvent(event);
    }
}
