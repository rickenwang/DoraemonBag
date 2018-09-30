package com.doraemon.sample.toucheventdispatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by rickenwang on 2018/9/30.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class TouchEventViewGroup extends FrameLayout {


    public TouchEventViewGroup(@NonNull Context context) {
        super(context);
    }

    public TouchEventViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i(App.APP_TAG, "View Group # dispatchTouchEvent " + App.actionToString(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.i(App.APP_TAG, "View Group # onInterceptTouchEvent " + App.actionToString(ev.getAction()));
        //return super.onInterceptTouchEvent(ev);

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(App.APP_TAG, "View Group # onTouchEvent " + App.actionToString(event.getAction()));
        return super.onTouchEvent(event);
    }
}
