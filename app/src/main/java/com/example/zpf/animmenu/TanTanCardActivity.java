package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import com.mcxtzhang.layoutmanager.swipecard.RenRenCallback;

import java.util.ArrayList;
import java.util.Arrays;

import adapter.TanTanAdapter;

public class TanTanCardActivity extends BaseActivity {

    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_tan_card);

        requestManager = Glide.with(this);

        initView(requestManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestManager.pauseRequests();
        requestManager.onDestroy();
    }

    private void initView(RequestManager requestManager) {

        RecyclerView rvTan = findViewById(R.id.rvTanTanCard);
        rvTan.setLayoutManager(new OverLayCardLayoutManager());
        CardConfig.initConfig(this);
        CardConfig.MAX_SHOW_COUNT = 6;

        ArrayList<String> urls = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.picUrls10)));

        TanTanAdapter adapter = new TanTanAdapter(requestManager, urls);
        ItemTouchHelper.Callback callback = new RenRenCallback(rvTan, adapter, urls);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvTan);

        rvTan.setAdapter(adapter);

    }
}
