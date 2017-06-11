package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;

import adapter.RecyclerDeleteItemAdapter;
import listener.listener.SimpleItemTouchHelperCallback;

@ContentView(R.layout.activity_recycler_remove_item)
public class RecyclerRemoveItemActivity extends BaseActivity {

    /**
     * 用于更新内容的url集合
     */
    private ArrayList<String> updateUrls = new ArrayList<>();
    private int updateIndex = 0;

    private RecyclerDeleteItemAdapter adapter;

    @ViewInject(R.id.recycler)
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        initRecycler();
    }

    private void initData() {

        updateUrls.addAll(Arrays.asList(Arrays.copyOf(getResources().getStringArray(R.array.picUrls), 5)));
    }

    private void initRecycler() {

        ArrayList<String> urls = new ArrayList<>();
        urls.addAll(Arrays.asList(Arrays.copyOf(getResources().getStringArray(R.array.picUrls), 5)));

        adapter = new RecyclerDeleteItemAdapter(this, urls);
        adapter.setOnItemClickListener(new RecyclerDeleteItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String url, int position) {

                Toast.makeText(RecyclerRemoveItemActivity.this, url, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new
                SimpleItemTouchHelperCallback(adapter, true, true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Event(R.id.tv_update_content)
    private void updateContent(View view) {

        String url = updateUrls.get(updateIndex % updateUrls.size());
        Log.i("====", url);
        adapter.updateContent(url);
        updateIndex++;
    }

}
