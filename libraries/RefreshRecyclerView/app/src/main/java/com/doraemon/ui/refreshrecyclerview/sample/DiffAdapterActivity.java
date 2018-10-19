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
                persons.add(new Person("ricken", true));
                persons.add(new Person("brady", true));

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


                final Person hertz = new Person("hertz", true);
                final List<Person> newPersons = new LinkedList<>(persons);
                //newPersons.add(hertz);
                newPersons.add(1, hertz);

                runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        personAdapter.setList(newPersons);
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < 20; i++) {

                    final int number = i;
                    newPersons.get(1).setName("ricken_" + number);
                    newPersons.get(1).setBeauty(number % 2 == 0);
                    runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            personAdapter.setList(newPersons);
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
