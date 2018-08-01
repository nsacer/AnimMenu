package customview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.zpf.animmenu.R;

import java.util.ArrayList;

import utils.DisplayUtil;

public class StockMarketTemperature extends View {

    //外层弧度的宽度
    private final float ARC_WIDTH_OUT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
    //刻度数值数组
    private final String[] sNums = new String[]{"0°", "10°", "20°", "30°", "40°", "50°", "60°", "70°", "80°", "90°", "100°"};
    //刻度表数值rgb红绿色值集合
    private ArrayList<Integer[]> rgList = new ArrayList<>();
    //温度计指针bitmap
    private Bitmap mBitmapIndex;
    //长宽中较短的一条边
    private float mMinBorder = 0;
    //仪表盘的底部坐标y
    private float mYChartBtm = 0;
    //控件中间点坐标
    private float mCenterX = 0, mCenterY = 0;
    //表盘左边、右边的文字
    private String mTextChartLeft = "弱势";
    private String mTextChartRight = "强势";
    //绘制图表刻度的paint
    private Paint mPaintChart;
    //刻度表的刻度的长度
    private float mScaleWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
    private float mRadiusChart;
    //刻度数，有多少格刻度
    private float mTikeCount = 50;
    //刻度的Paint
    private Paint mPaintTike;
    //表盘最大刻度数
    private float mMaxValue = 100;
    //动画进行当中的数值
    private float mCurrentValue = 0;
    //实际的数据值
    private float mValue = 0;
    //刻度数值字体大小
    private float mNumTextSize = 24;
    //刻度表外层的数值的Paint
    private Paint mPaintNum;
    //刻度表外层的数值渐变Paint
    private Paint mPaintNumGradient;
    //左右文本paint
    private Paint mPaintText;


    public StockMarketTemperature(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDefineAttrs(context, attrs);
        init();
    }

    public StockMarketTemperature(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefineAttrs(context, attrs);
        init();
    }

    //初始化画笔
    private void init() {

        //指针图片
        mBitmapIndex = BitmapFactory.decodeResource(getResources(), R.mipmap.market_research_temperature_index);

        //初始化生成刻度表对应数值的颜色
        //每次旋转的角度
        float rotateAngle = 180 / (sNums.length - 1);
        //已经绘制过的角度
        float angleDrawed = 0f;
        for (String sNum : sNums) {

            float percentDrawd = angleDrawed == 0 ? 0 : angleDrawed / 180;
            int itemRed = (int) (255 * percentDrawd);
            int itemGreen = 255 - itemRed;
            rgList.add(new Integer[]{itemRed, itemGreen});
            angleDrawed += rotateAngle;
        }

        mPaintChart = new Paint();
        mPaintChart.setAntiAlias(true);
        mPaintChart.setStyle(Paint.Style.FILL);

        mPaintTike = new Paint();
        mPaintTike.setAntiAlias(true);
        mPaintTike.setStyle(Paint.Style.FILL);

        mPaintNum = new TextPaint();
        mPaintNum.setAntiAlias(true);
        mPaintNum.setStyle(Paint.Style.FILL);
        mPaintNum.setColor(Color.GRAY);
        mPaintNum.setTextSize(mNumTextSize);
        mPaintNum.setTextAlign(TextPaint.Align.CENTER);

        mPaintNumGradient = new TextPaint();
        mPaintNumGradient.setAntiAlias(true);
        mPaintNumGradient.setStyle(Paint.Style.FILL);
        mPaintNumGradient.setTextSize(mNumTextSize);
        mPaintNumGradient.setTextAlign(TextPaint.Align.CENTER);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setTextSize(30);
        mPaintText.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        getDrawNeedCoordinate(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawChart(canvas);
    }

    /**
     * 获取计算绘图中需要的坐标
     *
     * @param x 控件宽度
     * @param y 控件高度
     */
    private void getDrawNeedCoordinate(int x, int y) {

        mMinBorder = x < y ? x : y;
        mCenterX = x / 2;
        mCenterY = y / 2;
        mYChartBtm = y * 3 / 4;
        float mDiameterChart = mMinBorder - DisplayUtil.dip2px(getContext(), 6);
        mRadiusChart = mDiameterChart / 2;
    }

    //读取设置的自定义的属性
    private void initDefineAttrs(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StockMarketTemperature);
        int size = array.getIndexCount();
        for (int i = 0; i < size; i++) {

            int attr = array.getIndex(i);
            switch (attr) {

                case R.styleable.StockMarketTemperature_stockMarketTemperatureLeftText:
                    mTextChartLeft = array.getString(attr);
                    break;
                case R.styleable.StockMarketTemperature_stockMarketTemperatureRightText:
                    mTextChartRight = array.getString(attr);
                    break;
            }
        }
        array.recycle();
    }

