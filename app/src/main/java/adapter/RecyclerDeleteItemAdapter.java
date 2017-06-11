package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zpf.animmenu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import holder.RecyclerRemoveItemHolder;
import listener.listener.OnItemTouchListener;

/**
 * Created by zpf on 2017/2/23.
 */

public class RecyclerDeleteItemAdapter extends RecyclerView.Adapter<RecyclerRemoveItemHolder>
        implements OnItemTouchListener, View.OnClickListener {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> urlList = new ArrayList<>();
    private AlertDialog alertDialog;
    private int positionDelete;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    @Override
    public void onClick(View view) {

        if (onItemClickListener != null)
            onItemClickListener.OnItemClick((String) view.getTag(R.id.civ_rdi),
                    (Integer) view.getTag(R.id.tv_num_rdi));
    }

    public interface OnItemClickListener {

        void OnItemClick(String url, int position);
    }

    public RecyclerDeleteItemAdapter(Context context, ArrayList<String> urls) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.urlList = urls;

    }

    @Override
    public RecyclerRemoveItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_remove_item_layout, parent, false);
        view.setOnClickListener(this);
        return new RecyclerRemoveItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerRemoveItemHolder holder, int position) {

        holder.itemView.setTag(R.id.civ_rdi, urlList.get(position));
        holder.itemView.setTag(R.id.tv_num_rdi, position);

        String url = urlList.get(position);

        if (!url.equals(holder.civHeader.getTag())) {

            Picasso.with(context).load(url)
                    .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_header))
                    .config(Bitmap.Config.ARGB_4444)
                    .into(holder.civHeader);
            holder.civHeader.setTag(url);
        }

        holder.tvTitle.setText(String.valueOf(position));

        holder.tvNum.setText(url);
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    /**
     * 删除单个item
     */
    public void removeItem(int position) {

        urlList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 更新内容
     * 删除第一条数据
     * 在末尾添加一条数据
     */
    public void updateContent(String url) {

//        if (url != null && !TextUtils.isEmpty(url)) {
//
//            urlList.remove(0);
//            notifyItemRangeRemoved(0, 1);
//            urlList.add(url);
//            notifyItemRangeInserted(urlList.size() - 1, 1);
//        }

        if (urlList != null && !urlList.isEmpty()) {

            urlList.add(urlList.get(0));
            urlList.remove(0);
            notifyItemRangeRemoved(0, 1);
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Collections.swap(urlList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwipeDismiss(int position) {

        positionDelete = position;
        popAlertDialog();
    }

    /**
     * 弹出AlertDialog
     */
    private void popAlertDialog() {

        if (alertDialog == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            alertDialog = builder
                    .setMessage("确定删除？")
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                            urlList.remove(positionDelete);
                        }
                    })
                    .create();

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                    notifyDataSetChanged();
                }
            });

        }

        alertDialog.show();

        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(context, R.color.colorGrayDarkDark));
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

    }
}
