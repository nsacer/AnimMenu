package com.example.zpf.animmenu

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_banner_zoom_center.*

class BannerZoomCenterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_zoom_center)
        initView()
    }

    private fun initView() {

        val list = ArrayList<String>()
        list.addAll(resources.getStringArray(R.array.picUrlsShort))

        vpZoomBanner.setViewPagerOffscreenPageLimit(list.size)
        vpZoomBanner.setImagesUrl(list)
    }
}
