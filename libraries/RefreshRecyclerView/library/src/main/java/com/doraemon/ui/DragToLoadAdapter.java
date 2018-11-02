package com.doraemon.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * Created by rickenwang on 2018/10/29.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public abstract class DragToLoadAdapter<T extends ItemSortable<T>> extends BaseDiffAdapter<T> {

    private int type;

    protected int loadingMoreLayoutId() {

        return R.layout._default_item_progress;
    }

    protected int noMoreLayoutId() {

        return R.layout._default_item_no_more;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutId = 0;

        switch (viewType) {

            case VIEW_TYPE_BASE: layoutId = baseItemLayoutId(); break;
            case VIEW_TYPE_LOADING_MORE: layoutId = loadingMoreLayoutId(); break;
            case VIEW_TYPE_NO_MORE: layoutId = noMoreLayoutId(); break;
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View contentView = layoutInflater.inflate(layoutId, parent, false);
        return baseItemViewHolder(contentView);
    }

    /**
     * 设置底部 Item
     *
     * VIEW_TYPE_BASE -> VIEW_TYPE_LOADING_MORE
     *                -> VIEW_TYPE_NO_MORE
     *
     * VIEW_TYPE_LOADING_MORE -> VIEW_TYPE_BASE
     *                        -> VIEW_TYPE_NO_MORE
     *
     * VIEW_TYPE_NO_MORE -> VIEW_TYPE_BASE
     *                   -XXX VIEW_TYPE_LOADING_MORE
     *
     * @param type 有效值 VIEW_TYPE_BASE VIEW_TYPE_NO_MORE  VIEW_TYPE_LOADING_MORE
     */
    synchronized void setType(int type) {

        if (type != VIEW_TYPE_LOADING_MORE &&
                type != VIEW_TYPE_NO_MORE &&
                type != VIEW_TYPE_BASE) {

            return;
        }

        Log.i("DragToLoadAdapter", String.format("current type is %d, new type is %d", this.type, type));
        if (this.type == VIEW_TYPE_NO_MORE && type == VIEW_TYPE_LOADING_MORE) {

            return;
        }

        int currentType = this.type;
        this.type = type;

        if (currentType != type) {

            LinkedList<? extends T> newItems = new LinkedList<>(items);

            if (currentType == VIEW_TYPE_BASE) {
                newItems.addLast(null);
            } else if (type == VIEW_TYPE_BASE){
                newItems.removeLast();
            } else {
                if (newItems.size() > 1) {
                    notifyItemChanged(newItems.size() - 1);
                }
            }
            super.setList(newItems);
        }
    }

    @Override
    public void setList(List<? extends T> data) {
        super.setList(data);
        type = VIEW_TYPE_BASE;
    }


    @Override
    public int getItemViewType(int position) {

        if (items.get(position) == null) {
            return type;
        }

        return VIEW_TYPE_BASE;
    }

    public int getType() {
        return type;
    }
}
