package com.doraemon.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;


/**
 *
 * Created by rickenwang on 2018/9/26.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class LinearRecyclerView extends SwipeRefreshLayout {


    protected RecyclerView recyclerView;

    protected LinearLayoutManager linearLayoutManager;


    public LinearRecyclerView(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public LinearRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    protected void initView(Context context, AttributeSet attrs) {

        View view = LayoutInflater.from(context).inflate(R.layout.refresh_recycler_view, this, true);
        recyclerView = view.findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    /**
     * 使用前必须调用这个方法来初始化
     *
     * @param adapter
     */
    public void init(BaseSortedAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void init(BaseDiffAdapter adapter) {

        recyclerView.setAdapter(adapter);
    }


}
