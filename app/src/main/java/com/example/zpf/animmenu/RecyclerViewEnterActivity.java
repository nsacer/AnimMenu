package com.example.zpf.animmenu;

import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_recycler_view_enter)
public class RecyclerViewEnterActivity extends BaseActivity {

    @Event(R.id.btn_enter_anim)
    private void goEnterAnimAct(View view) {

        openActivity(GridViewActivity.class, view);
    }

    @Event(R.id.btn_count_switch)
    private void goCountSwitchAct(View view) {

        openActivity(RecyclerViewSwitchActivity.class, view);
    }

    @Event(R.id.btn_circle_refresh)
    private void goRecyclerRefresh(View view) {

        openActivity(RecyclerViewRefreshActivity.class, view);
    }

    @Event(R.id.btn_recycler_snap)
    private void goRecyclerSnap(View view) {

        openActivity(RecyclerSnapHelperActivity.class, view);
    }

    @Event(R.id.btn_recycler_horizontal)
    private void goRecyclerHorizontal(View view) {

        openActivity(RecyclerRemoveItemActivity.class, view);
    }

    @Event(R.id.btn_recycler_head_foot)
    private void goRvHeadFoot(View view) {

        openActivity(RecyclerHeadFootActivity.class, view);
    }

    @Event(R.id.btn_recycler_load_more)
    private void goLoadMore(View view) {

        openActivity(RecyclerItemClickActivity.class, view);
    }

    @Event(R.id.btn_recycler_swipe_press)
    private void goRecyclerAll(View view) {

        openActivity(RecyclerSwipeDragActivity.class, view);
//        openActivity(RecyclerAllActivity.class, view);
    }

    @Event(R.id.btn_recycler_prize)
    private void goPrizeInfo(View view) {

        openActivity(RecyclerPrizeInfoActivity.class, view);
    }

    @Event(R.id.btn_rv_pull_up)
    private void goPullToUpAct(View view) {

        openActivity(RvPullUpActivity.class, view);
    }

    @Event(R.id.btn_full_list_view)
    private void goFullListView(View view) {

        openActivity(GroupListActivity.class, view);
    }

}
