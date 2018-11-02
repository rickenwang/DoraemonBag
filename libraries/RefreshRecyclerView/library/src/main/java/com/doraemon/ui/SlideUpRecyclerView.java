package com.doraemon.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 * 上拉监听RecyclerView
 *
 * 需要注意的是这里是通过OnTouchListener来实现的上拉监听，所以其他设置OnTouchListener时会产生冲突
 *
 * todo 也可以利用OnItemTouchListener来实现，这里先留个足迹
 *
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */

public class SlideUpRecyclerView extends RecyclerView {

    private int distance;

    private OnSlideUpListener onSlideUpListener;


    public SlideUpRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initOnSlideUpListener();
        //distance = ScreenUnitHelper.dip2px(context, 80);
    }

    private void initOnSlideUpListener() {

        Log.d("TAG", "initOnSlideUpListener");

        setOnTouchListener(new OnTouchListener() {

            float startY = 0;
            float startX = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean moveUp = false;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN :
                        startY = event.getY();
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP :

                        if (event.getY() + distance < startY) {
                            moveUp = true;
                        }
                        break;
                }

                int distanceX = (int) (startX - event.getX());
                int distanceY = (int) (startY - event.getY());

                if (moveUp && distanceY > distanceX) {
                    Log.i("TAG", "y = "+event.getY()+", distance = "+distance +", startY = "+startY);

                    int visibleItemCount = getLayoutManager().getChildCount();
                    int totalItemCount = getLayoutManager().getItemCount();
                    int pastVisibleItems = ((LinearLayoutManager)getLayoutManager()).findFirstVisibleItemPosition();
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {

                        if (onSlideUpListener != null) {
                            onSlideUpListener.onSlideUp();
                        }
                    }
                }
                return false;
            }
        });

    }

    @Override
    public boolean canScrollVertically(int direction) {
        // check if scrolling up
        if (direction < 1) {
            boolean original = super.canScrollVertically(direction);
            return !original && getChildAt(0) != null && getChildAt(0).getTop() < 0 || original;
        }
        return super.canScrollVertically(direction);

    }

    public void setOnSlideUpListener(final OnSlideUpListener onSlideUpListener) {

        this.onSlideUpListener = onSlideUpListener;

    }

    public interface OnSlideUpListener {

        void onSlideUp();
    }


}
