package com.example.zpf.animmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import adapter.RvAdapterHeadFoot;

public class RecyclerHeadFootActivity extends AppCompatActivity {

    private final String[] urls = {
            "http://hbimg.b0.upaiyun.com/6293fd60a2597a6017633e3c8e3816d89b70dc2165ad9-jkFvRh_fw658",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160807.jpg",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160826.jpg",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160848.jpg",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160908.jpg",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160926.jpg",
            "http://pic94.nipic.com/file/20160409/11284670_185122899000_2.jpg",
            "http://pic94.nipic.com/file/20160403/22743169_220209251000_2.jpg",
            "http://pic94.nipic.com/file/20160410/18807750_102028863000_2.jpg",
            "http://pic94.nipic.com/file/20160406/22743169_234848884000_2.jpg",
            "http://pic94.nipic.com/file/20160406/19700831_040444643000_2.jpg",
            "http://pic94.nipic.com/file/20160321/7874840_094355922000_2.jpg",
            "http://pic94.nipic.com/file/20160406/22743169_233812263000_2.jpg",
            "http://pic94.nipic.com/file/20160407/21544848_224025191000_2.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_head_foot);

        initView();
    }

    private void initView() {

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_head_foot);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(Arrays.asList(urls));

        RvAdapterHeadFoot adapter = new RvAdapterHeadFoot(this);
        adapter.setModels(strings);

        rv.setAdapter(adapter);
    }
}
