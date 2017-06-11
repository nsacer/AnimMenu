package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zpf.animmenu.R;

import java.util.ArrayList;

/**
 * 中奖信息轮播切换的Adapter
 */

public class RvAdapterPrizeInfo extends RecyclerView.Adapter<RvAdapterPrizeInfo.PrizeInfoViewHolder> {

    private Context mContext;
    private ArrayList<String> prizeList = new ArrayList<>();

    public RvAdapterPrizeInfo(Context context) {

        this.mContext = context;
    }

    public void setList(ArrayList<String> list) {

        if (list != null && !list.isEmpty()) {

            this.prizeList = list;
            notifyDataSetChanged();
        }
    }

    public void updateList() {

        if (!prizeList.isEmpty()) {

            prizeList.add(prizeList.get(0));
            prizeList.remove(0);
            notifyItemRangeRemoved(0, 1);
        }
    }

    @Override
    public PrizeInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_prize_info, viewGroup, false);
        return new PrizeInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PrizeInfoViewHolder holder, int i) {

        String info = prizeList.get(i);
        holder.tv.setText(info);

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class PrizeInfoViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        PrizeInfoViewHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv_prize_info);
        }
    }
}
