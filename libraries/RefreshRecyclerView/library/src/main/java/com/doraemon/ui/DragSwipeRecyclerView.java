package com.doraemon.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import static com.doraemon.ui.BaseDiffAdapter.VIEW_TYPE_LOADING_MORE;

/**
 * Created by rickenwang on 2018/10/30.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class DragSwipeRecyclerView extends LinearRecyclerView {

    private int visibleThreshold = 1;

    /** item 的数目必须大于这个值才会加载更多 */
    private int minLoadingMoreThreshold = 1;

    private Handler mainHandler;

    private OnLoadingMoreListener onLoadingMoreListener;

    private DragToLoadAdapter dragToLoadAdapter;

    public DragSwipeRecyclerView(@NonNull Context context) {
        super(context);
    }

    public DragSwipeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @AnyThread
    public void setTrunked(final boolean trunked) {

        if (dragToLoadAdapter != null) {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (trunked) {
                        dragToLoadAdapter.setType(BaseSortedAdapter.VIEW_TYPE_BASE);
                    } else {
                        dragToLoadAdapter.setType(BaseSortedAdapter.VIEW_TYPE_NO_MORE);
                    }
                }
            });
        }
    }

    @UiThread
    public void setAdapter(DragToLoadAdapter dragToLoadAdapter) {

        init(dragToLoadAdapter);
        this.dragToLoadAdapter = dragToLoadAdapter;
    }


    @Override
    protected void initView(Context context, AttributeSet attrs) {
        super.initView(context, attrs);

        mainHandler = new Handler(Looper.getMainLooper());


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //Log.i("scroll", String.format("dx = %d, dy = %d", dx, dy));
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager
                        .findLastVisibleItemPosition();
                if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                    // End has been reached
                    // Do something
                    if (onLoadingMoreListener != null && dragToLoadAdapter.getType() == DragToLoadAdapter.VIEW_TYPE_BASE
                            && totalItemCount >= minLoadingMoreThreshold && dy > 0 ) {

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                dragToLoadAdapter.setType(VIEW_TYPE_LOADING_MORE);
                            }
                        });

                        onLoadingMoreListener.onLoadingMore();
                    }
                }
            }
        });
    }

    public void setOnLoadingMoreListener(OnLoadingMoreListener onLoadingMoreListener) {
        this.onLoadingMoreListener = onLoadingMoreListener;
    }


    @Override
    public void setOnRefreshListener(@Nullable final OnRefreshListener listener) {
        super.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                setTrunked(true);
                listener.onRefresh();
            }
        });

    }

    public interface OnLoadingMoreListener {

        void onLoadingMore();
    }
}
