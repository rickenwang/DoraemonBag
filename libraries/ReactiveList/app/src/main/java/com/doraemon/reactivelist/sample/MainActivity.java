package com.doraemon.reactivelist.sample;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(getMainLooper());

        final MutableLiveData<Person> personMutableLiveData = new MutableLiveData<>();

        personMutableLiveData.observe(this, new Observer<Person>() {
            @Override
            public void onChanged(@Nullable Person person) {
                System.out.println("person name is " + person.name);
            }
        });

        final Person person = new Person("brady");
        personMutableLiveData.setValue(person);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                personMutableLiveData.setValue(person);
            }
        }, 2000);


    }
}
