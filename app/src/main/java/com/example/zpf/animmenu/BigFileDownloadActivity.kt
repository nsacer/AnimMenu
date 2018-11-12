package com.example.zpf.animmenu

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_big_file_download.*

/**
 * 大文件下载页面
 * */
class BigFileDownloadActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_file_download)

        downLoadFile()
    }

    private fun downLoadFile() {

        btnDownloadBigFile.setOnClickListener {

        }
    }
}
