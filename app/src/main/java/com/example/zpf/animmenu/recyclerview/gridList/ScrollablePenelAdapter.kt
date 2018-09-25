package com.example.zpf.animmenu.recyclerview.gridList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.zpf.animmenu.R
import com.kelin.scrollablepanel.library.PanelAdapter


/**
 * created by zpf on 2018/8/7
 * 自选股表格布局
 */
class ScrollablePenelAdapter : PanelAdapter() {

    //第一行第一列标题
    private val TYPE_TITLE = 0
    //第一列股票
    private val TYPE_STOCK = 1
    //第一行指标
    private val TYPE_INDEX = 2
    //数据行列
    private val TYPE_DATA = 3

    override fun getItemViewType(row: Int, column: Int): Int {
        return if (row == 0 && column == 0) TYPE_TITLE
        else if (column == 0) TYPE_STOCK
        else if (row == 0) TYPE_INDEX
        else TYPE_DATA
    }

    override fun getRowCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            TYPE_TITLE -> TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.select_stock_item_title, parent, false))
            else -> TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.select_stock_item_title, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, row: Int, column: Int) {

        val typeView = getItemViewType(row, column)
        when(typeView) {

        }
    }

    override fun getColumnCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvTitle: TextView = view.findViewById<View>(R.id.tvTitleStockItem) as TextView

    }

    private fun setTitleView(pos: Int, holder: TitleViewHolder) {


    }


}