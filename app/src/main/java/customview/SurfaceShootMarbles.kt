package customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import model.Marbles

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
    //目标被击中之后的paint
    private var mPaintTarget = Paint()
    //前一次、当前手指所在的位置
    private var preX = 0F
    private var curX = 0F
    //记录上一次tank停留的位置x
    private var preTankX = 0f
    //存储每一个发射出去的弹珠
    private var marbles = mutableListOf<Marbles>()
    //要打击的目标
    private val wTarget = 120f
    private var marbleTarget = Marbles(0f, 0f, 120f, 30f, false)
    //使用线程定时刷新
    private lateinit var threadRefresh: Thread
    //用来创建新tank的计数器
    private var mRunCount = 0
    //记录目标是否被击中（用于在循环里边判定是否绘制或者跳过）
    private var isTargetShooted = false

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
        threadRefresh = Thread(this)
        threadRefresh.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        hScreen = height
        wScreen = width
        preTankX = (wScreen - wTank) / 2
        marbles.add(createMarble())
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

        mIsDraw = false
    }

    //初始化设置View
    private fun initView() {

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

        mPaintTarget.isAntiAlias = true
        mPaintTarget.color = Color.TRANSPARENT
        mPaintTarget.style = Paint.Style.FILL
        mPaintTarget.strokeWidth = 2f
        mPaintTarget.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun run() {

        while (mIsDraw) {

            //发射弹珠速率，越小越快
            if (mRunCount > 8) {
                mRunCount = 0
                marbles.add(createMarble())
            }
            mRunCount++

            val start = System.currentTimeMillis()
            drawCanvas()
            val end = System.currentTimeMillis()
            //线程休眠时间
            val sleepTime = 28
            if (end - start < sleepTime) {

                Thread.sleep(sleepTime - (end - start))
            }

            marbles = marbles.dropLastWhile { (it.btm < 0) || it.isDestroy }.toMutableList()
        }
    }

    //真正的绘图逻辑
    private fun drawCanvas() {

        mCanvas = mSurfaceHolder!!.lockCanvas()
        if (mCanvas != null) {

            try {

                mCanvas!!.drawColor(Color.WHITE)
                drawTankCenter(mCanvas)
                drawMarbleTarget(mCanvas!!)
                drawShootMarbleAndUpdateData(mCanvas!!)
                if (isTargetShooted) {

                    mCanvas!!.drawRect(marbleTarget.left, marbleTarget.top, marbleTarget.right, marbleTarget.btm, mPaintTarget)
                    updateMarbleTarget()
                }
            } catch (e: Exception) {
                Log.e(tagLog, e.message?:"")

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

        //tank移动距离/手指在屏幕上移动的距离
        val realX = preTankX + (curX - preX) * 2.4
        return when {
            realX >= wScreen - wTank -> wScreen - wTank
            realX <= 0 -> 0f
            else -> realX.toFloat()
        }
    }

    //绘制发射出的弹珠并更新下一次显示位置坐标,并判断是否击中目标
    private fun drawShootMarbleAndUpdateData(canvas: Canvas) {

        marbles.forEachIndexed { index, marble ->

            if (marble.right > marbleTarget.left && marble.left < (marbleTarget.right) &&
                    marbleTarget.btm >= marble.top && !isTargetShooted) {

                isTargetShooted = true
                marbles[index].isDestroy = true
            }

            if (marble.btm >= 0) {

                canvas.drawRect(marble.left, marble.top, marble.right, marble.btm, mPaint)
                marbles[index] = updateMarble(marble)
            }
        }
    }

    //绘制要打击的目标
    private fun drawMarbleTarget(canvas: Canvas) {

        canvas.drawRect(marbleTarget.left, marbleTarget.top, marbleTarget.right, marbleTarget.btm, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                performClick()
                curX = event.x
                preX = curX
                true
            }
            MotionEvent.ACTION_MOVE -> {
                preX = curX
                curX = event.x
//                run()
                true
            }
            MotionEvent.ACTION_UP -> {
                preX = curX
                true
            }

            else -> super.onTouchEvent(event)
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    //创建Marbles对象
    private fun createMarble(): Marbles {

        return Marbles(preTankX + 60, hScreen - 60f, preTankX + 90, hScreen - 30f, false)
    }

    //更新发射出去的弹珠的位置坐标
    private fun updateMarble(marble: Marbles): Marbles {

        marble.btm = marble.btm - 30
        marble.top = marble.btm - 30
        return marble
    }

    //生成打击目标的随机坐标
    private fun updateMarbleTarget() {

        marbleTarget.left = Math.floor(Math.random() * (wScreen - wTarget)).toFloat()
        marbleTarget.right = marbleTarget.left + wTarget
        marbleTarget.top = 0f
        marbleTarget.btm = 30f
    }
}
