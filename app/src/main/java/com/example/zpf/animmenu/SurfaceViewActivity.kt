package com.example.zpf.animmenu

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_surface_view.*

class SurfaceViewActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btnSurfaceStart -> ssm.run()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surface_view)

        btnSurfaceStart.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()

        initView()
    }

    private fun initView() {

    }
}
