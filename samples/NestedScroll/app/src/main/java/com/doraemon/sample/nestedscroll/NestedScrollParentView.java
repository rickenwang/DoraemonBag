package com.doraemon.sample.nestedscroll;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rickenwang on 2018/9/29.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class NestedScrollParentView extends ViewGroup implements NestedScrollingParent {

    public NestedScrollParentView(Context context) {
        super(context);
    }

    public NestedScrollParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        int childCount = getChildCount();
        if (childCount != 1) {
            return;
        }

        View child = getChildAt(0);
        child.layout(left, top, right, bottom);

    }
}
