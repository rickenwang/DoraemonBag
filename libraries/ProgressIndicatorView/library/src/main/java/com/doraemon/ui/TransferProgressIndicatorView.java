package com.doraemon.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.doraemon.utils.UnitTransformUtils;

/**
 * Created by rickenwang on 2018/10/11.
 * <p>
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 */
public class TransferProgressIndicatorView extends View {

    public static final int TRANSFER_TYPE_UPLOAD = 0;
    public static final int TRANSFER_TYPE_DOWNLOAD = 1;

    public static final int IN_PROGRESS = 0;
    public static final int PAUSE = 1;
    public static final int RETRY = 2;

    private int state = IN_PROGRESS;
    private int progress = 0;
    private int transferType;

    private int widthInPixel = 0;
    private int heightInPixel = 0;

    private Paint darkerGrayPaint;
    private Paint lightGrayPaint;
    private Paint bluePaint;

    private Paint mArrowPaint;

    private Path mArrow;

    private Context context;

    private int strokeWidthInPixel;

    private float arrowSizePixel;

    int circleX;
    int circleY;
    int radius;

    /**
     * 外部 Rect 范围
     */
    private RectF outRect;

    private RectF centerRect;

    /**
     * 内部 Rect 范围
     */
    private RectF innerRect;


    public TransferProgressIndicatorView(Context context) {
        super(context);
        init(context, null);
    }

    public TransferProgressIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        this.context = context;

        transferType = TRANSFER_TYPE_UPLOAD;

