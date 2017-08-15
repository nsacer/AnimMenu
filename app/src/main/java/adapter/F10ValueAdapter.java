package adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zpf.animmenu.R;

import java.util.ArrayList;

/**
 * Created by zpf on 2017/8/15.
 */

public class F10ValueAdapter extends RecyclerView.Adapter<F10ValueAdapter.F10ValueViewHolder> {

    private Context mContext;

    public F10ValueAdapter(Context context) {

        this.mContext = context;
    }

    @Override
    public F10ValueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_f10, parent, false);
        return new F10ValueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(F10ValueViewHolder holder, int position) {

        ArrayList<String> models = new ArrayList<>();
        for (int i = 0; i < 17; i++) {

            models.add("内部数据" + i);
        }

        holder.rv.setLayoutManager(new LinearLayoutManager(mContext));
        F10HeaderAdapter adapter = new F10HeaderAdapter(mContext, models);
        holder.rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class F10ValueViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rv;

        F10ValueViewHolder(View itemView) {
            super(itemView);

            rv = (RecyclerView) itemView.findViewById(R.id.rv);
        }
    }
}
