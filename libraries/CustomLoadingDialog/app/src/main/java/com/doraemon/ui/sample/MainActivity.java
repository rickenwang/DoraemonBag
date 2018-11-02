package com.doraemon.ui.sample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doraemon.ui.DoraemonLoadingDialog;

public class MainActivity extends AppCompatActivity {

    private DoraemonLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingDialog = new DoraemonLoadingDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingDialog.show(getSupportFragmentManager(), "loading");

        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.setMessage("已删除100个文件");
            }
        }, 2000);
    }
}
