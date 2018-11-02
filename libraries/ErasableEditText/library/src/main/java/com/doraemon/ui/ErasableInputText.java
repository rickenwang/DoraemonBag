package com.doraemon.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Copyright 2010-2018 Tencent Cloud. All Rights Reserved.
 */
public class ErasableInputText extends ConstraintLayout {

    private Drawable normalBorder;
    private Drawable errorBorder;
    private Drawable successBorder;

    /**
     * 根据输入的文本判断是否是有效的输入
     */
    private TextChecker inputChecker;

    private ResultListener resultListener;

    private Editable text;
    private boolean success;

    public ErasableInputText(Context context) {
        super(context);
        initView(context, null);
    }

    public ErasableInputText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ErasableInputText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        success = false;

        inputChecker = new TextChecker() {
            @Override
            public boolean errorInput(Editable input) {
                return false;
            }

            @Override
            public boolean successInput(Editable input) {
                return false;
            }
        };

        normalBorder = ContextCompat.getDrawable(context, R.drawable.edit_text_border_normal);
        errorBorder = ContextCompat.getDrawable(context, R.drawable.edit_text_border_error);
        successBorder = ContextCompat.getDrawable(context, R.drawable.edit_text_border_success);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ErasableInputText,
                0, 0);
        String tipHint = null;
        int inputType = 0;
        int verticalMargin;
        int startMargin;
        int textSize;
        try {
            tipHint = typedArray.getString(R.styleable.ErasableInputText_text_hint);
            inputType = typedArray.getInt(R.styleable.ErasableInputText_input_type, 1);
            startMargin = typedArray.getDimensionPixelSize(R.styleable.ErasableInputText_padding_start, 24);
            verticalMargin = typedArray.getDimensionPixelSize(R.styleable.ErasableInputText_padding_vertical, 24);
            textSize = typedArray.getDimensionPixelSize(R.styleable.ErasableInputText_text_size, 16);

        } finally {
            typedArray.recycle();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.doraemon_input_text, this, true);

        final EditText editText = view.findViewById(R.id.text);
        final ImageView crossView = view.findViewById(R.id.cross);
        setViewBackground(normalBorder);

        if (!TextUtils.isEmpty(tipHint)) {

            editText.setHint(tipHint);
        }

        ConstraintLayout.LayoutParams params = (LayoutParams) editText.getLayoutParams();
        params.bottomMargin = verticalMargin;
        params.topMargin = verticalMargin;
        params.leftMargin = startMargin;

        editText.setLayoutParams(params);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        editText.setInputType(inputType);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                text = editable;

                if (inputChecker.successInput(editable)) {
                    success = true;
                    if (resultListener != null) {
                        resultListener.onSuccess(editable);
                    }
                    setViewBackground(successBorder);
                } else if (inputChecker.errorInput((editable))) {
                    success = false;
                    if (resultListener != null) {
                        resultListener.onFailed(editable);
                    }
                    setViewBackground(errorBorder);
                } else {
                    success = false;
                    if (resultListener != null) {
                        resultListener.onNormal(editable);
                    }
                    setViewBackground(normalBorder);
                }

                if (text != null && text.length() > 0) {
                    crossView.setVisibility(View.VISIBLE);
                } else {
                    crossView.setVisibility(View.INVISIBLE);
                }
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus && text != null && text.length() > 0) {
                    crossView.setVisibility(View.VISIBLE);
                } else {
                    crossView.setVisibility(View.INVISIBLE);
                }
            }
        });

        crossView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    public void setInputChecker(TextChecker inputChecker) {
        this.inputChecker = inputChecker;
    }

    public void setResultListener(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    private void setViewBackground(Drawable background) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(background);
        } else {
            setBackgroundDrawable(background);
        }


    }

    public String getText() {
        return text != null ? text.toString() : "";
    }

    public boolean isSuccess() {
        return success;
    }

    public interface TextChecker {

        boolean errorInput(Editable input);

        boolean successInput(Editable input);
    }

    public interface ResultListener {

        /**
         * 输入已经满足条件
         */
        void onSuccess(Editable input);

        /**
         * 正在输入中
         */
        void onNormal(Editable input);

        /**
         * 有错误的输入
         */
        void onFailed(Editable input);
    }
}
