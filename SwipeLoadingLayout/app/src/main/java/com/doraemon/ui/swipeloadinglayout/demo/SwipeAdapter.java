package com.doraemon.ui.swipeloadinglayout.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rickenwang on 2018/9/26.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeHolder> {


    List<String> items = new LinkedList<>();

    public SwipeAdapter() {

        items.add("brady");
        items.add("ricken");
        items.add("stone");
    }

    @NonNull
    @Override
    public SwipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item, parent, false);

        return new SwipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeHolder holder, int position) {

        holder.initView(items.get(position));
    }

    @Override
    public int getItemCount() {
        System.out.println("item size is " + items.size());
        return items.size();
    }

    static class SwipeHolder extends RecyclerView.ViewHolder {

        View view;

        public SwipeHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public void initView(String name) {

            TextView text = view.findViewById(R.id.text);
            text.setText(name);
        }
    }
}
