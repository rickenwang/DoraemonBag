package com.doraemon.ui.swipeloadinglayout.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doraemon.ui.swipeloadinglayout.SwipeLoadingLayout;

/**
 * Created by rickenwang on 2018/9/26.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class SwipeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(final View view) {

        final SwipeLoadingLayout swipeLoadingLayout = (SwipeLoadingLayout) view;
        swipeLoadingLayout.setOnRefreshListener(new SwipeLoadingLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLoadingLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SwipeAdapter());
        recyclerView.setVisibility(View.VISIBLE);
    }
}
