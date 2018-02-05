package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.zpf.animmenu.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 单双列切换的adapter
 */
public class RecyclerViewSwitchAdapter extends RecyclerView.Adapter<RecyclerViewSwitchAdapter.GridViewHolder> {

    private Context mContext;
    private ArrayList<String> urls = new ArrayList<>();
    private LayoutInflater inflater;
    private RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.bg_loading)
            .error(R.mipmap.bg_loading);

    public RecyclerViewSwitchAdapter(Context contexts, List<String> strings) {

        this.mContext = contexts;
        inflater = LayoutInflater.from(contexts);
        this.urls.addAll(strings);
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_count_switch, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {

        Glide.with(mContext).load(urls.get(position))
                .apply(options)
                .into(holder.iv);

        holder.tv.setText(urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv;

        private GridViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }

    }

}
