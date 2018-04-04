package customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * Created by Administrator on 2018/4/4.
 * SurfaceView实现雨滴效果
 */
class SurfaceRainDrop : SurfaceView, SurfaceHolder.Callback, Runnable {

    private lateinit var mSurfaceHolder: SurfaceHolder
    private var mCanDraw: Boolean = false
    private var mThread: Thread? = null
    private var mWidth = 0f
    private var mHeight = 0f
    private val mPaintBg: Paint = Paint()
    private var mLinearGradient: LinearGradient? = null
    private var mHasDrawBg: Boolean = false

    constructor(context: Context?) : super(context) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initPaint()
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

        mCanDraw = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {

        mCanDraw = true
        mThread = Thread(this)
        mThread?.start()
    }


    private fun initPaint() {

        mSurfaceHolder = holder
        mSurfaceHolder.addCallback(this)

        mPaintBg.isAntiAlias = true
        mPaintBg.style = Paint.Style.FILL

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        mLinearGradient = LinearGradient(0f, 0f, mWidth, mHeight, Color.parseColor("#BC3FFF"),
                Color.parseColor("#FF6F9E"), Shader.TileMode.CLAMP)
    }

//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//
//        if (mLinearGradient != null) mPaintBg.shader = mLinearGradient
//        canvas?.drawRect(0f, 0f, mWidth, mHeight, mPaintBg)
//
//    }

    override fun run() {

        while (mCanDraw) {

            drawContent()
        }
    }

    private fun drawContent() {

        val canvas = mSurfaceHolder.lockCanvas()
        try {
            drawCanvas(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mSurfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawCanvas(canvas: Canvas) {

        if (mLinearGradient != null || !mHasDrawBg) {

            mPaintBg.shader = mLinearGradient
            canvas.drawRect(0f, 0f, 0f, measuredHeight.toFloat(), mPaintBg)
            mHasDrawBg = true
        }
    }
}
