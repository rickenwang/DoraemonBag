package com.doraemon.ui.sample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;

import com.doraemon.ui.ErasableInputText;
import com.doraemon.ui.SimplePageIndicator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ErasableInputText erasableInputText = findViewById(R.id.erasable);

        erasableInputText.setInputChecker(new ErasableInputText.TextChecker() {
            @Override
            public boolean errorInput(Editable editable) {
                return false;
            }

            @Override
            public boolean successInput(Editable editable) {
                return true;
            }
        });


        final SimplePageIndicator simplePageIndicator = findViewById(R.id.page_indicator);

        Handler handler = new Handler(getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                simplePageIndicator.enableRedDot(true);
                simplePageIndicator.setSelected(true);
            }
        }, 2000);

    }
}
