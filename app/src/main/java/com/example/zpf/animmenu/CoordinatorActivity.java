package com.example.zpf.animmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import adapter.MainPagerAdapter;
import customview.FlowLayout;
import fragment.LiveFragment1;
import fragment.LiveFragment2;
import fragment.LiveFragment3;

/**
 * Created by zpf on 2016/8/10.
 */
public class CoordinatorActivity extends AppCompatActivity {

    private SpannableStringBuilder builder;
    private TextSwitcher switcher;
    private Timer timer;

    private FlowLayout layoutFlow;

    /**
     * handler接受不同类型消息处理
     * @param 0 :切换轮播消息
     *
     * */
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0)
                switcher.setText(builder.append("L"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout);

        initView();

    }

    private void initView(){

        initToolbar();

        initTabLayout();

        initHeadView();

        initTag();
    }

    private void initToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    private void initTabLayout(){

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);

        initViewPager(pager);

        tabLayout.setupWithViewPager(pager);

    }

    private void initViewPager(ViewPager pager){

        LiveFragment1 fragment1 = new LiveFragment1();
        LiveFragment2 fragment2 = new LiveFragment2();
        LiveFragment3 fragment3 = new LiveFragment3();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);

        pager.setAdapter(adapter);
    }


    private void initHeadView(){

        builder = new SpannableStringBuilder("管送直播室开盘了");

        switcher = (TextSwitcher) findViewById(R.id.ts_scroll);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return createTextViewScroll();
            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                handler.sendEmptyMessage(0);
            }
        }, 6000, 6000);

    }

    private void initTag(){

        layoutFlow = (FlowLayout) findViewById(R.id.layout_tag);

        TextView textView = new TextView(this, null, 0, R.style.TextViewTag);
        textView.setText("tamendoushuo");
        textView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT));
        layoutFlow.addView(textView);

        TextView tv = (TextView) findViewById(R.id.tv_tag_one);
        tv.setText("纯手工输入测试动态改变TextView");
    }

    private TextView createTextViewScroll(){

        TextView tv = new TextView(this);
        tv.setSingleLine();
        tv.setText("管送直播室开盘了");
        tv.setTextSize(14);
        tv.setTextColor(Color.YELLOW);
        tv.setIncludeFontPadding(false);

        return tv;

    }

    @Override
    protected void onDestroy() {
        if (timer != null)
            timer.cancel();
        super.onDestroy();
    }
}
