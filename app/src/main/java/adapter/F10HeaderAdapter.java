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
 * Created by zpf on 2017/8/15.
 */

public class F10HeaderAdapter extends RecyclerView.Adapter<F10HeaderAdapter.F10HeadViewHolder> {

    private Context mContext;
    private ArrayList<String> models = new ArrayList<>();

    public F10HeaderAdapter(Context context, ArrayList<String> models) {

        this.mContext = context;
        if (models != null)
            this.models = models;
    }


    @Override
    public F10HeadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_view_f10_head, parent, false);
        return new F10HeadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(F10HeadViewHolder holder, int position) {


        holder.tv.setText(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class F10HeadViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        F10HeadViewHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
