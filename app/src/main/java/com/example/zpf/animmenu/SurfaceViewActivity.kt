package com.example.zpf.animmenu

import android.os.Bundle
import android.support.constraint.ConstraintSet
import kotlinx.android.synthetic.main.activity_surface_view.*

class SurfaceViewActivity : BaseActivity() {

    private val csMain = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surface_view)

        initView()
    }

    override fun onResume() {
        super.onResume()

        setSurfaceView()
    }

    private fun initView() {

        csMain.clone(clSurfaceRoot)

    }

    private fun setSurfaceView() {

        surfaceShootMarbles.run()
    }

}
