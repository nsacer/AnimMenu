package adapter;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zpf.animmenu.R;

import java.util.ArrayList;

/**
 * Created by tantan卡片adapter on 2018/3/1.
 */

public class TanTanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private RequestManager manager;

    public TanTanAdapter(RequestManager manager, ArrayList<String> picUrls) {
        super(R.layout.item_tan_tan_card, picUrls);
        this.manager = manager;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageView iv = helper.getView(R.id.ivCoverTanItem);
        manager.load(item).thumbnail(0.2f).into(iv);

        helper.setText(R.id.tvContentTanItem, item);

    }
}
