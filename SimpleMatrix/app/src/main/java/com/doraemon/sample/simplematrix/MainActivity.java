package com.doraemon.sample.simplematrix;

import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private SimpleCustomAnimation simpleCustomAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button transform = findViewById(R.id.transform);
        simpleCustomAnimation = new SimpleCustomAnimation();

        transform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transform.startAnimation(simpleCustomAnimation);
            }
        });

    }
}
