package com.doraemon.ui.refreshrecyclerview.sample;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.doraemon.ui.BaseDiffAdapter;
import com.doraemon.ui.DragSwipeRecyclerView;
import com.doraemon.ui.LinearRecyclerView;

import java.util.LinkedList;
import java.util.List;

public class DiffAdapterActivity extends AppCompatActivity {

    private DragSwipeRecyclerView refreshRecyclerView;
    private PersonDiffAdapter personAdapter;

    private Handler mainHandler;

    private MutableLiveData<List<Person>> liveData;

    final List<Person> persons = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainHandler = new Handler(getMainLooper());
        refreshRecyclerView = findViewById(R.id.refresh_recycler_view);
        personAdapter = new PersonDiffAdapter();

        liveData = new MutableLiveData<>();

        initLiveData();

        refreshRecyclerView.setOnLoadingMoreListener(new DragSwipeRecyclerView.OnLoadingMoreListener() {
            @Override
            public void onLoadingMore() {
                Log.i("tag", "loading more");

                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshRecyclerView.setTrunked(false);
                    }
                }, 2000);
            }
        });

        refreshRecyclerView.setAdapter(personAdapter);

        refreshRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                personAdapter.setList(new LinkedList<Person>());

                Log.i("tag", "item count = " + personAdapter.getItemCount());

                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        personAdapter.setList(persons);
                        refreshRecyclerView.setRefreshing(false);
                    }
                }, 2000);
            }
        });



        liveData.observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable List<Person> people) {
                personAdapter.setList(people);
            }
        });
    }


    private void initLiveData() {


        persons.add(new Person("ricken", "12345"));
        persons.add(new Person("brady", "123456"));
        persons.add(new Person("hertz", "12345"));
        persons.add(new Person("erich", "123456"));
        persons.add(new Person("stone", "12345"));
        persons.add(new Person("jack", "123456"));
        persons.add(new Person("ada", "12345"));
        persons.add(new Person("allan", "123456"));
        persons.add(new Person("huang", "12345"));
        persons.add(new Person("wang", "123456"));
        persons.add(new Person("hu", "123456"));
        persons.add(new Person("pen", "12345"));
        persons.add(new Person("tag", "123456"));

        liveData.setValue(persons);

        int i = 0;

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                int i = 0;
                while (i < 50) {

                    i++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final LinkedList<Person> newPersons = new LinkedList<>(persons);
                    newPersons.removeFirst();
                    newPersons.addFirst(new Person("rickenwang", "new-" + i));
                    liveData.postValue(newPersons);
                }

                return null;
            }

        }.execute();

        personAdapter.setOnItemLongClickedListener(new BaseDiffAdapter.OnItemClickedListener() {
            @Override
            public void onClicked(View contentView, int position) {

                Log.i("doraemon", "setOnItemLongClickedListener " + position);
            }
        });

    }

    private void runInMainThread(Runnable runnable) {

        mainHandler.post(runnable);
    }
}
