package com.example.zpf.animmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import customview.SparkView;

public class ActivitySpark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_spark);

        SparkView sparkView = new SparkView(this);

        RelativeLayout layoutSpark = (RelativeLayout) findViewById(R.id.layout_spark);
        layoutSpark.addView(sparkView);

    }
}
