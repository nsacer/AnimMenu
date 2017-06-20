package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zpf.animmenu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zpf on 2017/2/17.
 */

public class RecyclerSnapAdapter extends RecyclerView.Adapter<RecyclerSnapAdapter.SnapViewHolder> {

    private Context context;
    private ArrayList<String> urlList = new ArrayList<>();

    public RecyclerSnapAdapter(Context context, String[] urls) {

        this.context = context;
        this.urlList.addAll(Arrays.asList(urls));
    }


    @Override
    public SnapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_recycler, parent, false);
        return new SnapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SnapViewHolder holder, int position) {

        Picasso.with(context).load(urlList.get(position)).config(Bitmap.Config.ARGB_4444)
                .placeholder(R.mipmap.wall).into(holder.iv);

        holder.tv.setText(urlList.get(position));
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    class SnapViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv;
        private TextView tv;

        SnapViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);

        }
    }
}
