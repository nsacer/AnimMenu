package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import customview.SparkView;

public class ActivitySpark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_spark);

//        ((SparkView) findViewById(R.id.sparkView)).run();
        SparkView sparkView = new SparkView(this);
        RelativeLayout rlRoot = findViewById(R.id.layout_spark);
        rlRoot.addView(sparkView, getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels / 2);
    }
}
