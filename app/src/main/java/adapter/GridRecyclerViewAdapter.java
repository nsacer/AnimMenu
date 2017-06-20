package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zpf.animmenu.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import listener.listener.ItemTouchViewHolder;
import listener.listener.OnItemTouchListener;


/**
 * Created by zpf on 2016/9/8.
 */
public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.GridViewHolder>
implements OnItemTouchListener {

    private Context context;
    private ArrayList<String> urls = new ArrayList<>();
    private LayoutInflater inflater;
    private Picasso picasso;

    public GridRecyclerViewAdapter(Context contexts, List<String> strings) {

        this.context = contexts;
        picasso = Picasso.with(contexts);
        inflater = LayoutInflater.from(contexts);
        this.urls.addAll(strings);
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_grid_recycler, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GridViewHolder holder, final int position) {

        picasso.load(urls.get(position))
                .placeholder(R.mipmap.wall)
                .error(R.mipmap.wall)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {

                        Palette.from(source).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {

                                Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                                if (swatch != null) {
                                    holder.tv.setBackgroundColor(swatch.getRgb());
                                }
                            }
                        });
                        return source;
                    }

                    @Override
                    public String key() {
                        return urls.get(position);
                    }
                }).into(holder.iv);

        holder.tv.setText(urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Collections.swap(urls, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwipeDismiss(int position) {

        urls.remove(position);
        notifyItemRemoved(position);
    }

    class GridViewHolder extends RecyclerView.ViewHolder implements ItemTouchViewHolder{

        private ImageView iv;
        private TextView tv;

        private GridViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }

        @Override
        public void onItemSelected() {

            ((CardView) itemView).setCardBackgroundColor(Color.LTGRAY);
            ((CardView) itemView).setCardElevation(R.dimen.card_item_selected_elevation);
        }

        @Override
        public void onItemClear() {

            ((CardView) itemView).setCardBackgroundColor(0);
            ((CardView) itemView).setCardElevation(R.dimen.card_item_default_elevation);
        }
    }
}
