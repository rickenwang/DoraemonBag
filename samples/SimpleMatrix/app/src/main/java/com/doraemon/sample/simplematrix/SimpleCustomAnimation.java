package com.doraemon.sample.simplematrix;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

/**
 * Created by rickenwang on 2018/9/29.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class SimpleCustomAnimation extends Animation {

    private int width;

    private int height;

    /**
     * Animation 每次启动后会调用本方法
     *
     * @param width 关联的 View 的宽度
     * @param height 关联的 View 的高度
     * @param parentWidth 关联的 View 的父 View 的宽度
     * @param parentHeight 关联的 View 的父 View 的高度
     */
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        this.width = width;
        this.height = height;

        // 设置持续时间，默认为 0
        setDuration(2000);

        // 默认为 AccelerateDecelerateInterpolator
        setInterpolator(new DecelerateInterpolator());


        // 设置监听
        setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    /**
     * View 会在 Animation 持续时间内，不断调用这个方法来获得合适的转换参数 Transformation。
     *
     * 通过不断的左乘和右乘矩阵，来达到最终的变换效果，相乘时需要注意顺序和左右位置。
     *
     * @param interpolatedTime 经过 {@link android.view.animation.Interpolator} 插值后的时间，已经归一化到 [0.0 1.0]
     * @param t 变换，包括拉伸、平移、旋转以及透明度的改变
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {


        Matrix matrix = t.getMatrix();

        // 设置旋转角度为 [0 360]
        matrix.preRotate(interpolatedTime * 360);//旋转

        // 将变换的基点移动到整个View的中心，默认是以View的左上角作为基点
        matrix.preTranslate(-width / 2, -height / 2);
        matrix.postTranslate(width / 2, height / 2);

    }
}
