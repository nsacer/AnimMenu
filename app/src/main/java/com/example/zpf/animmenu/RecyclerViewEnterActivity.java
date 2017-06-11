package com.example.zpf.animmenu;

import android.content.Intent;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_recycler_view_enter)
public class RecyclerViewEnterActivity extends BaseActivity {


    @Event(R.id.btn_circle_refresh)
    private void goRecyclerRefresh(View view) {

        startActivity(new Intent(this, RecyclerViewRefreshActivity.class));
    }

    @Event(R.id.btn_recycler_snap)
    private void goRecyclerSnap(View view) {

        startActivity(new Intent(this, RecyclerSnapHelperActivity.class));
    }

    @Event(R.id.btn_recycler_horizontal)
    private void goRecyclerHorizontal(View view) {

        startActivity(new Intent(this, RecyclerRemoveItemActivity.class));
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

}
