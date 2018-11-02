package com.doraemon.ui;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 内部使用 {@link SortedList} 来保存数据，更适合于经常刷新单个 Item 的场景。
 *
 * Created by rickenwang on 2018/10/16.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public abstract class BaseSortedAdapter<T extends ItemSortable<T>>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static protected final int VIEW_TYPE_BASE = 0;
    static protected final int VIEW_TYPE_NO_MORE = 1;
    static protected final int VIEW_TYPE_LOADING_MORE = 2;

    protected SortedList<T> items;

    public static final int ITEM_CHANGE_PAYLOAD = 1;

    private OnItemClickedListener onItemClickedListener;


    public BaseSortedAdapter(Class<T> tClass) {

        items = new SortedList<>(tClass, new SortedList.Callback<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compare(o2);
            }

            @Override
            public boolean areContentsTheSame(T oldItem, T newItem) {
                return oldItem.areContentsTheSame(newItem);
            }

            @Override
            public boolean areItemsTheSame(T item1, T item2) {
                return item1.areItemsTheSame(item2);
            }

            @Override
            public void onChanged(int position, int count) {

                BaseSortedAdapter.this.onChanged(position, count);
            }

            @Override
            public void onInserted(int position, int count) {

                BaseSortedAdapter.this.notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {

                BaseSortedAdapter.this.notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {

                BaseSortedAdapter.this.notifyItemMoved(fromPosition, toPosition);
            }
        });
    }


    public void onChanged(int position, int count) {

        notifyItemRangeChanged(position, count, ITEM_CHANGE_PAYLOAD);
        // notifyItemRangeChanged(position, count);
    }


    public void onInserted(int position, int count) {

        notifyItemRangeInserted(position, count);
    }


    public void onRemoved(int position, int count) {

        notifyItemRangeRemoved(position, count);
    }

    public void onMoved(int fromPosition, int toPosition) {

        notifyItemMoved(fromPosition, toPosition);
    }

    public void addAll(List<T> data) {

        items.addAll(data);

    }

    public void add(T data) {

        items.add(data);
    }


    public void remove(int position) {

        items.removeItemAt(position);
    }

    public T get(int position) {

        return items.get(position);
    }

    public void update(T data, int position) {

        items.updateItemAt(position, data);
    }


    /**
     * 返回列表项布局文件的 id
     *
     * @return
     */
    protected abstract int baseItemLayoutId();

    /**
     * 需要用户通过继承 {@link android.support.v7.widget.RecyclerView.ViewHolder} 来自定义列表项的
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


    protected void setType(int type) {}

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
        return items != null ? items.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {

        return VIEW_TYPE_BASE;
    }

    public interface OnItemClickedListener {

        void onClicked(View contentView, int position);
    }
}
