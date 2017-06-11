package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zpf.animmenu.R;

import java.util.ArrayList;

/**
 * 下拉刷新，上拉加载更多，ItemClickListener
 */

public class RvAdapterItemClick extends RecyclerView.Adapter<RvAdapterItemClick.ItemClickViewHolder>
        implements View.OnClickListener{

    private final int TYPE_FOOT = -1;
    private final int TYPE_ITEM = 0;
    /**
     * 规定每次加载更多返回数据为10条，如果数据count < 10则说明没有更多数据了，反之则有更多数据
     * */
    private final int COUNT_LOAD_MORE = 10;

    private Context mContext;
    private ArrayList<String> strings = new ArrayList<>();
    private OnItemClickListener listener;
    private View footView;
    /**
     * 用来记录RecyclerView是否正在加载数据，防止多次请求数据
     * */
    private boolean isLoading = false;
    /**
     * 记录是否还有更多数据，用来设置Foot——View显示与否
     * */
    private boolean hasMoreData = false;

    public RvAdapterItemClick(Context context) {

        this.mContext = context;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public void onClick(View view) {

        if (listener != null) {

            listener.OnItemClick(view, (Integer) view.getTag(R.id.iv_item_head_foot),
                    (String) view.getTag(R.layout.item_head_foot_rv));
        }
    }


    public interface OnItemClickListener{

        void OnItemClick(View view, int position, String url);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }

    public void setModels(ArrayList<String> strings) {

        if (strings != null && !strings.isEmpty()) {

            this.strings = strings;
            hasMoreData = !(strings.size() < COUNT_LOAD_MORE);
            notifyDataSetChanged();
        }
    }

    public void appendModels(ArrayList<String> strings) {

        if (strings != null && !strings.isEmpty()) {

            this.strings.addAll(strings);
            hasMoreData = !(strings.size() < COUNT_LOAD_MORE);

        } else
            hasMoreData = false;

        setFootView(hasMoreData);

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == strings.size() ? TYPE_FOOT : TYPE_ITEM;
    }

    @Override
    public ItemClickViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view;
        if (viewType == TYPE_FOOT) {

            view = footView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_foot_rv, viewGroup, false);
            setFootView(hasMoreData);

        } else {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_head_foot_rv, viewGroup, false);
            view.setOnClickListener(this);

        }
        return new ItemClickViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ItemClickViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_FOOT)
            return;

        String url = strings.get(position);
        //通过setTag将该item对应的url信息保存，用于item点击事件Toast显示
        holder.itemView.setTag(R.layout.item_head_foot_rv, url);
        holder.itemView.setTag(R.id.iv_item_head_foot, position);
        holder.tv.setText(url);

    }

    @Override
    public int getItemCount() {
        //添加了一个Foot—View，所以 +1；
        return strings.size() + 1;
    }

    class ItemClickViewHolder extends ViewHolder{

        private ImageView iv;
        private TextView tv;

        ItemClickViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_FOOT)
                return;

            iv = (ImageView) itemView.findViewById(R.id.iv_item_head_foot);
            tv = (TextView) itemView.findViewById(R.id.tv_item_head_foot);

        }
    }

    /**
     * 根据是否还有更多数据处理FootView显示与否
     * */
    private void setFootView(boolean hasMoreData) {

        if (hasMoreData && footView != null)
            footView.setVisibility(View.VISIBLE);
        else if (footView != null)
            footView.setVisibility(View.GONE);
    }
}
