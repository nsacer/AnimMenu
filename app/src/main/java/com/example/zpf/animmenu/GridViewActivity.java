package com.example.zpf.animmenu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

import adapter.GridRecyclerViewAdapter;
import customview.GridRecyclerView;
import listener.listener.SimpleItemTouchHelperCallback;
import listener.listener.StartDragListener;

public class GridViewActivity extends AppCompatActivity implements StartDragListener{


    private GridRecyclerView gridRecyclerView;

    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        initView();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void initView() {

        findView();
    }

    private void findView() {

        gridRecyclerView = (GridRecyclerView) findViewById(R.id.grid_recycler);

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        gridRecyclerView.setLayoutManager(manager);
        gridRecyclerView.setHasFixedSize(true);

        GridRecyclerViewAdapter adapter = new GridRecyclerViewAdapter(this, Arrays.asList(urls));
        gridRecyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(gridRecyclerView);

    }



    private String[] urls = new String[]{
            "http://hbimg.b0.upaiyun.com/6293fd60a2597a6017633e3c8e3816d89b70dc2165ad9-jkFvRh_fw658",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160807.jpg",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160826.jpg",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160848.jpg",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160908.jpg",
            "http://icon.nipic.com/BannerPic/20160426/photo/20160426160926.jpg",
            "http://pic94.nipic.com/file/20160409/11284670_185122899000_2.jpg",
            "http://pic94.nipic.com/file/20160403/22743169_220209251000_2.jpg",
            "http://pic94.nipic.com/file/20160410/18807750_102028863000_2.jpg",
            "http://pic94.nipic.com/file/20160406/22743169_234848884000_2.jpg",
            "http://pic94.nipic.com/file/20160406/19700831_040444643000_2.jpg",
            "http://pic94.nipic.com/file/20160321/7874840_094355922000_2.jpg",
            "http://pic94.nipic.com/file/20160406/22743169_233812263000_2.jpg",
            "http://pic94.nipic.com/file/20160407/21544848_224025191000_2.jpg",
            "http://pic94.nipic.com/file/20160401/5189591_135724196000_2.jpg",
            "http://pic94.nipic.com/file/20160403/8572479_084616112000_2.jpg",
            "http://pic94.nipic.com/file/20160405/17961491_161210706000_2.jpg",
            "http://pic94.nipic.com/file/20160402/20020614_152846001000_2.jpg",
            "http://pic94.nipic.com/file/20160409/20383858_105135352396_2.jpg",
            "http://pic94.nipic.com/file/20160329/17961491_164127144000_2.jpg",
            "http://pic94.nipic.com/file/20160406/7977744_213422625000_2.jpg",
            "http://pic93.nipic.com/file/20160405/9413594_234307716000_2.jpg",
            "http://pic94.nipic.com/file/20160403/17961491_083818949000_2.jpg",
            "http://pic94.nipic.com/file/20160406/22743169_234450344000_2.jpg",
            "http://pic94.nipic.com/file/20160407/7874840_203749124000_2.jpg",
            "http://pic94.nipic.com/file/20160326/20614752_115114920000_2.jpg",
            "http://pic250.qjimage.com/mf093/mf829-06753088.jpg",
            "http://pic250.qjimage.com/mf056/mf700-03017590.jpg",
            "http://pic250.qjimage.com/mf088/mf829-06512042.jpg",
            "http://pic250.qjimage.com/ing012/ing-02e91590.jpg",
            "http://pic250.qjimage.com/west006/gnf01142.jpg",
            "http://pic250.qjimage.com/top025/top-832792.jpg",
            "http://pic250.qjimage.com/age_foto39/l28-727549.jpg",
            "http://pic250.qjimage.com/top017/top-703786.jpg",
            "http://pic250.qjimage.com/caia_rf002/412-10570.jpg",
            "http://pic250.qjimage.com/mf083/mf821-05789764.jpg",
            "http://pic250.qjimage.com/mf093/mf829-06753077.jpg",
            "http://pic250.qjimage.com/top034/top-930776.jpg",
            "http://pic250.qjimage.com/ing012/ing-02e91395.jpg",
            "http://pic250.qjimage.com/ibrf010/ibxbai00060130.jpg",
            "http://pic250.qjimage.com/top017/top-703675.jpg",
            "http://pic250.qjimage.com/mf016/mf700-00190335.jpg",
            "http://pic250.qjimage.com/ing012/ing-02e93099.jpg",
            "http://pic250.qjimage.com/top017/top-703711.jpg",
            "http://pic250.qjimage.com/is_rm001/is0990hp2.jpg",
            "http://pic250.qjimage.com/mf093/mf829-06935949.jpg",
            "http://pic250.qjimage.com/top023/top-810215.jpg",
            "http://pic250.qjimage.com/cultura_rm001/is09ab7dc.jpg",
            "http://pic250.qjimage.com/mf064/mf866-03583400.jpg",
            "http://pic250.qjimage.com/age_foto41/x4c-909772.jpg",
            "http://pic250.qjimage.com/rob_pre003/rob-791-21.jpg",
            "http://pic250.qjimage.com/amana_rf005/11017002444.jpg",
            "http://pic250.qjimage.com/glow_bot001/gbr177871121.jpg",
            "http://pic250.qjimage.com/ing012/ing-02e93102.jpg",
            "http://pic250.qjimage.com/tongro_rf003/trs052ca0056.jpg",
            "http://pic250.qjimage.com/ing012/ing-02e92246.jpg",
            "http://pic250.qjimage.com/mf063/mf866-03578910.jpg",
            "http://pic250.qjimage.com/chineseview032/tpgrf-px040085.jpg",
            "http://pic250.qjimage.com/mf057/mf700-02885862.jpg",
            "http://pic250.qjimage.com/top014/top-664862.jpg",
            "http://pic250.qjimage.com/is005/is0266k0u.jpg",
            "http://pic250.qjimage.com/chineseview054/tpgrf-fncsrf_a06361.jpg",
            "http://pic250.qjimage.com/Chineseview003/1n-14122.jpg",
            "http://pic250.qjimage.com/chineseview054/tpgrf-fncsrf_a06361.jpg",
            "http://pic250.qjimage.com/ibrm018/iblrdi00007232.jpg",
            "http://pic250.qjimage.com/culturarm002/42tew0111rm.jpg",
            "http://pic250.qjimage.com/caia_rf002/412-10562.jpg",
            "http://pic250.qjimage.com/caia_rf001/412-03675.jpg",
            "http://pic250.qjimage.com/chineseview054/tpgrf-fncsrf_a05063.jpg",
            "http://pic250.qjimage.com/cultura010/51mhe0018rf.jpg",
            "http://pic250.qjimage.com/chineseview075/east-ep-a51-1901934.jpg",
            "http://pic250.qjimage.com/alamy001/aaby0d.jpg"
    };

    @Override
    public void onStartDrag(RecyclerView.ViewHolder holder) {

        itemTouchHelper.startDrag(holder);
    }
}
