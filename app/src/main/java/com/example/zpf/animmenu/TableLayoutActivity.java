package com.example.zpf.animmenu;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class TableLayoutActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout);

        initView();

    }

    private void initView() {

        initBtn();

        initTableLayout();
    }

    private void initBtn() {

        Button btn = (Button) findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayout.addView(createTextView("你的背包（110236）"));
            }
        });
    }

    private void initTableLayout() {

        String[] sTitles = new String[] {"四川飞马（100112）",
        "四川黑马（112113）", "重庆火锅（332112）", "通州火锅（112114）"};

        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        for (int i=0; i<sTitles.length; i++) {

            linearLayout.addView(createTextView(sTitles[i]), i);
        }

    }

    /** 创建TextView */
    private View createTextView(String title) {

        TextView tv = new TextView(this);
        tv.setSingleLine(true);
        tv.setBackgroundColor(Color.GRAY);
        tv.setText(title);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(22);
        tv.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setMargins(dp2px(30), dp2px(16) , dp2px(30), dp2px(16));

        tv.setLayoutParams(ll);

        return tv;
    }

    private int dp2px(float dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
