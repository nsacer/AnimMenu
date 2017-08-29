package model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/29.
 */

public class GroupModel {

    private String groupTitle;

    private ArrayList<GroupItemModel> itemModels;

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public ArrayList<GroupItemModel> getItemModels() {
        return itemModels;
    }

    public void setItemModels(ArrayList<GroupItemModel> itemModels) {
        this.itemModels = itemModels;
    }
}
