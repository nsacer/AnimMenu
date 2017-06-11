package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import adapter.RecyclerAllAdapter;

@ContentView(R.layout.activity_recycler_all)
public class RecyclerAllActivity extends BaseActivity {

    @ViewInject(R.id.rv_all)
    private RecyclerView rvAll;

    private RecyclerAllAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRecyclerAll();
    }

    private void initRecyclerAll() {

        rvAll.setHasFixedSize(true);

        adapter = new RecyclerAllAdapter(this);
        adapter.setOnItemClickListener(new RecyclerAllAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view) {


            }
        });
    }
}
