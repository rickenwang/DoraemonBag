package com.doraemon.ui.refreshrecyclerview.sample;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doraemon.ui.list.BaseDiffAdapter;
import com.doraemon.ui.list.BaseSortedAdapter;

/**
 * Created by rickenwang on 2018/10/17.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class PersonDiffAdapter extends BaseDiffAdapter<Person> {


    @Override
    protected int baseItemLayoutId() {
        return R.layout.item;
    }

    @Override
    protected RecyclerView.ViewHolder baseItemViewHolder(View view) {
        return new PersonViewHolder(view);
    }

    @Override
    protected void initViewHolder(RecyclerView.ViewHolder viewHolder, Person item, int position) {


        PersonViewHolder personViewHolder = (PersonViewHolder) viewHolder;
        personViewHolder.init(item);
    }

    @Override
    protected void refreshViewHolder(RecyclerView.ViewHolder viewHolder, Person item, int position) {
        PersonViewHolder personViewHolder = (PersonViewHolder) viewHolder;
        personViewHolder.refresh(item);
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {

        private View contentView;

        private TextView name;
        private ImageView icon;

        private Resources resources;

        public PersonViewHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            resources = contentView.getContext().getResources();

            name = contentView.findViewById(R.id.name);
            icon = contentView.findViewById(R.id.icon);
        }

        void init(Person item) {

            name.setText(item.getName());
            if (item.isBeauty()) {
                icon.setImageDrawable(resources.getDrawable(R.mipmap.ic_launcher_round));
            } else {
                icon.setImageDrawable(resources.getDrawable(R.mipmap.ic_launcher));
            }
        }

        void refresh(Person item) {

            name.setText(item.getName());
        }

    }

}
