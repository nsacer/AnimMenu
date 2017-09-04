package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zpf.animmenu.R;

import java.util.ArrayList;

import model.GroupItemModel;

/**
 * Created by zpf on 2017/9/4.
 */

public class RvGroupAdapter extends RecyclerView.Adapter<RvGroupAdapter.GroupViewHolder> {

    private Context mContext;
    private ArrayList<GroupItemModel> models = new ArrayList<>();

    public RvGroupAdapter(Context context) {

        this.mContext = context;
    }

    public ArrayList<GroupItemModel> getModels() {
        return models;
    }

    public void setModels(ArrayList<GroupItemModel> models) {

        if (models != null && !models.isEmpty()) {

            this.models = models;
            notifyDataSetChanged();
        }
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {

        GroupItemModel model = models.get(position);
        holder.textView.setText(model.getUrl());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public GroupViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_group_item);
        }
    }
}
