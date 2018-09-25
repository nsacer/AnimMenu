package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zpf.animmenu.R;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;

import java.util.ArrayList;

/**
 * Created by zpf on 2017/8/7.
 * 横滑删除的Adapter
 */

public class RecyclerEasySwipeMenuAdapter extends
        RecyclerView.Adapter<RecyclerEasySwipeMenuAdapter.EasySwipeViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private ArrayList<String> strings = new ArrayList<>();
    private OnDeleteBtnClickListener onDeleteBtnClickListener;

    public RecyclerEasySwipeMenuAdapter(Context context) {

        this.mContext = context;
    }

    public void setStrings(ArrayList<String> list) {

        if (list != null) {

            this.strings = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {

        if (onDeleteBtnClickListener != null) {

            onDeleteBtnClickListener.OnDeleteBtnClick((EasySwipeMenuLayout) view.getTag(R.id.easy_swipe_layout),
                    (String) view.getTag(R.id.tv_delete_easy_swipe));
        }
    }

    public void setOnDeleteBtnClickListener(OnDeleteBtnClickListener onDeleteBtnClickListener) {
        this.onDeleteBtnClickListener = onDeleteBtnClickListener;
    }

    @Override
    public EasySwipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_easy_swipe_menu, parent, false);
        TextView tvDelete = (TextView) view.findViewById(R.id.tv_delete_easy_swipe);
        tvDelete.setOnClickListener(this);
        return new EasySwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EasySwipeViewHolder holder, int position) {

        String url = strings.get(position);
        holder.tvDelete.setTag(R.id.easy_swipe_layout, holder.swipeLayout);
        holder.tvDelete.setTag(R.id.tv_delete_easy_swipe, url);
        holder.tvContent.setText(url);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public interface OnDeleteBtnClickListener {

        void OnDeleteBtnClick(EasySwipeMenuLayout swipeLayout, String content);
    }

    class EasySwipeViewHolder extends RecyclerView.ViewHolder {

        private EasySwipeMenuLayout swipeLayout;
        private TextView tvContent, tvDelete;

        EasySwipeViewHolder(View itemView) {
            super(itemView);

            swipeLayout = itemView.findViewById(R.id.easy_swipe_layout);
            tvContent = itemView.findViewById(R.id.tv_content_easy_swipe);
            tvDelete = itemView.findViewById(R.id.tv_delete_easy_swipe);
        }
    }
}
