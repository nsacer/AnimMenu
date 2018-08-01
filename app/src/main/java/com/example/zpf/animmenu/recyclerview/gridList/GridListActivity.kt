package com.example.zpf.animmenu.recyclerview.gridList

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.zpf.animmenu.BaseActivity
import com.example.zpf.animmenu.R
import com.example.zpf.animmenu.recyclerview.model.SelectedStockModel
import kotlinx.android.synthetic.main.activity_grid_list.*

/**
 * 支持横向滑动的网格布局列表
 * */
class GridListActivity : BaseActivity() {

    private lateinit var adapterGridList: GridListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_list)

        initView()
    }

    private fun initView() {

        initRecyclerView()
    }

    private fun initRecyclerView() {

        rvGridList.layoutManager = LinearLayoutManager(this)
        rvGridList.setHasFixedSize(true)

        adapterGridList = GridListAdapter(createTestData())
        rvGridList.adapter = adapterGridList

    }

    //创建虚拟数据
    private fun createTestData(): List<SelectedStockModel> {

        val models = arrayListOf<SelectedStockModel>()
        for (i in 1..15) {

            models.add(SelectedStockModel("00004$i", "浦发银行$i", "9$i",
                    "+9.$i%", "买卖点", "持仓线", "价值分析",
                    "股性分析", "-7$i", "12.$i", "129$i" + "亿"))
        }
        return models
    }
}
