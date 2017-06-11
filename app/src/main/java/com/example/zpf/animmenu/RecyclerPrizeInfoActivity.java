package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import adapter.RvAdapterPrizeInfo;

public class RecyclerPrizeInfoActivity extends AppCompatActivity {

    private RvAdapterPrizeInfo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_prize_info);

        initView();
    }

    private void initView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_prize_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ArrayList<String> strings = new ArrayList<>();
        for (int i=0; i<10; i++) {

            strings.add("恭喜***抽中" + (i*2) + "元红包！");
        }

        adapter = new RvAdapterPrizeInfo(this);
        recyclerView.setAdapter(adapter);
        adapter.setList(strings);

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                adapter.updateList();
            }
        }, 2000, 2000, TimeUnit.MILLISECONDS);
    }
}
