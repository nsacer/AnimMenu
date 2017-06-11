package com.example.zpf.animmenu;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import customview.CircleImageView;

public class CustomBehaviorActivity extends AppCompatActivity {

    private boolean bToolbarVisible = Boolean.FALSE;

    private boolean bExpendTitleVisible = Boolean.TRUE;

    private CoordinatorLayout coordinatorLayout;

    private AppBarLayout appbar;

    private CollapsingToolbarLayout collapsingToolbar;

    private FrameLayout frameLayoutHeader;

    private RelativeLayout layoutIntroduce;

    private ImageView ivCover;

    private TextView tvTitleToolbar;

    private Toolbar toolbar;

    private CircleImageView crvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_behavior);

        initView();
    }

    private void initView() {

        findView();

        initHeader();
    }

    private void findView() {

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_behavior);

        appbar = (AppBarLayout) findViewById(R.id.appbar_behavior);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_behavior);

        frameLayoutHeader = (FrameLayout) findViewById(R.id.frame_title);

        layoutIntroduce = (RelativeLayout) findViewById(R.id.layout_introduce);

        ivCover = (ImageView) findViewById(R.id.iv_cover_behavior);

        tvTitleToolbar = (TextView) findViewById(R.id.tv_title_toolbar_behavior);

        toolbar = (Toolbar) findViewById(R.id.toolbar_behavior);

        crvHeader = (CircleImageView) findViewById(R.id.crv_header_behavior);
    }

    private void initHeader() {

        toolbar.setTitle("");

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                int maxOffset = appBarLayout.getTotalScrollRange();

                float percent = Math.abs(verticalOffset) / maxOffset;

                handleAlphaOnTitle(percent);

                handleToolbarTitleVisibility(percent);

            }
        });

        initParallaxValues();
    }

    // 处理ToolBar的显示
    private void handleToolbarTitleVisibility(float percentage) {

        float percentShowToolbar = 0.9f;
        long durationAnim = 200;
        if (percentage >= percentShowToolbar) {
            if (!bToolbarVisible) {
                startAlphaAnimation(tvTitleToolbar, durationAnim, View.VISIBLE);
                bToolbarVisible = true;
            }
        } else {
            if (bToolbarVisible) {
                startAlphaAnimation(tvTitleToolbar, durationAnim, View.INVISIBLE);
                bToolbarVisible = false;
            }
        }
    }

    // 控制展开后居中标题Title的显示
    private void handleAlphaOnTitle(float percentage) {

        float percentHideExpendTitle = 0.3f;
        long durationAnim = 200;
        if (percentage >= percentHideExpendTitle) {
            if (bExpendTitleVisible) {
                startAlphaAnimation(layoutIntroduce, durationAnim, View.INVISIBLE);
                bExpendTitleVisible = false;
            }
        } else {
            if (!bExpendTitleVisible) {
                startAlphaAnimation(layoutIntroduce, durationAnim, View.VISIBLE);
                bExpendTitleVisible = true;
            }
        }
    }

    // 设置渐变的动画
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    // 设置自动滑动的动画效果
    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) ivCover.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) frameLayoutHeader.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        ivCover.setLayoutParams(petDetailsLp);
        frameLayoutHeader.setLayoutParams(petBackgroundLp);
    }


}
