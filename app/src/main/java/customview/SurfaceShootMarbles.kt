package customview

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import utils.ScreenUtils

/**
 * Created by SurfaceView on 2018/1/23.
 * 弹射弹珠View
 */

class SurfaceShootMarbles : SurfaceView, SurfaceHolder.Callback, Runnable {

    private val tagLog = context.packageName

    private var mSurfaceHolder: SurfaceHolder? = null
    private var mCanvas: Canvas? = null
    //子线程标志位，用来判断是否进行绘制
    private var mIsDraw: Boolean = false
    //屏幕宽度、高度
    private var hScreen = 0
    private var wScreen = 0
    //移动物体宽度、高度
    private val wTank = 150f
    private val hTank = 30f
    //全局paint
    private var mPaint = Paint()
    //前一次、当前手指所在的位置
    private var preX = 0F
    private var curX = 0F
    //记录上一次tank停留的位置x
    private var preTankX = 0f

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

        mIsDraw = true
        preTankX = (wScreen - wTank) / 2
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        hScreen = height
        wScreen = width
        run()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

        mIsDraw = false
    }

    //初始化设置View
    private fun initView() {

        hScreen = ScreenUtils.getScreenHeightWithoutVirtualBar(context as Activity) -
                ScreenUtils.getVirtualBarHeight(context as Activity)
        wScreen = ScreenUtils.getScreenWidth(context as Activity)

        mSurfaceHolder = holder
        mSurfaceHolder!!.addCallback(this)
        isFocusable = true
        //保持屏幕常量，使用PowerManager,WakeLock手动处理需要的话
        keepScreenOn = true
        isFocusableInTouchMode = true

        //初始化全局Paint
        mPaint.isAntiAlias = true
        mPaint.color = Color.DKGRAY
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 2F
    }

    override fun run() {

        if (mIsDraw) drawCanvas()
    }

    //真正的绘图逻辑
    private fun drawCanvas() {

        mCanvas = mSurfaceHolder!!.lockCanvas()
        if (mCanvas != null) {

            try {

                mCanvas!!.drawColor(Color.WHITE)
                drawTankCenter(mCanvas)
            } catch (e: Exception) {
                Log.e(tagLog, e.message)

            } finally {
                //提交绘制内容展示
                mSurfaceHolder!!.unlockCanvasAndPost(mCanvas)
            }
        }
    }

    //绘制tank初始位置
    private fun drawTankCenter(canvas: Canvas?) {

        val leftDef: Float = getTankStartX()
        preTankX = leftDef
        val topDef = hScreen - hTank
        val rightDef = (leftDef + wTank)
        val btmDef = hScreen.toFloat()
        canvas?.drawRect(leftDef, topDef, rightDef, btmDef, mPaint)
        canvas?.drawRect(leftDef + 60, topDef - 30, leftDef + 90, topDef, mPaint)
    }

    //获取Tank移动位置x，区间一定在[0, wScreen - wTank]
    private fun getTankStartX(): Float {

        val realX = preTankX + (curX - preX) * 1.6
        return when {
            realX >= wScreen - wTank -> wScreen - wTank
            realX <= 0 -> 0f
            else -> realX.toFloat()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                curX = event.x
                preX = curX
                true
            }
            MotionEvent.ACTION_MOVE -> {
                preX = curX
                curX = event.x
                run()
                true
            }
            MotionEvent.ACTION_UP -> {
                preX = curX
                true
            }

            else -> super.onTouchEvent(event)
        }
    }
}
