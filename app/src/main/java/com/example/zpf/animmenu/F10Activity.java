package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import adapter.F10HeaderAdapter;
import adapter.F10ValueAdapter;
import customview.FlowLayout;

@ContentView(R.layout.activity_f10)
public class F10Activity extends BaseActivity {

    @ViewInject(R.id.srl_f10)
    private SmartRefreshLayout refreshLayout;

    @ViewInject(R.id.flowLayout_f10)
    private FlowLayout flowLayout;

    @ViewInject(R.id.rv_head_f10)
    private RecyclerView rvHead;

    private F10HeaderAdapter adapterHead;

    @ViewInject(R.id.rv_value_f10)
    private RecyclerView rvValue;
    private F10ValueAdapter adapterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFlowLayout();

        initRefreshLayout();

        initRecyclerView();
    }

    private void initFlowLayout() {

        for (int i = 0; i < 10; i++) {

            TextView textView = new TextView(this, null, 0, R.style.TextViewTag);
            textView.setText("这个是标签" + i * 2 + "の名");
            textView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT));
            flowLayout.addView(textView);
        }

    }

    private void initRefreshLayout() {

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {

                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshlayout.finishLoadmore();
                    }
                }, 3000);
            }
        });
    }

    private void initRecyclerView() {

        ArrayList<String> models = new ArrayList<>();
        for (int i = 0; i < 17; i++) {

            models.add("故名月氏" + i);
        }

        rvHead.setLayoutManager(new LinearLayoutManager(this));
        rvHead.setHasFixedSize(true);

        adapterHead = new F10HeaderAdapter(this, models);
        rvHead.setAdapter(adapterHead);
        adapterHead.notifyDataSetChanged();

        rvValue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterValue = new F10ValueAdapter(this);
        rvValue.setAdapter(adapterValue);
        adapterValue.notifyDataSetChanged();

        LinearSnapHelper pagerSnapHelper = new LinearSnapHelper();
//        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvValue);
    }


}
