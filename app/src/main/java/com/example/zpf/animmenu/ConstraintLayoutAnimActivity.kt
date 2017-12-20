package com.example.zpf.animmenu

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.transition.TransitionManager
import android.view.View
import kotlinx.android.synthetic.main.activity_constraint_layout_anim.*

class ConstraintLayoutAnimActivity : AppCompatActivity() {

    private val csApply: ConstraintSet = ConstraintSet()
    private val csReset: ConstraintSet = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout_anim)

        initView()
    }

    fun initView() {

        csApply.clone(cs_main)
        csReset.clone(cs_main)
    }

    fun onApplyClick(v: View) {

        val sNum = et_num.text.toString()
        if (TextUtils.isEmpty(sNum)) return
        val iNum = Integer.parseInt(sNum)
        when (iNum) {

            0 -> animFirstBtnToLeft()
            1 -> animAllBtnCenterHorizontal()
            2 -> animThreeBtnCenter()
            3 -> animTwoBtnWidthHeight()
            4 -> animTwoThreeGoneOneCrop()
            5 -> animChainStyleButtons()

        }

    }

    /**
     * 重置布局按钮点击事件
     */
    fun onResetClick(v: View) {

        TransitionManager.beginDelayedTransition(cs_main)
        csReset.applyTo(cs_main)
    }

    /**
     * 第一个按钮移动到左边
     */
    private fun animFirstBtnToLeft() {

        TransitionManager.beginDelayedTransition(cs_main)
        csApply.setMargin(R.id.btn_one, ConstraintSet.START, 0)
        csApply.applyTo(cs_main)
    }

    /**
     * 所有按钮移动到水平居中位置
     */
    private fun animAllBtnCenterHorizontal() {

        TransitionManager.beginDelayedTransition(cs_main)

        csApply.setMargin(R.id.btn_one, ConstraintSet.START, 0)
        csApply.setMargin(R.id.btn_one, ConstraintSet.END, 0)

        csApply.setMargin(R.id.btn_two, ConstraintSet.START, 0)
        csApply.setMargin(R.id.btn_two, ConstraintSet.END, 0)

        csApply.setMargin(R.id.btn_three, ConstraintSet.START, 0)
        csApply.setMargin(R.id.btn_three, ConstraintSet.END, 0)

        csApply.centerHorizontally(R.id.btn_one, R.id.cs_main)
        csApply.centerHorizontally(R.id.btn_two, R.id.cs_main)
        csApply.centerHorizontally(R.id.btn_three, R.id.cs_main)

        csApply.applyTo(cs_main)
    }

    /**
     * 第三个按钮移动到中心位置
     */
    private fun animThreeBtnCenter() {

        TransitionManager.beginDelayedTransition(cs_main)

        csApply.setMargin(R.id.btn_three, ConstraintSet.START, 0)
        csApply.setMargin(R.id.btn_three, ConstraintSet.TOP, 0)
        csApply.setMargin(R.id.btn_three, ConstraintSet.END, 0)
        csApply.setMargin(R.id.btn_three, ConstraintSet.BOTTOM, 0)
        csApply.centerHorizontally(R.id.btn_three, R.id.cs_main)
        csApply.centerVertically(R.id.btn_three, R.id.cs_main)

        csApply.applyTo(cs_main)
    }

    /**
     * 更改第二个按钮的宽度、高度
     */
    private fun animTwoBtnWidthHeight() {

        TransitionManager.beginDelayedTransition(cs_main)

        csApply.constrainWidth(R.id.btn_two, 9600)
        csApply.constrainHeight(R.id.btn_two, 200)

        csApply.applyTo(cs_main)
    }

    /**
     * 隐藏第二、第三按钮，第一个按钮扩展整个屏幕
     */
    private fun animTwoThreeGoneOneCrop() {

        TransitionManager.beginDelayedTransition(cs_main)

        csApply.setVisibility(R.id.btn_two, ConstraintSet.GONE)
        csApply.setVisibility(R.id.btn_three, ConstraintSet.GONE)

        csApply.clear(R.id.btn_one)
        csApply.connect(R.id.btn_one, ConstraintSet.LEFT, R.id.cs_main, ConstraintSet.LEFT, 0)
        csApply.connect(R.id.btn_one, ConstraintSet.TOP, R.id.cs_main, ConstraintSet.TOP, 0)
        csApply.connect(R.id.btn_one, ConstraintSet.RIGHT, R.id.cs_main, ConstraintSet.RIGHT, 0)
        csApply.connect(R.id.btn_one, ConstraintSet.BOTTOM, R.id.cs_main, ConstraintSet.BOTTOM, 0)

        csApply.applyTo(cs_main)
    }

    /**
     * 三个按钮链式布局
     */
    private fun animChainStyleButtons() {

        TransitionManager.beginDelayedTransition(cs_main)

        csApply.clear(R.id.btn_one)
        csApply.clear(R.id.btn_two)
        csApply.clear(R.id.btn_three)

        csApply.connect(R.id.btn_one, ConstraintSet.LEFT, R.id.cs_main, ConstraintSet.LEFT, 0)
        csApply.connect(R.id.btn_one, ConstraintSet.RIGHT, R.id.btn_two, ConstraintSet.LEFT, 0)

        csApply.connect(R.id.btn_two, ConstraintSet.LEFT, R.id.btn_one, ConstraintSet.RIGHT, 0)
        csApply.connect(R.id.btn_two, ConstraintSet.RIGHT, R.id.btn_three, ConstraintSet.LEFT, 0)

        csApply.connect(R.id.btn_three, ConstraintSet.LEFT, R.id.btn_two, ConstraintSet.RIGHT, 0)
        csApply.connect(R.id.btn_three, ConstraintSet.RIGHT, R.id.cs_main, ConstraintSet.RIGHT, 0)

        csApply.createHorizontalChain(R.id.cs_main, ConstraintSet.LEFT, R.id.cs_main, ConstraintSet.RIGHT,
                intArrayOf(R.id.btn_one, R.id.btn_two, R.id.btn_three), null, ConstraintSet.MATCH_CONSTRAINT)

        csApply.constrainWidth(R.id.btn_one, ConstraintSet.WRAP_CONTENT)
        csApply.constrainHeight(R.id.btn_one, ConstraintSet.WRAP_CONTENT)
        csApply.constrainWidth(R.id.btn_two, ConstraintSet.WRAP_CONTENT)
        csApply.constrainHeight(R.id.btn_two, ConstraintSet.WRAP_CONTENT)
        csApply.constrainWidth(R.id.btn_three, ConstraintSet.WRAP_CONTENT)
        csApply.constrainHeight(R.id.btn_three, ConstraintSet.WRAP_CONTENT)

        csApply.applyTo(cs_main)
    }
}
