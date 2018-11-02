package com.doraemon.ui.refreshrecyclerview.sample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doraemon.ui.LinearRecyclerView;

import java.util.LinkedList;
import java.util.List;

public class SortedAdapterActivity extends AppCompatActivity {

    private LinearRecyclerView refreshRecyclerView;
    private PersonSortedAdapter personAdapter;

    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshRecyclerView = findViewById(R.id.refresh_recycler_view);
        refreshRecyclerView.setEnabled(false);

        personAdapter = new PersonSortedAdapter();

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

                        personAdapter.addAll(persons);
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final Person hertz = new Person("hertz", "1234567");

                runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        personAdapter.add(hertz);
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        personAdapter.remove(0);
                    }
                });


                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < 20; i++) {

                    final int number = i;
                    runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            personAdapter.update(new Person("ricken_" + number,
                                    "12345"), 1);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }


    private void runInMainThread(Runnable runnable) {

        mainHandler.post(runnable);
    }
}
