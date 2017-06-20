package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import adapter.RecyclerSnapAdapter;

@ContentView(R.layout.activity_recycler_snap_helper)
public class RecyclerSnapHelperActivity extends BaseActivity {

    @ViewInject(R.id.recycler_snap)
    private RecyclerView recyclerViewSnap;

    private int countRvItem = 0;
    private int itemIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRecyclerView();
    }

    private void initRecyclerView() {

        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        recyclerViewSnap.setHasFixedSize(true);
        recyclerViewSnap.setLayoutManager(manager);

        new LinearSnapHelper().attachToRecyclerView(recyclerViewSnap);

//        new PagerSnapHelper().attachToRecyclerView(recyclerViewSnap);

        //自定义的SnapHelper
//        new LinearHorizontalLeftSnapHelper().attachToRecyclerView(recyclerViewSnap);

        String[] strings = Arrays.copyOf(getResources().getStringArray(R.array.picUrls), 7);
        RecyclerSnapAdapter adapter = new RecyclerSnapAdapter(this, strings);

        recyclerViewSnap.setAdapter(adapter);

        countRvItem = adapter.getItemCount();

//        scheduleTask();
    }

    private float dp2Px(float dp) {

        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    /**
     * java定时任务
     */
    private void scheduleTask() {

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                itemIndex = itemIndex % countRvItem;
                recyclerViewSnap.smoothScrollToPosition(itemIndex);
                itemIndex++;
            }
        }, 0, 3000, TimeUnit.MILLISECONDS);
    }

}
