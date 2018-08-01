package com.example.zpf.animmenu.recyclerview.gridList

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.zpf.animmenu.R
import com.example.zpf.animmenu.recyclerview.model.SelectedStockModel

/**
 * 自选股列表的adapter
 * */
class GridListAdapter constructor(models: List<SelectedStockModel>) :
        BaseQuickAdapter<SelectedStockModel, BaseViewHolder>(R.layout.rv_grid_list_item, models) {


    override fun convert(helper: BaseViewHolder?, item: SelectedStockModel?) {

        if (helper != null && item != null) {

            helper.setText(R.id.tvStockNameGLI, item.stockName)
                    .setText(R.id.tvStockCodeGLI, item.stockCode)
                    .setText(R.id.tvPriceNewGLI, item.priceNew)
                    .setText(R.id.tvQuoteChangeGLI, item.quoteChange)
                    .setText(R.id.tvBuySellPointGLI, item.buySellPoint)
                    .setText(R.id.tvHoldLineGLI, item.holdLine)
                    .setText(R.id.tvValueAnalysisGLI, item.valueAnalysis)
                    .setText(R.id.tvStockAnalysisGLI, item.stockAnalysis)
                    .setText(R.id.tvRiseAndFallGLI, item.riseAndFall)
                    .setText(R.id.tvHandTurnoverRateGLI, item.handTurnoverRate)
                    .setText(R.id.tvTotalMarketCapitalizationGLI, item.totalMarketCapitalization)
        }
    }
}