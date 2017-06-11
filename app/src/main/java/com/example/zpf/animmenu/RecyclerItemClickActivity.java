package com.example.zpf.animmenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import adapter.RvAdapterItemClick;

public class RecyclerItemClickActivity extends BaseActivity {

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

    /**
     * 模拟下拉的handler message what
     * */
    private final int MSG_DATA_DOWN = 0;

    /**
     * 模拟上拉加载更多的handler message what
     * */
    private final int MSG_DATA_UP = 1;

    private SwipeRefreshLayout refreshLayout;
    private RvAdapterItemClick adapterItemClick;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            doMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item_click);

        initView();
    }

    private void initView() {

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_item_click);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshLayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(MSG_DATA_DOWN, 3000);
            }
        });

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_item_click);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastPosition = manager.findLastVisibleItemPosition();
                View viewLast = manager.findViewByPosition(lastPosition);

                //viewFoot.getBottom() == recyclerView.getBottom() 表示已经滑动到最底部
                if (viewLast != null && viewLast.getBottom() == recyclerView.getBottom() &&
                        !adapterItemClick.isLoading()) {

                    //将加载数据状态设置true，防止多次加载，在doMessage里将值重置
                    adapterItemClick.setLoading(true);

                    //模拟三秒后获取到数据
                    handler.sendEmptyMessageDelayed(MSG_DATA_UP, 3000);
                }
            }
        });

        adapterItemClick = new RvAdapterItemClick(this);
        rv.setAdapter(adapterItemClick);

        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(Arrays.asList(urls));
        adapterItemClick.setModels(strings);

        adapterItemClick.setOnItemClickListener(new RvAdapterItemClick.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, String url) {

                //RecyclerView的ItemClick点击事件的处理
                Toast.makeText(RecyclerItemClickActivity.this, String.valueOf(position),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 模拟网络请求数据返回结果处理
     * */
    private void doMessage(Message msg) {

        //模拟下拉数据反馈处理
        if (msg.what == MSG_DATA_DOWN) {

            refreshLayout.setRefreshing(false);

            ArrayList<String> urlList = new ArrayList<>();
            urlList.addAll(Arrays.asList(urls));
            adapterItemClick.setModels(urlList);

        }
        //模拟上拉数据反馈处理
        else if (msg.what == MSG_DATA_UP) {

            adapterItemClick.setLoading(false);

            ArrayList<String> strings = new ArrayList<>();
            strings.addAll(Arrays.asList(urls));
            adapterItemClick.appendModels(strings);
        }
    }
}
