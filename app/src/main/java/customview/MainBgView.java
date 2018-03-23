package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.zpf.animmenu.R;

/**
 * Created by Administrator on 2018/3/23.
 * 主页背景View
 */

public class MainBgView extends View {

    //默认背景
    private final int COLOR_BG = Color.RED;
    //默认左上角圆形颜色
    private final int COLOR_CIRCLE_LT = Color.BLACK;
    //默认右下角圆形颜色
    private final int COLOR_CIRCLE_BR = Color.BLACK;
    //默认坐上角圆形半径
    private final float DEFAULT_RADIUS_CIRCLE_LT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 99, getResources().getDisplayMetrics());
    //默认右下角圆形半径
    private final float DEFAULT_RADIUS_CIRCLE_BR = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
    //背景颜色
    private int mColorBg = COLOR_BG;
    //左上角圆形颜色、半径
    private int mColorCircleLT = COLOR_CIRCLE_LT;
    private float mRadiusCircleLT = DEFAULT_RADIUS_CIRCLE_LT;
    //右下角圆形颜色、半径
    private int mColorCircleBR = COLOR_CIRCLE_BR;
    private float mRadiusCircleBR = DEFAULT_RADIUS_CIRCLE_BR;
    //绘制Circle的画笔
    private Paint mPaintCircle;
    //控件的实际的宽、高
    private int mWidth, mHeight;

    public MainBgView(Context context) {
        super(context);
    }

    public MainBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initPaint();
    }

    public MainBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initPaint();
    }

    public MainBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        initPaint();
    }

    //初始化Paint
    private void initPaint() {

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.FILL);
    }

    //读取自定义的属性值
    private void initAttr(Context context, AttributeSet attrs) {

//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MainBgView);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {

            int attr = array.getIndex(i);
            switch (attr) {

                case R.styleable.MainBgView_mainBgColor:
                    mColorBg = array.getColor(attr, COLOR_BG);
                    break;
                case R.styleable.MainBgView_mainCircleLTColor:
                    mColorCircleLT = array.getColor(attr, COLOR_CIRCLE_LT);
                    break;
                case R.styleable.MainBgView_mainCircleLTRadius:
                    mRadiusCircleLT = array.getDimension(attr, DEFAULT_RADIUS_CIRCLE_LT);
                    break;
                case R.styleable.MainBgView_mainCircleBRColor:
                    mColorCircleBR = array.getColor(attr, COLOR_CIRCLE_BR);
                    break;
                case R.styleable.MainBgView_mainCircleBRRadius:
                    mRadiusCircleBR = array.getDimension(attr, DEFAULT_RADIUS_CIRCLE_BR);
                    break;

            }

        }

        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawContent(canvas);
    }

    //绘制图形
    private void drawContent(Canvas canvas) {

        canvas.drawColor(mColorBg);

        mPaintCircle.setColor(mColorCircleLT);
        canvas.drawCircle(0, 0, mRadiusCircleLT, mPaintCircle);

        mPaintCircle.setColor(mColorCircleBR);
        canvas.drawCircle(mWidth, mHeight, mRadiusCircleBR, mPaintCircle);
    }
}
