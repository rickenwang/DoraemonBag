package com.doraemon.sample.toucheventdispatch;

import android.app.Application;

import static android.view.MotionEvent.*;

/**
 * Created by rickenwang on 2018/9/30.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class App extends Application {

    static final String APP_TAG = "TouchEvent";

    public static String actionToString(int action) {
        switch (action) {
            case ACTION_DOWN:
                return "ACTION_DOWN";
            case ACTION_UP:
                return "ACTION_UP";
            case ACTION_CANCEL:
                return "ACTION_CANCEL";
            case ACTION_OUTSIDE:
                return "ACTION_OUTSIDE";
            case ACTION_MOVE:
                return "ACTION_MOVE";
            case ACTION_HOVER_MOVE:
                return "ACTION_HOVER_MOVE";
            case ACTION_SCROLL:
                return "ACTION_SCROLL";
            case ACTION_HOVER_ENTER:
                return "ACTION_HOVER_ENTER";
            case ACTION_HOVER_EXIT:
                return "ACTION_HOVER_EXIT";
            case ACTION_BUTTON_PRESS:
                return "ACTION_BUTTON_PRESS";
            case ACTION_BUTTON_RELEASE:
                return "ACTION_BUTTON_RELEASE";
        }
        int index = (action & ACTION_POINTER_INDEX_MASK) >> ACTION_POINTER_INDEX_SHIFT;
        switch (action & ACTION_MASK) {
            case ACTION_POINTER_DOWN:
                return "ACTION_POINTER_DOWN(" + index + ")";
            case ACTION_POINTER_UP:
                return "ACTION_POINTER_UP(" + index + ")";
            default:
                return Integer.toString(action);
        }
    }
}
