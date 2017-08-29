package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import customview.nestfulllistview.NestFullListView;
import customview.nestfulllistview.NestFullListViewAdapter;
import customview.nestfulllistview.NestFullViewHolder;
import model.GroupItemModel;
import model.GroupModel;

public class GroupListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        initView();
    }

    private void initView() {

        //制造假数据
        ArrayList<GroupModel> groupModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            GroupModel groupModel = new GroupModel();
            String groupTitle = "第" + i + "组";
            groupModel.setGroupTitle(groupTitle);

            ArrayList<GroupItemModel> itemModels = new ArrayList<>();
            for (int j = 0; j < 5; j++) {

                GroupItemModel itemModel = new GroupItemModel();
                itemModel.setTitle(groupTitle + j + "分队");
                itemModel.setUrl("这是是url你晓得不?第" + j + "条");

                itemModels.add(itemModel);
            }

            groupModel.setItemModels(itemModels);
            groupModels.add(groupModel);
        }

        //NestFullListView的使用请参考Github，项目地址：https://github.com/mcxtzhang/NestFullListView
        NestFullListView nestFullListView = (NestFullListView) findViewById(R.id.nflv_group);
        nestFullListView.setAdapter(
                new NestFullListViewAdapter<GroupModel>(R.layout.item_group_nflv, groupModels) {
                    @Override
                    public void onBind(int pos, GroupModel groupModel, NestFullViewHolder holder) {

                        holder.setText(R.id.cb_nflv, groupModel.getGroupTitle());

                        final NestFullListView nflvInner = holder.getView(R.id.nflv_inner);

                        final List<GroupItemModel> itemModels = groupModel.getItemModels();
                        nflvInner.setAdapter(getInnerAdapter(itemModels, false));

                        CheckBox cb = holder.getView(R.id.cb_nflv);
                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                nflvInner.setAdapter(getInnerAdapter(itemModels, b));
                            }
                        });

                    }
                });

    }

    /**
     * 重新获取要设置显示的Adapter
     *
     * @param isChecked 展开、收缩CheckBox是否选中,默认不展开
     */
    private NestFullListViewAdapter<GroupItemModel> getInnerAdapter(
            List<GroupItemModel> models, boolean isChecked) {

        if (models == null)
            return null;

        //展开显示全部数据，未展开显示两条数据
        List<GroupItemModel> showModels = isChecked ? models :
                (models.size() > 2 ? models.subList(0, 2) : models);

        NestFullListViewAdapter<GroupItemModel> adapter =
                new NestFullListViewAdapter<GroupItemModel>(R.layout.item_group_item_nflv, showModels) {
                    @Override
                    public void onBind(int pos, GroupItemModel groupItemModel, NestFullViewHolder holder) {

                        TextView tv = holder.getView(R.id.tv_group_inner);
                        tv.setText(groupItemModel.getTitle());

                        final String url = groupItemModel.getUrl();
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Toast.makeText(GroupListActivity.this, url, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };

        return adapter;
    }
}
