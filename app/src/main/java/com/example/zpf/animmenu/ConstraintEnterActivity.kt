package com.example.zpf.animmenu

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_constraint_enter.*

class ConstraintEnterActivity : BaseActivity(), View.OnClickListener {

    private var btmSheetDialog: BottomSheetDialog? = null

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btnOne -> openActivity(ConstraintLayoutActivity::class.java, v)
            R.id.btnTwo -> openActivity(ConstraintLayoutAnimActivity::class.java, v)
            R.id.btnBanner -> openActivity(BannerZoomCenterActivity::class.java, v)
            R.id.btnSheet -> btmSheetDialog!!.show()
            R.id.btnSurfaceView -> openActivity(SurfaceViewActivity::class.java, v)
            R.id.btnMainBgView -> openActivity(MainBgActivity::class.java, v)
            R.id.btnSurfaceRain -> openActivity(SurfaceRainActivity::class.java, v)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_enter)

        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
        btnBanner.setOnClickListener(this)

        initBottomSheetDialog()
        btnSheet.setOnClickListener(this)

        btnSurfaceView.setOnClickListener(this)

        btnMainBgView.setOnClickListener(this)

        btnSurfaceRain.setOnClickListener(this)
    }

    //创建bottomSheetDialog
    private fun initBottomSheetDialog() {

        btmSheetDialog = BottomSheetDialog(this)
        btmSheetDialog!!.setContentView(R.layout.layout_bottom_sheet_dialog)
        val frameLayout = btmSheetDialog!!.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        val mBehavior = BottomSheetBehavior.from(frameLayout)
        mBehavior.peekHeight = getDp(240)
        mBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

}
