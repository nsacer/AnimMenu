package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import listener.listener.ItemTouchViewHolder;
import listener.listener.OnItemTouchListener;

/**
 * Created by Administrator on 2017/5/16.
 */

public class RecyclerAllAdapter extends RecyclerView.Adapter<RecyclerAllAdapter.AllViewHolder>
        implements OnItemTouchListener, View.OnClickListener{

    private Context mContext;
    private ArrayList<String> strings = new ArrayList<>();
    private OnItemClickListener listener;

    public RecyclerAllAdapter(Context context) {

        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {

        void OnItemClick(View view);
    }

    public void setStrings(ArrayList<String> strings) {

        if (strings != null && !strings.isEmpty()) {

            this.strings = strings;
        }
    }

    public void appStrings(ArrayList<String> strings) {

        if (strings != null && !strings.isEmpty() && !this.strings.containsAll(strings)) {

            this.strings.addAll(strings);
        }
    }
    
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public AllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(AllViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onSwipeDismiss(int position) {

    }

    @Override
    public void onClick(View v) {

        if (listener != null)
            listener.OnItemClick(v);
    }

    class AllViewHolder extends RecyclerView.ViewHolder implements ItemTouchViewHolder {
        public AllViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }
    }

}
