package com.doraemon.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.Preconditions;
import android.util.DisplayMetrics;

/**
 * 常见的单位变换
 *
 * Created by rickenwang on 2018/9/29.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class UnitTransform {


    public static float dp2px(Context context, float dp) {

        final float screenDensity = screenDensity(context);

        return dp * screenDensity;
    }


    public static float px2dp(Context context, float px) {

        final float screenDensity = screenDensity(context);
        return px / screenDensity;
    }









    private static float screenDensity(Context context) {

        final Resources resources = context.getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.density;
    }

}
