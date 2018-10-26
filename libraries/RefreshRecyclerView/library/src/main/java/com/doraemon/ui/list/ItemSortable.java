package com.doraemon.ui.list;

/**
 * Created by rickenwang on 2018/10/16.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public interface ItemSortable<T> {

    int compare(T o);

    boolean areContentsTheSame(T o);

    boolean areItemsTheSame(T o);

    Object payload(T o);
}
