package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_constraint_layout)
public class ConstraintLayoutActivity extends BaseActivity {

    private ConstraintSet csSource = new ConstraintSet();
    private ConstraintSet csApply = new ConstraintSet();

    @ViewInject(R.id.content_constraint_layout)
    private ConstraintLayout constraintLayout;

    @ViewInject(R.id.rv_coupon)
    private RecyclerView rvCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {

        csSource.clone(constraintLayout);
        csApply.clone(constraintLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Event(R.id.fab)
    private void fab(View view) {

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Event(R.id.btn_one)
    private void clickBtnOne(View view) {

        showToast(view.getId());

        TransitionManager.beginDelayedTransition(constraintLayout);

        if (rvCoupon.getVisibility() == View.GONE) {

            csApply.setVisibility(R.id.rv_coupon, ConstraintSet.VISIBLE);
            csApply.applyTo(constraintLayout);

        }

    }

}
