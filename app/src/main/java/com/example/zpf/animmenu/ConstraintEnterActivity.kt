package com.example.zpf.animmenu

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.widget.FrameLayout
import com.example.zpf.animmenu.customview.TemperatureActivity
import kotlinx.android.synthetic.main.activity_constraint_enter.*

/**
 * 测试试验田入口页面
 * */
class ConstraintEnterActivity : BaseActivity(), View.OnClickListener {

    private lateinit var btmSheetDialog: BottomSheetDialog

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btnOne -> openActivity(ConstraintLayoutActivity::class.java, v)
            R.id.btnTwo -> openActivity(ConstraintLayoutAnimActivity::class.java, v)
            R.id.btnBanner -> openActivity(BannerZoomCenterActivity::class.java, v)
            R.id.btnSheet -> btmSheetDialog.show()
            R.id.btnSurfaceView -> openActivity(SurfaceViewActivity::class.java, v)
            R.id.btnTabBar -> openActivity(TabBarActivity::class.java, v)
            R.id.btnTemperature -> openActivity(TemperatureActivity::class.java, v)
            R.id.btnDownRetrofit -> showToast("bigFileDownload")
            R.id.btnDataBind -> openActivity(DataBindingActivity::class.java, v)
            R.id.tvDrawableSize -> {

                when {
                    tvDrawableSize.tag == null || !(tvDrawableSize.tag as Boolean) -> {

                        val drawable = getDrawable(R.mipmap.ic_face_chat)
                        drawable!!.setBounds(0, 0, 40, 40)
                        tvDrawableSize.setCompoundDrawables(drawable, null, null, null)
                        tvDrawableSize.tag = true
                    }
                    else -> {

                        tvDrawableSize.tag = false
                        tvDrawableSize.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_face_chat), null, null, null)
                    }
                }
            }
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

        btnTabBar.setOnClickListener(this)

        btnTemperature.setOnClickListener(this)

        btnDownRetrofit.setOnClickListener(this)

        btnDataBind.setOnClickListener(this)

        tvDrawableSize.setOnClickListener(this)

    }

    //创建bottomSheetDialog
    private fun initBottomSheetDialog() {

        btmSheetDialog = BottomSheetDialog(this)
        btmSheetDialog.setContentView(R.layout.layout_bottom_sheet_dialog)
        val frameLayout = btmSheetDialog.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        val mBehavior = BottomSheetBehavior.from(frameLayout)
        mBehavior.peekHeight = getDp(240)
        mBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

}