        Resources resources = context.getResources();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TransferProgressIndicatorView, 0, 0);

        float strokeWidth = typedArray.getDimension(R.styleable.TransferProgressIndicatorView_stroke_width, 3);
        strokeWidthInPixel = (int) UnitTransformUtils.dp2px(context, strokeWidth);

        arrowSizePixel = strokeWidthInPixel * 3;

        darkerGrayPaint = new Paint();
        darkerGrayPaint.setColor(resources.getColor(R.color.progress_indicator_gray_dark));
        darkerGrayPaint.setStrokeWidth(strokeWidthInPixel);
        darkerGrayPaint.setStyle(Paint.Style.STROKE);

        lightGrayPaint = new Paint();
        lightGrayPaint.setColor(resources.getColor(R.color.progress_indicator_gray_light));
        lightGrayPaint.setStrokeWidth(strokeWidthInPixel);
        lightGrayPaint.setStyle(Paint.Style.STROKE);

        bluePaint = new Paint();
        bluePaint.setColor(resources.getColor(R.color.progress_indicator_blue));
        bluePaint.setStrokeWidth(strokeWidthInPixel);
        bluePaint.setStyle(Paint.Style.STROKE);

        mArrowPaint = new Paint();
        mArrowPaint.setColor(resources.getColor(R.color.progress_indicator_gray_light));
        mArrowPaint.setStyle(Paint.Style.FILL);
        mArrowPaint.setAntiAlias(true);

    }

    public void setTransferType(int transferType) {

        this.transferType = transferType;
    }

    public void refreshState(int state) {

        if (state < IN_PROGRESS || state > RETRY) {

            throw new RuntimeException("error state");
        }

        this.state = state;

        invalidate();
    }

    public void refreshProgress(int progress) {

        if (progress < 0 || progress > 100) {

            throw new RuntimeException("error progress");
        }
        this.progress = progress;

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int suggestWidth = MeasureSpec.getSize(widthMeasureSpec);
        int suggestHeight = MeasureSpec.getSize(heightMeasureSpec);

        widthInPixel = suggestWidth;
        heightInPixel = suggestHeight;

        circleX = widthInPixel / 2;
        circleY = heightInPixel / 2;
        //radius = Math.min(widthInPixel / 2, heightInPixel / 2) - strokeWidthInPixel / 2;


        if (outRect == null) {
            outRect = new RectF(strokeWidthInPixel, strokeWidthInPixel,
                    widthInPixel - strokeWidthInPixel, heightInPixel - strokeWidthInPixel);
        }
        if (innerRect == null) {
            innerRect = new RectF(widthInPixel/4, heightInPixel/4, 3*widthInPixel/4, 3*heightInPixel/4);
        }

        if (centerRect == null) {

            centerRect = new RectF(arrowSizePixel / 2, arrowSizePixel / 2,
                    widthInPixel - arrowSizePixel / 2, heightInPixel - arrowSizePixel / 2);
        }

        radius = (int) (Math.min(outRect.width() / 2, outRect.height() / 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (state == IN_PROGRESS || state == PAUSE) {

            canvas.drawCircle(circleX, circleY, radius, darkerGrayPaint);
        }

        if (state == IN_PROGRESS) {

            //setBackgroundDrawableCompat(inProgress);
            canvas.drawLine(innerRect.left + innerRect.width() / 4,
                    innerRect.top + innerRect.height() / 8,
                    innerRect.left + innerRect.width() / 4,
                    innerRect.bottom - innerRect.height() / 8,
                    lightGrayPaint);

            canvas.drawLine(innerRect.right - innerRect.width() / 4,
                    innerRect.top + innerRect.height() / 8,
                    innerRect.right - innerRect.width() / 4,
                    innerRect.bottom - innerRect.height() / 8,
                    lightGrayPaint);

            canvas.drawArc(outRect, -90, (float) (3.6 * progress), false, bluePaint);
        }

        if (state == PAUSE) {

            // setBackgroundDrawableCompat(pause);

            canvas.drawLine(innerRect.left + innerRect.width() / 2,
                    innerRect.top + innerRect.height() / 8,
                    innerRect.left + innerRect.width() / 2,
                    innerRect.bottom - innerRect.height() / 8,
                    lightGrayPaint);

            if (transferType == TRANSFER_TYPE_DOWNLOAD) {

                canvas.drawLine(innerRect.left, innerRect.top + innerRect.height() / 2,
                        innerRect.left + innerRect.width() / 2 + strokeWidthInPixel / 4,
                        innerRect.bottom + strokeWidthInPixel / 4, lightGrayPaint);

                canvas.drawLine(innerRect.right, innerRect.top + innerRect.height() / 2,
                        innerRect.left + innerRect.width() / 2 - strokeWidthInPixel / 4,
                        innerRect.bottom + strokeWidthInPixel / 4, lightGrayPaint);
            } else {

                canvas.drawLine(innerRect.left, innerRect.top + innerRect.height() / 2,
                        innerRect.left + innerRect.width() / 2 + strokeWidthInPixel / 4,
                        innerRect.top - strokeWidthInPixel / 4, lightGrayPaint);

                canvas.drawLine(innerRect.right, innerRect.top + innerRect.height() / 2,
                        innerRect.left + innerRect.width() / 2 - strokeWidthInPixel / 4,
                        innerRect.top - strokeWidthInPixel / 4, lightGrayPaint);
            }

            canvas.drawArc(outRect, -90, (float) (3.6 * progress), false, lightGrayPaint);
        }

        if (state == RETRY) {

            canvas.drawArc(centerRect, -40, 140, false, lightGrayPaint);
            canvas.drawArc(centerRect, 140, 140, false, lightGrayPaint);

            drawArrow(canvas, centerRect.centerX(), centerRect.bottom, 90);

            drawArrow(canvas, centerRect.centerX(), centerRect.top, 270);
        }

    }

    private void setBackgroundDrawableCompat(Drawable drawable) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    private void drawArrow(Canvas canvas, float startX, float startY, int angle) {

        if (mArrow == null) {
            mArrow = new android.graphics.Path();
            mArrow.setFillType(android.graphics.Path.FillType.EVEN_ODD);
        } else {
            mArrow.reset();
        }

        // Update the path each time. This works around an issue in SKIA
        // where concatenating a rotation matrix to a scale matrix
        // ignored a starting negative rotation. This appears to have
        // been fixed as of API 21.
        mArrow.moveTo(startX - arrowSizePixel / 2, startY);
        mArrow.lineTo(startX + arrowSizePixel / 2, startY);
        mArrow.lineTo(startX, startY + arrowSizePixel * 3 / 5);

        mArrow.close();

        canvas.save();
        canvas.rotate(angle, startX, startY);

        canvas.drawPath(mArrow, mArrowPaint);
        canvas.restore();
    }

}
