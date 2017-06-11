package holder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.zpf.animmenu.R;

import customview.CircleImageView;
import listener.listener.ItemTouchViewHolder;

/**
 * Created by zpf on 2017/2/22.
 */

public class RecyclerRemoveItemHolder extends RecyclerView.ViewHolder implements ItemTouchViewHolder{

    private boolean isChecked = false;
    public RadioButton radioButton;
    public CircleImageView civHeader;
    public TextView tvTitle;
    public TextView tvNum;
    public ImageView ivDelete;
    public LinearLayout layoutInner;

    public RecyclerRemoveItemHolder(View itemView) {
        super(itemView);

        radioButton = (RadioButton) itemView.findViewById(R.id.rb_rdi);
        civHeader = (CircleImageView) itemView.findViewById(R.id.civ_rdi);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title_rdi);
        tvNum = (TextView) itemView.findViewById(R.id.tv_num_rdi);
        ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete_rdi);
        layoutInner = (LinearLayout) itemView.findViewById(R.id.layout_rdi);
    }

    @Override
    public void onItemSelected() {

        layoutInner.setBackgroundColor(Color.LTGRAY);
        isChecked = true;
        radioButton.setChecked(true);
        radioButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClear() {

        if(isChecked) {

            layoutInner.setBackground(null);
            isChecked = false;
            radioButton.setChecked(false);
            radioButton.setVisibility(View.GONE);
        }
    }
}
