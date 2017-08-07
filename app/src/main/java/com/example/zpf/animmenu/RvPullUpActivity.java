package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;

import adapter.RecyclerEasySwipeMenuAdapter;

@ContentView(R.layout.activity_rv_pull_up)
public class RvPullUpActivity extends BaseActivity {

    private final String[] urls = new String[]{

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

    @ViewInject(R.id.refresh_pull_item_delete)
    private SmartRefreshLayout refreshLayout;

    @ViewInject(R.id.rv_pull_item_delete)
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRefreshLayout();

        initRecyclerView();
    }

    private void initRefreshLayout() {

        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                refreshlayout.finishLoadmore(2000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(2000);
            }
        });
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(Arrays.asList(urls));

        RecyclerEasySwipeMenuAdapter adapter = new RecyclerEasySwipeMenuAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setStrings(strings);

        adapter.setOnDeleteBtnClickListener(new RecyclerEasySwipeMenuAdapter.OnDeleteBtnClickListener() {
            @Override
            public void OnDeleteBtnClick(EasySwipeMenuLayout swipeLayout, String content) {

                showToast(content);
                swipeLayout.resetStatus();
            }
        });
    }
}
