package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;

import adapter.RecyclerSnapAdapter;
import helpers.LinearHorizontalLeftSnapHelper;
import tyrantgit.explosionfield.Utils;

@ContentView(R.layout.activity_recycler_snap_helper)
public class RecyclerSnapHelperActivity extends BaseActivity {

    @ViewInject(R.id.recycler_snap)
    private RecyclerView recyclerViewSnap;

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

        //自定义的SnapHelper
//        new LinearHorizontalLeftSnapHelper().attachToRecyclerView(recyclerViewSnap);

        String[] strings = Arrays.copyOf(getResources().getStringArray(R.array.picUrls), 7);
        RecyclerSnapAdapter adapter = new RecyclerSnapAdapter(this, strings);

        recyclerViewSnap.setAdapter(adapter);


    }

    private float dp2Px(float dp) {

        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

}
