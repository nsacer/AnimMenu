package com.example.zpf.animmenu;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AlphaAnalyzeActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;

    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {

        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_analyze);

        initView();
    }

    private void initView() {

        initHeaderBar();

        initFooter();
    }

    private void initHeaderBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_alpha);
        setSupportActionBar(toolbar);

        TextView tvIntroduce = (TextView) findViewById(R.id.tv_introduce);
        tvIntroduce.setOnClickListener(this);
        
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Alpha量化分析");

        collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.colorBlackDark));
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorWhiteDark));

        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {

                    if (state != CollapsingToolbarLayoutState.EXPANDED) {

                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        toolbar.setNavigationIcon(R.drawable.selector_back_light);
                    }

                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {

                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                        toolbar.setNavigationIcon(R.drawable.selector_back_dark);
                    }

                } else {

                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {

                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {

//                            //由折叠变为中间状态时

                        } else if (state == CollapsingToolbarLayoutState.EXPANDED) {

                            //由展开变为中间状态
                        }

                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }

            }
        });

    }

    private void initFooter() {

        TextView tvDeadLine = (TextView) findViewById(R.id.tv_deadline);
        tvDeadLine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_introduce:

                startActivity(new Intent(this, CustomBehaviorActivity.class));
                break;

            case R.id.tv_deadline:

                startActivity(new Intent(this, RecyclerViewRefreshActivity.class));
                break;

            default:
                break;
        }
    }

}
