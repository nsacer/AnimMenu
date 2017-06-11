package com.example.zpf.animmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import customview.LoadingView;

public class CustomLoadingActivity extends AppCompatActivity implements View.OnClickListener{

    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_loading);

        initView();
    }

    private void initView() {

        TextView tvVisible = (TextView) findViewById(R.id.tv_visible);
        tvVisible.setOnClickListener(this);
        TextView tvGone = (TextView) findViewById(R.id.tv_gone);
        tvGone.setOnClickListener(this);

        loadingView = (LoadingView) findViewById(R.id.load_view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_visible:

                loadingView.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_gone:

                loadingView.setVisibility(View.GONE);
                break;
        }
    }
}
