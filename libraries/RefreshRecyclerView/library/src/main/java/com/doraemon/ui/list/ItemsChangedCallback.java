package com.doraemon.ui.list;

import android.support.v7.util.SortedList;

/**
 * Created by rickenwang on 2018/10/16.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public interface ItemsChangedCallback {

    void onItemsChanged(int position, int count);

    void onItemsInserted(int position, int count);

    void onItemsRemoved(int position, int count);

    void onItemsMoved(int fromPosition, int toPosition);
}
