package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import listener.listener.ItemTouchViewHolder;
import listener.listener.OnItemTouchListener;
import listener.listener.StartDragListener;

/**
 * 实现RecyclerView的横滑删除、长按item/拖动图标 排序Adapter
 */

public class RvAdapterSwipePress extends RecyclerView.Adapter<RvAdapterSwipePress.SwipePressViewHolder>
        implements OnItemTouchListener {

    private Context mContext;
    private ArrayList<String> strings = new ArrayList<>();
    private StartDragListener dragListener;
    private AlertDialog alertDialog;
    private int positionDelete;

    public RvAdapterSwipePress(Context context) {

        this.mContext = context;
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public void setStrings(ArrayList<String> list) {

        if (list != null) {

            this.strings = list;
            notifyDataSetChanged();
        }
    }

    public void setDragListener(StartDragListener dragListener) {

        if (dragListener != null)
            this.dragListener = dragListener;
    }

    @Override
    public SwipePressViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_swipe_press_rv, viewGroup, false);
        return new SwipePressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SwipePressViewHolder holder, int position) {

        String url = strings.get(position);
        holder.tvContent.setText(url);

        //拖动图标设置OnTouch接口使其具有和长按item拖动一样的效果
        holder.ivDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (dragListener != null) dragListener.onStartDrag(holder);
                        break;
                    case MotionEvent.ACTION_UP:
                        holder.ivDrag.performClick();
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Collections.swap(strings, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwipeDismiss(int position) {

        positionDelete = position;
        //这里进行弹窗询问操作，也可自定义其他的操作方式
        popAlertDialog();
    }

    //这里ViewHolder实现ItemTouchViewHolder接口可以自定义设置item长按状态（onItemSelected（））的显示效果
    //以及item长按释放之后（onItemClear()）的效果重置
    class SwipePressViewHolder extends RecyclerView.ViewHolder implements ItemTouchViewHolder {

        private TextView tvContent;
        private ImageView ivDrag;

        SwipePressViewHolder(View itemView) {
            super(itemView);

            tvContent = (TextView) itemView.findViewById(R.id.tv_swipe_press);
            ivDrag = (ImageView) itemView.findViewById(R.id.iv_drag_swipe_press);
        }

        @Override
        public void onItemSelected() {

            if (tvContent != null)
                tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        }

        @Override
        public void onItemClear() {

            if (tvContent != null)
                tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlack));
        }
    }

    /**
     * 弹出AlertDialog
     */
    private void popAlertDialog() {

        if (alertDialog == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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

                            strings.remove(positionDelete);
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

    }
}
