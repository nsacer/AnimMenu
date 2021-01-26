package com.example.zpf.animmenu.recyclerview.model

import android.os.Parcel
import android.os.Parcelable

/**
 * 自选股列表model
 * @param stockCode 股票代码
 * @param stockName 股票名称
 * @param priceNew 最新价
 * @param quoteChange 涨跌幅
 * @param buySellPoint 买卖点
 * @param holdLine 持仓线
 * @param valueAnalysis 价值分析
 * @param stockAnalysis 股性分析
 * @param riseAndFall 涨跌额
 * @param handTurnoverRate 换手率
 * @param totalMarketCapitalization 总市值
 * */
class SelectedStockModel constructor(var stockCode: String?,
                                     var stockName: String?,
                                     var priceNew: String?,
                                     var quoteChange: String?,
                                     var buySellPoint: String?,
                                     var holdLine: String?,
                                     var valueAnalysis: String?,
                                     var stockAnalysis: String?,
                                     var riseAndFall: String?,
                                     var handTurnoverRate: String?,
                                     var totalMarketCapitalization: String?) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(stockCode)
        writeString(stockName)
        writeString(priceNew)
        writeString(quoteChange)
        writeString(buySellPoint)
        writeString(holdLine)
        writeString(valueAnalysis)
        writeString(stockAnalysis)
        writeString(riseAndFall)
        writeString(handTurnoverRate)
        writeString(totalMarketCapitalization)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SelectedStockModel> = object : Parcelable.Creator<SelectedStockModel> {
            override fun createFromParcel(source: Parcel): SelectedStockModel = SelectedStockModel(source)
            override fun newArray(size: Int): Array<SelectedStockModel?> = arrayOfNulls(size)
        }
    }
}