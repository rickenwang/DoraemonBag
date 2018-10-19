package com.doraemon.ui.list;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 内部使用 {@link List} 来保存数据，更适合于不用刷新单个 Item 的场景。
 *
 * Created by rickenwang on 2018/10/16.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public abstract class BaseDiffAdapter<T extends ItemSortable<T>>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static final int VIEW_TYPE_BASE = 0;
    static final int VIEW_TYPE_NO_MORE = 1;
    static final int VIEW_TYPE_LOADING_MORE = 2;

    protected List<? extends T> items;

    public static final int ITEM_CHANGE_PAYLOAD = 1;

    private OnItemClickedListener onItemClickedListener;

    public BaseDiffAdapter() {}


    /**
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
                    return items.get(oldItemPosition).areItemsTheSame(data.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return items.get(oldItemPosition).areContentsTheSame(data.get(newItemPosition));
                }
            });

            items = data;
            result.dispatchUpdatesTo(this);
        }
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
    protected abstract void initViewHolder(RecyclerView.ViewHolder viewHolder, T item, int position);

    /**
     * 只刷新需要改变的数据，一些固定的内容的 View 不需要改变
     *
     * @param viewHolder
     * @param item
     */
    protected abstract void refreshViewHolder(RecyclerView.ViewHolder viewHolder, T item, int position);

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

        initViewHolder(holder, items.get(position), position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {

        if (!payloads.isEmpty()) {
            refreshViewHolder(holder, items.get(position), position);
            return;
        }

        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {

        return VIEW_TYPE_BASE;
    }

    public interface OnItemClickedListener {

        void onClicked(View contentView, int position);
    }
}
