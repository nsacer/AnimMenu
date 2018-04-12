package com.example.zpf.animmenu;

import android.os.Bundle;
import android.widget.Toast;

import customview.StockInfoTabView;

public class TabBarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bar);

        initView();
    }

    private void initView() {

        StockInfoTabView tabView = findViewById(R.id.tabView);
        tabView.setmTitles(new String[]{"头条", "抖音", "段子", "火山", "西瓜"});
        tabView.setOnTabChangeListener(new StockInfoTabView.onTabChangeListener() {
            @Override
            public void OnTabChanged(int index) {

                Toast.makeText(TabBarActivity.this, String.valueOf(index), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
