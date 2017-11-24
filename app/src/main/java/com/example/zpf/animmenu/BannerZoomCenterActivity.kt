package com.example.zpf.animmenu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class BannerZoomCenterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_zoom_center)
        initView()
    }

    private fun initView() {

        val list = ArrayList<String>()
        list.addAll(resources.getStringArray(R.array.picUrlsShort))
    }
}
