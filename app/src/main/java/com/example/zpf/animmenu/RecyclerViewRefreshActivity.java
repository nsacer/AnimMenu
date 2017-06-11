package com.example.zpf.animmenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;

import adapter.RvCircleRefreshAdapter;
import listener.listener.SimpleItemTouchHelperCallback;
import listener.listener.StartDragListener;

@ContentView(R.layout.activity_circle_refresh)
public class RecyclerViewRefreshActivity extends BaseActivity implements StartDragListener {

    @ViewInject(R.id.recycler_circle_refresh)
    private RecyclerView recyclerView;

    private String[] strs = {
            "The",
            "Canvas",
            "class",
            "holds",
            "the",
            "draw",
            "calls",
            ".",
            "To",
            "draw",
            "something,",
            "you",
            "need",
            "4 basic",
            "components",
            "Bitmap",
    };

    private ItemTouchHelper itemTouchHelper;

    /**
     * 水滴加载layout
     */
    @ViewInject(R.id.circle_refresh_layout)
    private CircleRefreshLayout refreshLayout;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
                refreshLayout.finishRefreshing();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        initRecyclerView();

        initCircleRefreshLayout();

    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        RvCircleRefreshAdapter adapter = new RvCircleRefreshAdapter(this, Arrays.asList(strs), this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new RvCircleRefreshAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, String string) {

                Toast.makeText(RecyclerViewRefreshActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCircleRefreshLayout() {

        refreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {

                handler.sendEmptyMessageDelayed(0, 3000);
            }
        });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder holder) {

        itemTouchHelper.startDrag(holder);
    }
}
