package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_constraint_layout)
public class ConstraintLayoutActivity extends BaseActivity {

    @ViewInject(R.id.coordinator_layout_root)
    private CoordinatorLayout coordinatorLayout;

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

        View viewContent = LayoutInflater.from(this).inflate(R.layout.alert_coupon, null);
        PopupWindow popupWindow = new PopupWindow(viewContent,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindowBackgroundAlpha(0.2f);
        popupWindow.setAnimationStyle(R.style.PopWindowAnimation);
        popupWindow.showAtLocation(coordinatorLayout, Gravity.BOTTOM, 0, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                popupWindowBackgroundAlpha(1f);
            }
        });

    }

    private void popupWindowBackgroundAlpha(float alpha) {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    private void showPopupWindow() {

//        if ()
    }

}
