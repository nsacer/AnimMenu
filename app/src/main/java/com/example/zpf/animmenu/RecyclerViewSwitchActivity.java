package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Arrays;

import adapter.RecyclerViewSwitchAdapter;

public class RecyclerViewSwitchActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private GridLayoutManager manager;
    private RecyclerViewSwitchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_switch);

        initView();
    }

    private void initView() {

        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.switch_count);
        switchCompat.setOnCheckedChangeListener(this);

        initRecyclerView();
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_count_switch);

        manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        ArrayList<String> urls = new ArrayList<>();
        urls.addAll(Arrays.asList(getResources().getStringArray(R.array.picUrls)));
        adapter = new RecyclerViewSwitchAdapter(this, urls);

        recyclerView.setAdapter(adapter);

        changeShowItemCount(1);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int count = b ? 3 : 1;
        changeShowItemCount(count);
    }

    /**
     * 更改每行显示数目
     */
    private void changeShowItemCount(int count) {

        final int i = 4 - count;
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return i;
            }
        });
        adapter.notifyDataSetChanged();
    }
}
