package com.doraemon.sample.nestedscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by rickenwang on 2018/9/29.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class NestedScrollChildView extends View implements NestedScrollingChild {

    private NestedScrollingChildHelper nestedScrollingChildHelper;
    private Paint paint;

    public NestedScrollChildView(Context context) {
        super(context);
        init();
    }

    public NestedScrollChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        nestedScrollingChildHelper = new NestedScrollingChildHelper(this);

        paint = new Paint();
        paint.setColor(Color.DKGRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(200, 200, 400, 800, paint);

    }

    @Override
    public void setNestedScrollingEnabled(boolean b) {
        nestedScrollingChildHelper.setNestedScrollingEnabled(b);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return nestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int i) {

        System.out.println("Nest Scroll startNestedScroll");

        return nestedScrollingChildHelper.startNestedScroll(i);
    }

    @Override
    public void stopNestedScroll() {

        System.out.println("Nest Scroll startNestedScroll");
        nestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {

        System.out.println("Nest Scroll startNestedScroll");
        return nestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int i, int i1, int i2, int i3, @Nullable int[] ints) {

        System.out.println("Nest Scroll startNestedScroll");
        return nestedScrollingChildHelper.dispatchNestedScroll(i, i1, i2, i3, ints);
    }

    @Override
    public boolean dispatchNestedPreScroll(int i, int i1, @Nullable int[] ints, @Nullable int[] ints1) {

        System.out.println("Nest Scroll startNestedScroll");
        return nestedScrollingChildHelper.dispatchNestedPreScroll(i, i1, ints, ints1);
    }

    @Override
    public boolean dispatchNestedFling(float v, float v1, boolean b) {



        return nestedScrollingChildHelper.dispatchNestedFling(v, v1, b);
    }

    @Override
    public boolean dispatchNestedPreFling(float v, float v1) {
        return dispatchNestedPreFling(v, v1);
    }
}
