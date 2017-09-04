package com.example.zpf.animmenu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.listener.PowerGroupListener;

import java.util.ArrayList;

import adapter.RvGroupAdapter;
import model.GroupItemModel;
import model.GroupModel;
import utils.DisplayUtil;

public class RvGroupTitleActivity extends AppCompatActivity {

    private ArrayList<GroupItemModel> groupModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_group_title);

        initView();
    }

    private void initView() {

        initRecyclerView();
    }

    private void initRecyclerView() {

        createData();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_group_title);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


        RvGroupAdapter adapterGroup = new RvGroupAdapter(this);
        recyclerView.setAdapter(adapterGroup);

        adapterGroup.setModels(groupModels);

        recyclerView.addItemDecoration(getDecoration());
    }

    /**
     * 制造假数据
     */
    private void createData() {

        for (int i=0; i<20; i++) {

            GroupItemModel model = new GroupItemModel();

            String title = "默认的标题";
            if (i < 4)
                title = "标题1组队";
            else if (i < 8)
                title = "标题2组队";
            else if (i < 12)
                title = "标题3组队";
            else if (i < 16)
                title = "标题4组队";
            else
                title = "标题5组队";

            model.setTitle(title);
            model.setUrl("给定的ur" + i + "条");

            groupModels.add(model);
        }
    }

    private PowerfulStickyDecoration getDecoration() {

        PowerGroupListener groupListener = new PowerGroupListener() {
            @Override
            public String getGroupName(int position) {
                return groupModels.get(position).getTitle();
            }

            @Override
            public View getGroupView(int position) {

                View decorationView = getLayoutInflater().inflate(R.layout.item_group_title, null, false);
                ((TextView) decorationView.findViewById(R.id.tv_group_title)).setText(getGroupName(position));
                return decorationView;
            }
        };

        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(groupListener)
                .setGroupHeight(DisplayUtil.dip2px(this, 40))
                .setDivideHeight(2)
                .setDivideColor(Color.parseColor("#dadada"))
                .build();

        return  decoration;
    }


}
