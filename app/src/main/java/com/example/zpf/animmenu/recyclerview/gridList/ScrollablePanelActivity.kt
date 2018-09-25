package com.example.zpf.animmenu.recyclerview.gridList

import android.os.Bundle
import com.example.zpf.animmenu.BaseActivity
import com.example.zpf.animmenu.R
import kotlinx.android.synthetic.main.scrollable_panel_activity.*

class ScrollablePanelActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scrollable_panel_activity)

        initScrollPanel()
    }

    private fun initScrollPanel() {

    }
}
