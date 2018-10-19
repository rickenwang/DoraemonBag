package com.doraemon.ui.refreshrecyclerview.sample;

import com.doraemon.ui.list.ItemSortable;

/**
 * Created by rickenwang on 2018/10/17.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class Person implements ItemSortable<Person> {

    private String name;
    private boolean beauty;

    public Person(String name, boolean beauty) {

        this.name = name;
        this.beauty = beauty;
    }

    @Override
    public int compare(Person o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean areContentsTheSame(Person o) {
        return false;
    }

    @Override
    public boolean areItemsTheSame(Person o) {
        return equals(o);
    }

    public String getName() {
        return name;
    }

    public boolean isBeauty() {
        return beauty;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setBeauty(boolean beauty) {
        this.beauty = beauty;
    }
}
