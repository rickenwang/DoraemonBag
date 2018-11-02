package com.doraemon.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 内部使用 {@link List} 来保存数据，更适合于拉取 COS 列表项展示的场景。
 *
 * 1、列表项
 *
 * Created by rickenwang on 2018/10/16.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public abstract class BaseDiffAdapter<T extends ItemSortable<T>>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static protected final int VIEW_TYPE_BASE = 0;
    static protected final int VIEW_TYPE_NO_MORE = 1;
    static protected final int VIEW_TYPE_LOADING_MORE = 2;

    protected List<? extends T> items;

    public static final int ITEM_CHANGE_PAYLOAD = 1;

    private OnItemClickedListener onItemClickedListener;

    private OnItemClickedListener onItemLongClickedListener;

    public BaseDiffAdapter() {}

    private boolean detectMoves;

    /**
     * 刷新 Adapter 的数据，并同步修改 UI 。
     *
     * @param data 这里需要注意如果 data 一直复用同一个 List，那么 areContentsTheSame 方法可能会一直返回 true，
     *             导致列表视图不刷新。
     */
    public void setList(final List<? extends T> data) {

        if (items == null) {
            items = data;
            notifyItemRangeInserted(0, data.size());
        } else {

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return items.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                    T oldItem = items.get(oldItemPosition);
                    T newItem = data.get(newItemPosition);

                    if (oldItem == null && newItem == null) {
                        return true;
                    }

                    if (oldItem != null && newItem != null) {
                        return oldItem.areItemsTheSame(newItem);
                    }

                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    T oldItem = items.get(oldItemPosition);
                    T newItem = data.get(newItemPosition);

                    if (oldItem == null && newItem == null) {
                        return true;
                    }

                    if (oldItem != null && newItem != null) {
                        return oldItem.areContentsTheSame(newItem);
                    }

                    return false;
                }

                @Nullable
                @Override
                public Object getChangePayload(int oldItemPosition, int newItemPosition) {

                    T oldItem = items.get(oldItemPosition);
                    T newItem = data.get(newItemPosition);

                    if (oldItem != null && newItem != null) {
                        return newItem.payload(oldItem);
                    }

                    return null;
                }
            }, detectMoves);

            items = data;
            result.dispatchUpdatesTo(this);
        }
    }

    public void setDetectMoves(boolean detectMoves) {
        this.detectMoves = detectMoves;
    }


    /**
     * 返回列表项布局文件的 id
     *
     * @return
     */
    protected abstract int baseItemLayoutId();

    /**
     * 需要用户通过继承 {@link RecyclerView.ViewHolder} 来自定义列表项的
     * ViewHolder
     *
     * @param view
     * @return
     */
    protected abstract RecyclerView.ViewHolder baseItemViewHolder(View view);


    /**
     * 重新设置列表项所有的数据
     *
     * @param viewHolder
     * @param item
     */
    protected abstract void initViewHolder(RecyclerView.ViewHolder viewHolder, @Nullable T item, int position);

    /**
     * 只刷新需要改变的数据，一些固定的内容的 View 不需要改变
     *
     * @param viewHolder
     * @param item
     */
    protected abstract void refreshViewHolder(RecyclerView.ViewHolder viewHolder, @Nullable T item, int position, @Nullable Object payload);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutId = baseItemLayoutId();

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View contentView = layoutInflater.inflate(layoutId, parent, false);
        return baseItemViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onClicked(holder.itemView, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickedListener != null) {
                    onItemLongClickedListener.onClicked(holder.itemView, position);
                }
                return true;
            }
        });

        initViewHolder(holder, items.get(position), position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {

        if (!payloads.isEmpty()) {
            refreshViewHolder(holder, items.get(position), position, payloads.get(0));
            return;
        }

        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }


    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public void setOnItemLongClickedListener(OnItemClickedListener onItemLongClickedListener) {
        this.onItemLongClickedListener = onItemLongClickedListener;
    }

    public interface OnItemClickedListener {

        void onClicked(View contentView, int position);
    }
}
