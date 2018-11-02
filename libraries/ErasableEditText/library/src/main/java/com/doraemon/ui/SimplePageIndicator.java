package com.doraemon.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.MainThread;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Copyright 2010-2018 Tencent Cloud. All Rights Reserved.
 */
public class SimplePageIndicator extends ConstraintLayout {

    private ImageView imageView;

    private ImageView redDot;

    private TextView textView;

    int selectedImgId;

    int unselectedImgId;

    public SimplePageIndicator(Context context) {
        super(context);
        initView(context, null);
    }

    public SimplePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SimplePageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SimplePageIndicator,
                0, 0);

        String name;

        try {
            selectedImgId = typedArray.getResourceId(R.styleable.SimplePageIndicator_selected_img, R.mipmap.doraemon_person_selected);
            unselectedImgId = typedArray.getResourceId(R.styleable.SimplePageIndicator_unselected_img, R.mipmap.doraemon_person);
            name = typedArray.getString(R.styleable.SimplePageIndicator_page_name);

        } finally {
            typedArray.recycle();
        }



        View view = LayoutInflater.from(context).inflate(R.layout.doraemon_page_indicator, this, true);

        imageView = view.findViewById(R.id.img);
        textView = view.findViewById(R.id.text);
        redDot = view.findViewById(R.id.red_dot);

        name = TextUtils.isEmpty(name) ? "name" : name;
        imageView.setImageResource(unselectedImgId);
        textView.setText(name);
        redDot.setVisibility(GONE);
    }


    @MainThread
    public void setSelected(boolean selected) {

        if (selected) {
            imageView.setImageResource(selectedImgId);
        } else {
            imageView.setImageResource(unselectedImgId);
        }
    }

    @MainThread
    public void enableRedDot(boolean enable) {

        if (enable) {
            redDot.setVisibility(VISIBLE);
        } else {
            redDot.setVisibility(GONE);
        }
    }
}
