package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_constraint_layout)
public class ConstraintLayoutActivity extends BaseActivity {

    @ViewInject(R.id.appbar_layout)
    private AppBarLayout appBarLayout;

    @ViewInject(R.id.constraint_layout_coupon)
    private ConstraintLayout constraintLayoutCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Event(R.id.fab)
    private void fab(View view) {

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * 弹出RecyclerView
     */
    @Event(R.id.btn_one)
    private void clickBtnOne(View view) {

        showToast(view.getId());

        appBarLayout.setVisibility(View.GONE);
        constraintLayoutCoupon.setVisibility(View.VISIBLE);
    }

    /**
     * 收起RecyclerView
     */
    @Event(R.id.btn_coupon)
    private void clickBtnCoupon(View view) {

        appBarLayout.setVisibility(View.VISIBLE);
        constraintLayoutCoupon.setVisibility(View.GONE);
    }

}
