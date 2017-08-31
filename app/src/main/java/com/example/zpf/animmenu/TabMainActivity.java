package com.example.zpf.animmenu;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class TabMainActivity extends TabActivity implements TabHost.OnTabChangeListener {

    private TabHost tabHost;
    private ArrayList<TextView> tvsTab = new ArrayList<>();
    private int[] drawablesTab = new int[]{
            R.mipmap.ask_help, R.mipmap.back_arrow, R.mipmap.ic_3d_rotation_black_48dp,
            R.mipmap.ic_accessibility_black_48dp, R.mipmap.ic_account_balance_black_48dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        initView();
    }

    private void initView() {

        tabHost = getTabHost();

//        setIndicator("hi",
//                ContextCompat.getDrawable(this, R.mipmap.ask_help))
//                .setContent(new Intent(this, TableLayoutActivity.class));
        TabHost.TabSpec tabSpec0 = tabHost.newTabSpec("one").setIndicator(getTabItem())
                .setContent(new Intent(this, TableLayoutActivity.class));

        //"hello",ContextCompat.getDrawable(this, R.mipmap.back_arrow)
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("two").setIndicator(getTabItem())
                .setContent(new Intent(this, TurnTableActivity.class));

//        "nihao",
//                ContextCompat.getDrawable(this, R.mipmap.ic_accessibility_black_48dp)
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("three").setIndicator(getTabItem())
                .setContent(new Intent(this, RvPullUpActivity.class));

        //"wanan",
//        ContextCompat.getDrawable(this, R.mipmap.ic_accessible_white_24dp)
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("four").setIndicator(getTabItem())
                .setContent(new Intent(this, RecyclerViewSwitchActivity.class));

        //"zao",
//        ContextCompat.getDrawable(this, R.mipmap.ic_account_balance_wallet_black_48dp)
        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("five").setIndicator(getTabItem())
                .setContent(new Intent(this, DanmuKuActivity.class));

        tabHost.addTab(tabSpec0);
        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);
        tabHost.addTab(tabSpec4);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {

            ImageView imageView = (ImageView) tabHost.getTabWidget().getChildAt(i)
                    .findViewById(R.id.iv_tab);
            imageView.setImageResource(drawablesTab[i]);
            TextView textView = (TextView) tabHost.getTabWidget().getChildAt(i)
                    .findViewById(R.id.tv_tab);
            textView.setText("Ta" + i);
            tvsTab.add(textView);
        }

        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.setOnTabChangedListener(this);

    }

    private View getTabItem() {

        return LayoutInflater.from(this).inflate(R.layout.item_tab_act_tab, null);
    }

    @Override
    public void onTabChanged(String s) {

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {

            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.DKGRAY);
            tvsTab.get(i).setTextColor(Color.WHITE);
        }

        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.GREEN);
        tvsTab.get(tabHost.getCurrentTab()).setTextColor(Color.BLACK);
    }
}