    //绘制图表
    private void drawChart(Canvas canvas) {

        drawChartBorderScale(canvas);

        drawChartLeftRightText(canvas);

        drawBorderScaleGray(canvas);

        drawScrollIndexCurrent(canvas);
    }

    //绘制图表周围的刻度
    private void drawChartBorderScale(Canvas canvas) {

        //绘制外层弧度
        mPaintChart.setStrokeWidth(ARC_WIDTH_OUT);
        mPaintChart.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(mCenterX - mRadiusChart, mYChartBtm - mRadiusChart, mCenterX + mRadiusChart, mYChartBtm + mRadiusChart);
        mPaintChart.setShader(new SweepGradient(oval.centerX(), oval.centerY(), new int[]{Color.GREEN, Color.RED}, new float[]{0.5f, 1f}));
        canvas.drawArc(oval, -180, 180, false, mPaintChart);

        //绘制刻度
        canvas.save();
        canvas.translate(mCenterX, mYChartBtm);
        canvas.rotate(90);
        //每次旋转的角度
        float rotateAngle = 180 / mTikeCount;
        //已经绘制过的角度
        float angleDrawed = 0f;
        for (int i = 0; i < mTikeCount + 1; i++) {

            //根据已经绘制的角度比例，计算当前绘制的item的颜色值
            float percentDrawd = angleDrawed == 0 ? 0 : (angleDrawed / 180);
            int itemRed = (int) (255 * percentDrawd);
            int itemGreen = 255 - itemRed;
            mPaintTike.setARGB(255, itemRed, itemGreen, 0);
            float endY;
            if (i % 5 == 0) {
                mPaintTike.setStrokeWidth(4);
                endY = mRadiusChart - ARC_WIDTH_OUT - 2 - mScaleWidth;
            } else {
                mPaintTike.setStrokeWidth(2);
                endY = mRadiusChart - ARC_WIDTH_OUT - 2 - mScaleWidth / 2;
            }

            canvas.drawLine(0, mRadiusChart - ARC_WIDTH_OUT - 2, 0, endY, mPaintTike);
            angleDrawed += rotateAngle;
            canvas.rotate(rotateAngle);
        }

        canvas.restore();
    }

    //绘制左右文本
    private void drawChartLeftRightText(Canvas canvas) {

        float mYChartText = mYChartBtm + mPaintText.getTextSize() + DisplayUtil.dip2px(getContext(), 2);

        if (!TextUtils.isEmpty(mTextChartLeft)) {

            mPaintText.setColor(Color.GREEN);
            canvas.drawText(mTextChartLeft, mCenterX - mRadiusChart, mYChartText, mPaintText);
        }

        if (!TextUtils.isEmpty(mTextChartRight)) {

            mPaintText.setColor(Color.RED);
            canvas.drawText(mTextChartRight, mCenterX + mRadiusChart, mYChartText, mPaintText);
        }
    }

