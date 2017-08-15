package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_video)
public class VideoActivity extends BaseActivity {

    @ViewInject(R.id.rv_video)
    private RecyclerView rvVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRecyclerView();
    }

    private void initRecyclerView() {


    }
}
