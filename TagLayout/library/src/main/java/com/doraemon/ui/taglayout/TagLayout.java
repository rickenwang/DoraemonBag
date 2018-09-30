package com.doraemon.ui.taglayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rickenwang on 2018/9/27.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class TagLayout extends ViewGroup {

    Rect childRect = new Rect();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 确定其 ChildView 的位置
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        final int count = getChildCount();

        // ViewGroup 的上下左右边界相对于其父容器的位置
        int leftPos = getPaddingLeft();
        int rightPos = right - left - getPaddingRight();
        int topPos = getPaddingTop();
        int bottomPos = bottom - top - getPaddingTop();

        int newLineTopPos = topPos;
        int childLeftPos = leftPos;
        int maxLineHeight = 0;

        for (int i = 0; i < count; i++) {

            final View child = getChildAt(i);

            if (child.getVisibility() != GONE) {

                final LayoutParams tagLayout = child.getLayoutParams();

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();


                maxLineHeight = Math.max(maxLineHeight, height);

                if (childLeftPos + width > rightPos) {
                    childLeftPos = leftPos;
                    newLineTopPos += maxLineHeight;
                }

                childRect.top = newLineTopPos;
                childRect.bottom = newLineTopPos + height;
                childRect.left = childLeftPos;
                childRect.right = childLeftPos + width;

                childLeftPos += width;

                child.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
            }
        }

    }


    /**
     * 确定当前 {@link ViewGroup} 以及其 ChildView 的宽度和高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();

        int maxParentWidth = MeasureSpec.getSize(widthMeasureSpec);

        // ViewGroup 的宽高数据
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        int oneLineWidth = 0;
        int oneLineHeight = 0;

        for (int i = 0; i < count; i++) {

            final View child = getChildAt(i);

            if (child.getVisibility() != GONE) {

                /**
                 * 计算 ChildView 的宽和高
                 */
                measureChildWithMargins(child, widthMeasureSpec, 0,
                        heightMeasureSpec, 0);

                final TagLayoutParams layoutParams = (TagLayoutParams) child.getLayoutParams();

                int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                if (oneLineWidth + childWidth > maxParentWidth) { // new line
                    maxWidth = Math.max(maxWidth, oneLineWidth);
                    maxHeight += oneLineHeight;
                    oneLineWidth = 0;
                    oneLineHeight = 0;

                }

                oneLineWidth += childWidth;
                oneLineHeight = Math.max(oneLineHeight, childHeight);

                childState = combineMeasuredStates(childState, child.getMeasuredState());
            }

        }

        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }



    /**
     * 返回自定义 {@link android.view.ViewGroup.LayoutParams}
     *
     * @param attrs 参数
     * @return 自定义 {@link android.view.ViewGroup.LayoutParams}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {

        return new TagLayoutParams(getContext(), attrs);
    }


    static class TagLayoutParams extends MarginLayoutParams {

        int tag_margin;

        TagLayoutParams(Context context, AttributeSet attrs) {

            super(context, attrs);

            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.TagLayout, 0, 0);

            tag_margin = typedArray.getDimensionPixelSize(R.styleable.TagLayout_tag_margin, 0);
            typedArray.recycle();
        }

        public TagLayoutParams(int width, int height) {
            super(width, height);
        }

        public TagLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public TagLayoutParams(LayoutParams source) {
            super(source);
        }


    }
}
