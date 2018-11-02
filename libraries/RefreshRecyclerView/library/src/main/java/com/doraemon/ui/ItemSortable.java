package com.doraemon.ui;

import android.support.annotation.Nullable;

/**
 * Created by rickenwang on 2018/10/16.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public interface ItemSortable<T> {

    int compare(@Nullable T o);

    boolean areContentsTheSame(@Nullable T o);

    boolean areItemsTheSame(@Nullable T o);

    Object payload(@Nullable T o);
}
