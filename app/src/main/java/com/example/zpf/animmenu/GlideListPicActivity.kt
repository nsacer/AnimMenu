package com.example.zpf.animmenu

import adapter.GlideListPicAdapter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_glide_list_pic.*
import utils.DisplayUtil

class GlideListPicActivity : BaseActivity() {

    private lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_list_pic)

        initView()
    }

    override fun onResume() {
        super.onResume()
        requestManager.resumeRequests()
    }

    override fun onDestroy() {
        super.onDestroy()
        requestManager.pauseRequests()
    }

    private fun initView() {

        requestManager = Glide.with(this)
        val urls = arrayListOf<String>()
        urls.addAll(resources.getStringArray(R.array.picUrls))
        val dpHeight = DisplayUtil.dip2px(this, 120f)
        val adapter = GlideListPicAdapter(requestManager, urls, dpHeight)

        rvGlideListPic.layoutManager = LinearLayoutManager(this)
        rvGlideListPic.setHasFixedSize(true)
        rvGlideListPic.adapter = adapter
    }

}
