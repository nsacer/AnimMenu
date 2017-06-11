package adapter;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zpf.animmenu.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import listener.listener.ItemTouchViewHolder;
import listener.listener.OnItemTouchListener;
import listener.listener.StartDragListener;

/**
 * Created by zpf on 2016/12/1.
 */

public class RvCircleRefreshAdapter extends RecyclerView.Adapter<RvCircleRefreshAdapter.CrItemViewHolder>
        implements View.OnClickListener, OnItemTouchListener {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> strings = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private StartDragListener dragListener;

    public RvCircleRefreshAdapter(Context context, List<String> strings, StartDragListener listener) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);

        if(strings != null && !strings.isEmpty())
            this.strings.addAll(strings);

        if(listener != null)
            this.dragListener = listener;


    }

    public void setsTitles(List<String> sTitles) {

        if(strings != null && !strings.isEmpty()) {

            strings.clear();
            strings.addAll(sTitles);
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(RvCircleRefreshAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Collections.swap(strings, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwipeDismiss(int position) {

        strings.remove(position);
        notifyItemRemoved(position);

    }

    public interface OnItemClickListener {

        void OnItemClick(View view, String string);
    }

    @Override
    public CrItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_circle_refresh_rv, parent, false);

        view.setOnClickListener(this);

        return new CrItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CrItemViewHolder holder, int position) {

        String title = strings.get(position);
        holder.itemView.setTag(title);

        holder.tvTitle.setText(title);
        holder.ivDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                    dragListener.onStartDrag(holder);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {

        return strings.size();
    }

    @Override
    public void onClick(View v) {

        if (onItemClickListener != null)
            onItemClickListener.OnItemClick(v, (String) v.getTag());
    }

    class CrItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchViewHolder{

        private TextView tvTitle;
        private ImageView ivDrag;

        private CrItemViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_circle_refresh);
            ivDrag = (ImageView) itemView.findViewById(R.id.iv_drag);
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }
    }

}
