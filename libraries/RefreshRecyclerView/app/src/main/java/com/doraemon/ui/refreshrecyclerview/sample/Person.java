package com.doraemon.ui.refreshrecyclerview.sample;

import com.doraemon.ui.list.ItemSortable;

/**
 * Created by rickenwang on 2018/10/17.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class Person implements ItemSortable<Person> {

    private String name;

    private String phone;

    public Person(String name, String phone) {

        this.name = name;
        this.phone = phone;
    }

    @Override
    public int compare(Person o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean areContentsTheSame(Person o) {
        return name.equals(o.name)
                && phone.equals(o.phone);
    }

    @Override
    public boolean areItemsTheSame(Person o) {
        return name.equals(o.name);
    }

    @Override
    public Object payload(Person person) {
        return 1;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