    //绘制外边灰色刻度数值
    private void drawBorderScaleGray(Canvas canvas) {

        mPaintNum.setColor(Color.GRAY);
        //绘制外层底部的灰色数值
        double arcNumSingle = Math.PI / (sNums.length - 1);
        //数值与圆心的距离
        float distanceNum = mRadiusChart + DisplayUtil.dip2px(getContext(), 10);
        for (int i = 0; i < sNums.length; i++) {

            double argCurr = arcNumSingle * i;
            float xCurr = (float) (mCenterX - Math.cos(argCurr) * distanceNum);
            float yCurr = (float) (mYChartBtm - Math.sin(argCurr) * distanceNum);
            canvas.drawText(sNums[i], xCurr, yCurr, mPaintNum);
        }
    }

    //绘制外边刻度（渐变颜色）
    private void drawBorderScaleGradient(Canvas canvas) {

        double arcNumSingle = Math.PI / (sNums.length - 1);
        //数值与圆心的距离
        float distanceNum = mRadiusChart + DisplayUtil.dip2px(getContext(), 10);
        int currGradient = (int) Math.floor(mCurrentValue / (sNums.length - 1));
        if (currGradient < sNums.length) {
            for (int i = 0; i <= currGradient; i++) {

                mPaintNumGradient.setARGB(255, rgList.get(i)[0], rgList.get(i)[1], 0);
                double arcCurr = arcNumSingle * i;
                float xCurr = (float) (mCenterX - Math.cos(arcCurr) * distanceNum);
                float yCurr = (float) (mYChartBtm - Math.sin(arcCurr) * distanceNum);
                canvas.drawText(sNums[i], xCurr, yCurr, mPaintNumGradient);
            }
        }
    }

    //绘制指针动画
    private void drawScrollIndexCurrent(Canvas canvas) {

        if (mCurrentValue == 0) {

            drawScrollIndex(canvas, -90);
        } else {

            drawScrollIndex(canvas, (int) ((mCurrentValue / 100) * 180 - 90));
            drawBorderScaleGradient(canvas);
        }
    }

    //根据角度绘制指针
    private void drawScrollIndex(Canvas canvas, int rotation) {

        drawRotateBitmap(canvas, null, mBitmapIndex, rotation, mCenterX - mBitmapIndex.getWidth() / 2, mYChartBtm - mBitmapIndex.getHeight() / 2);
    }

    /**
     * 绘制自旋转位图
     *
     * @param canvas
     * @param paint
     * @param bitmap   位图对象
     * @param rotation 旋转度数
     * @param posX     在canvas的位置坐标
     * @param posY
     */
    private void drawRotateBitmap(Canvas canvas, Paint paint, Bitmap bitmap,
                                  float rotation, float posX, float posY) {
        Matrix matrix = new Matrix();
        int offsetX = bitmap.getWidth() / 2;
        int offsetY = bitmap.getHeight() / 2;
        matrix.postTranslate(-offsetX, -offsetY);
        matrix.postRotate(rotation);
        matrix.postTranslate(posX + offsetX, posY + offsetY);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    //开始动画
    public void doAnimation() {

        doAnimation(1000, 400);
    }

    /**
     * 配置动画的参数
     *
     * @param delay    延迟开始时间/ms
     * @param duration 动画时间/ms
     */
    private void doAnimation(long delay, long duration) {

        ValueAnimator animator = ValueAnimator.ofObject(new PositiveEvaluator(), 0f, mValue);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mCurrentValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        animator.setStartDelay(delay);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    //动画插值器
    private class PositiveEvaluator implements TypeEvaluator<Float> {

        @Override
        public Float evaluate(float v, Float startValue, Float endValue) {

            return startValue + v * (endValue - startValue);
        }
    }

    //设置数据值
    public void setValueWithAnim(int value) {

        if (value > mMaxValue || value < 0) {
            Toast.makeText(getContext(), "数据值超过范围", Toast.LENGTH_SHORT).show();
        } else {

            mValue = value;
            doAnimation();
        }
    }
}
