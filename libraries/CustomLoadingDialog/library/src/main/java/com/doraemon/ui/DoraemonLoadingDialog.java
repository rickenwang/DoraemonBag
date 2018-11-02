package com.doraemon.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doraemon.utils.UnitTransformUtils;

/**
 * Copyright 2010-2018 Tencent Cloud. All Rights Reserved.
 */
public class DoraemonLoadingDialog extends DialogFragment {

    private String message;

    private int widthInDp = 180;

    private int heightInDp = 110;

    private TextView messageView;

    private View contentView;

    Handler handler;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.doraemon_fragment_loading, null);
        initView(contentView);
        builder.setView(contentView);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void initView(View contentView) {

        messageView = contentView.findViewById(R.id.message);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onResume() {
        super.onResume();

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();

        params.width = (int) UnitTransformUtils.dp2px(getContext(), widthInDp);
        params.height = (int) UnitTransformUtils.dp2px(getContext(), heightInDp);

        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    public void initMessage(String message) {

        this.message = message;
    }

    public void setMessage(final String message) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                messageView.setText(message);
            }
        });
    }

    public void initHeightInDp(int heightInDp) {
        this.heightInDp = heightInDp;
    }

    public void initWidthInDp(int widthInDp) {
        this.widthInDp = widthInDp;
    }


}
