package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import adapter.RecyclerSnapAdapter;
import helpers.CardScaleHelper;

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

        //LinearSnapHelper支持一次滚动多个item
//        new LinearSnapHelper().attachToRecyclerView(recyclerViewSnap);

        //PagerSnapHelper仅支持一次滚动一个item
//        new PagerSnapHelper().attachToRecyclerView(recyclerViewSnap);

        //自定义的SnapHelper,前两个都有第一个、最后一个item显示不全问题，这个没有，但是滑动到最后会全部显示完全
//        new LinearHorizontalLeftSnapHelper().attachToRecyclerView(recyclerViewSnap);

        //CardView+中间放大效果
        new CardScaleHelper().attachToRecyclerView(recyclerViewSnap);

        String[] strings = Arrays.copyOf(getResources().getStringArray(R.array.picUrls), 7);
        RecyclerSnapAdapter adapter = new RecyclerSnapAdapter(this, strings);
        recyclerViewSnap.setAdapter(adapter);

        countRvItem = adapter.getItemCount();

//        scheduleTask();
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
        }, 3000, 3000, TimeUnit.MILLISECONDS);
    }

}
