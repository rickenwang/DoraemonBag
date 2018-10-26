package com.doraemon.ui.refreshrecyclerview.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.doraemon.ui.list.LinearRecyclerView;

import java.util.LinkedList;
import java.util.List;

public class DiffAdapterActivity extends AppCompatActivity {

    private LinearRecyclerView refreshRecyclerView;
    private PersonDiffAdapter personAdapter;

    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshRecyclerView = findViewById(R.id.refresh_recycler_view);
        refreshRecyclerView.setEnabled(false);

        personAdapter = new PersonDiffAdapter();

        refreshRecyclerView.init(personAdapter);

        mainHandler = new Handler(getMainLooper());

        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<Person> persons = new LinkedList<>();
                persons.add(new Person("ricken", "12345"));
                persons.add(new Person("brady", "123456"));

                runInMainThread(new Runnable() {
                    @Override
                    public void run() {

                        personAdapter.setList(persons);
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                final Person hertz = new Person("ricken", "1234567");
                final List<Person> newPersons = new LinkedList<>(persons);
                newPersons.remove(0);
                newPersons.add(0, hertz);

                runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        personAdapter.setList(newPersons);
                    }
                });

            }
        }).start();

    }


    private void runInMainThread(Runnable runnable) {

        mainHandler.post(runnable);
    }
}
