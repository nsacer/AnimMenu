package adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zpf.animmenu.R;

import java.util.List;


/**
 * Created by zpf on 2018/3/7.
 * 股票详情页-日k-资金活跃度、资金净流入、短线电波...等List的adapter
 */

public class StockAnalysisIndexAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public StockAnalysisIndexAdapter(@Nullable List<String> data) {
        super(R.layout.item_stock_analysis_index, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        TextView tv = helper.getView(R.id.tvStockIndexItemSingleDetail);
        tv.setText(item);
        tv.setTextColor(item.equals("资金活跃度") ? Color.parseColor("#c11211") :
                Color.parseColor("#7c7c7c"));
    }
}
