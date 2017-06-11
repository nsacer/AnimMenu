package customview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.example.zpf.animmenu.R;


/**
 * Created by zpf on 2016/11/8.
 */
public class SpiderChart extends View {

    /** 默认参数点的个数 */
    private final int DEFAULT_POINT_NUM = 5;

    /** 默认的圆环数量 */
    private final int DEFAULT_RING_NUM = 5;

    /** 圆环线的默认的宽度 */
    private final float DEFAULT_RING_LINE_WIDTH = 2;

    /** 默认的数值圆点的半径 */
    private final float DEFAULT_VALUE_POINT_RADIUS = 8;

    /** 默认的数值圆点的半径 */
    private final float DEFAULT_VALUE_INNER_POINT_RADIUS = 4;

    /** 默认的数值圆点连接线的宽度 */
    private final float DEFAULT_VALUE_LINE_WIDTH = 2;

    /** 默认的文本的大小 */
    private final int DEFAULT_TEXT_SIZE = 22;

    /** 标题距离数据顶点的距离 */
    private final float DEFAULT_TITLE_MARGIN = 24;

    /** 默认数据集 */
    private final float[] DEFAULT_DATA = {0, 0, 0, 0, 0};

    /** 圆环的个数 */
    private int ringNum = DEFAULT_RING_NUM;

    /** 图的最大值 */
    private float maxValue = 100;

    /** 存储传入的数据值 */
    private float[] data;

    /** 圆心坐标、半径(半径为长、宽中最小值的1/4) */
    private int centerX, centerY;
    private float radius;

    /** 每两个点之间夹角的弧度 */
    private float radian = (float) (Math.PI * 2 / DEFAULT_POINT_NUM);

    /** 单个环半径 */
    private float radiusSingle;

    /** 方形环浅色、深色 */
    private int spiderRingLightColor = ContextCompat.getColor(getContext(), R.color.spider_ring_light);
    private int spiderRingDarkColor = ContextCompat.getColor(getContext(), R.color.spider_ring_dark);

    /** 标题的颜色、大小、距离标题顶点的距离 */
    private int spiderTitleColor = ContextCompat.getColor(getContext(), R.color.colorGrayDarkDark);
    private float spiderTitleSize = DEFAULT_TEXT_SIZE;
    private float titleMargin = DEFAULT_TITLE_MARGIN;

    /** 标题（顶部为titleOne, 顺时针方向） */
    private String spiderTitleOne = "雪球价值",
            spiderTitleTwo = "市场关注度",
            spiderTitleThree = "F点密集度",
            spiderTitleFour = "资金热度",
            spiderTitleFive = "主力深度";

    /** 内部圆环线的颜色、宽度 */
    private int spiderRingLineColor = ContextCompat.getColor(getContext(), R.color.spider_ring_line);
    private float spiderRingLineWidth = DEFAULT_RING_LINE_WIDTH;

    /** 数据值大圆点颜色、半径 */
    private int spiderValuePointColor = ContextCompat.getColor(getContext(), R.color.spider_value_line);
    private float spiderValuePointRadius = DEFAULT_VALUE_POINT_RADIUS;

    /** 数据值小圆点颜色、半径 */
    private int spiderValueInnerPointColor = ContextCompat.getColor(getContext(), R.color.spider_ring_light);
    private float spiderValueInnerPointRadius = DEFAULT_VALUE_INNER_POINT_RADIUS;

    /** 数值圆点连接线颜色、宽度 */
    private int spiderValueLineColor = ContextCompat.getColor(getContext(), R.color.spider_value_line);
    private float spiderValueLineWidth = DEFAULT_VALUE_LINE_WIDTH;

    /** 数据值包围范围的颜色 */
    private int spiderValueRectColor = ContextCompat.getColor(getContext(), R.color.spider_value_rect);

    /** 内层环的Paint */
    private Paint paintRing = new Paint();

    /** 内层环线的Paint */
    private Paint paintRingLine = new Paint();

    /** 内层数值圆点的Paint */
    private Paint paintValuePoint = new Paint();

    /** 内层数值连接线的Paint */
    private Paint paintValueLine = new Paint();

    /** 内层数值包围区域的Paint */
    private Paint paintValueRect = new Paint();

    /** 标题的Paint */
    private Paint paintTitle = new Paint();

    /** 动画 */
    private ValueAnimator animator;

    public SpiderChart(Context context) {
        super(context);
    }

    public SpiderChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefineAttrs(context, attrs);
        initPaint();
    }

    public SpiderChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefineAttrs(context, attrs);
        initPaint();
    }

    private ValueAnimator getAnimator() {
        return animator;
    }

    private void setAnimator(ValueAnimator animator) {
        this.animator = animator;
    }

    /** 设置自定义xml属性值 */
    private void initDefineAttrs(Context context, AttributeSet attrs) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SpiderChart);
        int count = array.getIndexCount();

        for (int i=0; i<count; i++) {

            int attr = array.getIndex(i);
            switch (attr) {

                case R.styleable.SpiderChart_spiderRingNum:

                    ringNum = array.getInt(attr, DEFAULT_RING_NUM);
                    break;

                case R.styleable.SpiderChart_spiderTitleMargin:

                    titleMargin = array.getDimension(attr, DEFAULT_TITLE_MARGIN);
                    break;

                case R.styleable.SpiderChart_spiderTitleColor:

                    spiderTitleColor = array.getColor(attr,
                            ContextCompat.getColor(context, R.color.colorGrayDarkDark));
                    break;

                case R.styleable.SpiderChart_spiderTitleSize:

                    spiderTitleSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                            array.getDimensionPixelSize(attr, DEFAULT_TEXT_SIZE), metrics);
                    break;

                case R.styleable.SpiderChart_spiderTitleOne:

                    spiderTitleOne = array.getString(attr);
                    break;

                case R.styleable.SpiderChart_spiderTitleTwo:

                    spiderTitleTwo = array.getString(attr);
                    break;

                case R.styleable.SpiderChart_spiderTitleThree:

                    spiderTitleThree = array.getString(attr);
                    break;

                case R.styleable.SpiderChart_spiderTitleFour:

                    spiderTitleFour = array.getString(attr);
                    break;

                case R.styleable.SpiderChart_spiderTitleFive:

                    spiderTitleFive = array.getString(attr);
                    break;

                case R.styleable.SpiderChart_spiderRingLineColor:

                    spiderRingLineColor = array.getColor(attr,
                            ContextCompat.getColor(context, R.color.spider_ring_line));
                    break;

                case R.styleable.SpiderChart_spiderRingLineWidth:

                    spiderRingLineWidth = array.getDimension(attr, DEFAULT_RING_LINE_WIDTH);
                    break;

                case R.styleable.SpiderChart_spiderRingLightColor:

                    spiderRingLightColor = array.getColor(attr,
                            ContextCompat.getColor(context, R.color.spider_ring_light));
                    break;

                case R.styleable.SpiderChart_spiderRingDarkColor:

                    spiderRingDarkColor = array.getColor(attr,
                            ContextCompat.getColor(context, R.color.spider_ring_dark));
                    break;

                case R.styleable.SpiderChart_spiderValuePointColor:

                    spiderValuePointColor = array.getColor(attr,
                            ContextCompat.getColor(context, R.color.spider_value_line));
                    break;

                case R.styleable.SpiderChart_spiderValuePointRadius:

                    spiderValuePointRadius = array.getDimension(attr, DEFAULT_VALUE_POINT_RADIUS);
                    break;

                case R.styleable.SpiderChart_spiderValueInnerPointColor:

                    spiderValueInnerPointColor = array.getColor(attr,
                            ContextCompat.getColor(getContext(), R.color.spider_ring_light));
                    break;

                case R.styleable.SpiderChart_spiderValueInnerPointRadius:

                    spiderValueInnerPointRadius = array.getDimension(attr,
                            DEFAULT_VALUE_INNER_POINT_RADIUS);
                    break;

                case R.styleable.SpiderChart_spiderValueLineColor:

                    spiderValueLineColor = array.getColor(attr,
                            ContextCompat.getColor(getContext(), R.color.spider_value_line));
                    break;

                case R.styleable.SpiderChart_spiderValueLineWidth:

                    spiderValueLineWidth = array.getDimension(attr, DEFAULT_VALUE_LINE_WIDTH);
                    break;

                case R.styleable.SpiderChart_spiderValueRectColor:

                    spiderValueRectColor = array.getColor(attr,
                            ContextCompat.getColor(getContext(), R.color.spider_value_rect));
                    break;

                default:
                    break;
            }
        }

        array.recycle();
    }

    /** 初始化绘图Paint */
    private void initPaint() {

        paintRing.setAntiAlias(true);
        paintRing.setStrokeWidth(spiderRingLineWidth);
        paintRing.setStyle(Paint.Style.FILL);

        paintRingLine.setAntiAlias(true);
        paintRingLine.setStrokeWidth(spiderRingLineWidth);
        paintRingLine.setStyle(Paint.Style.STROKE);
        paintRingLine.setColor(spiderRingLineColor);

        paintValuePoint.setAntiAlias(true);
        paintValuePoint.setColor(spiderValuePointColor);
        paintValuePoint.setStrokeWidth(spiderValuePointRadius);
        paintValuePoint.setStyle(Paint.Style.FILL);

        paintValueLine.setAntiAlias(true);
        paintValueLine.setColor(spiderValueLineColor);
        paintValueLine.setStrokeWidth(spiderValueLineWidth);
        paintValueLine.setStyle(Paint.Style.STROKE);

        paintValueRect.setAntiAlias(true);
        paintValueRect.setColor(spiderValueRectColor);
        paintValueRect.setStyle(Paint.Style.FILL);

        paintTitle.setAntiAlias(true);
        paintTitle.setTextAlign(Paint.Align.CENTER);
        paintTitle.setColor(spiderTitleColor);
        paintTitle.setStrokeWidth(1);
        paintTitle.setStyle(Paint.Style.FILL);
        paintTitle.setTextSize(spiderTitleSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        getBaseInfo(w, h);
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /** 获取基本的数值信息 */
    private void getBaseInfo(int w, int h) {

        radius = Math.min(w, h) * 3 / 8;
        radiusSingle = radius / ringNum;
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackground(canvas);

        drawBackgroundLine(canvas);

        drawTitle(canvas);

        drawValue(canvas);
    }

    /** draw background */
    private void drawBackground(Canvas canvas) {


        for (int n=0; n<DEFAULT_RING_NUM; n++) {

            float radiusRing = radius - (radiusSingle * n);

            Path pathBg = new Path();

            for (int i=0; i<DEFAULT_POINT_NUM; i++) {

                int x = getPoint(i, radiusRing).x;
                int y = getPoint(i, radiusRing).y;

                if(i == 0)
                    pathBg.moveTo(x, y);
                else
                    pathBg.lineTo(x, y);
            }
            pathBg.close();

            if(n % 2 == 0)
                paintRing.setColor(spiderRingLightColor);
            else
                paintRing.setColor(spiderRingDarkColor);
            canvas.drawPath(pathBg, paintRing);
        }

    }

    /** draw Background line */
    private void drawBackgroundLine(Canvas canvas) {

        for (int n=0; n<DEFAULT_RING_NUM; n++) {

            float radiusRing = radius - (radiusSingle * n);

            Path pathRingLine = new Path();

            for (int i=0; i<DEFAULT_POINT_NUM; i++) {

                int x = getPoint(i, radiusRing).x;
                int y = getPoint(i, radiusRing).y;

                if(i == 0)
                    pathRingLine.moveTo(x, y);
                else
                    pathRingLine.lineTo(x, y);
            }
            pathRingLine.close();

            canvas.drawPath(pathRingLine, paintRingLine);

            if(n == DEFAULT_POINT_NUM - 1) {

                for (int j=0; j<DEFAULT_POINT_NUM; j++) {

                    int x = getPoint(j, radius).x;
                    int y = getPoint(j, radius).y;
                    pathRingLine.reset();
                    pathRingLine.moveTo(centerX, centerY);
                    pathRingLine.lineTo(x, y);
                    canvas.drawPath(pathRingLine, paintRingLine);
                }
            }
        }
    }

    /** draw title */
    private void drawTitle(Canvas canvas) {

        float radiusTitle = radius + titleMargin;
        for (int i=0; i<DEFAULT_POINT_NUM; i++) {

            int x = getPoint(i, radiusTitle).x;
            int y = getPoint(i, radiusTitle).y;

            switch (i) {

                case 0:

                    canvas.drawText(spiderTitleOne, x, y, paintTitle);
                    break;

                case 1:

                    x += spiderTitleSize * 2;
                    canvas.drawText(spiderTitleTwo, x, y, paintTitle);
                    break;

                case 2:

                    y += spiderTitleSize;
                    canvas.drawText(spiderTitleThree, x, y, paintTitle);
                    break;

                case 3:

                    y += spiderTitleSize;
                    canvas.drawText(spiderTitleFour, x, y, paintTitle);
                    break;

                case 4:

                    x -= spiderTitleSize * 2;
                    canvas.drawText(spiderTitleFive, x, y, paintTitle);
                    break;
            }
        }
    }

    /** draw value rect */
    private void drawValue(Canvas canvas) {

        if(data == null)
            return;

        Path path = new Path();

        float[] fx = new float[5];
        float[] fy = new float[5];

        for (int i=0; i<DEFAULT_POINT_NUM; i++) {

            float percent = data[i] / maxValue;
            float x = getPoint(i, radius, percent).x;
            float y = getPoint(i, radius, percent).y;

            if(i ==0)
                path.moveTo(x, y);
            else
                path.lineTo(x, y);

            fx[i] = x;
            fy[i] = y;

        }
        path.close();
        canvas.drawPath(path, paintValueLine);
        canvas.drawPath(path, paintValueRect);

        for (int j=0; j<fx.length; j++) {

            paintValuePoint.setColor(spiderValuePointColor);
            canvas.drawCircle(fx[j], fy[j], spiderValuePointRadius, paintValuePoint);

            paintValuePoint.setColor(spiderValueInnerPointColor);
            canvas.drawCircle(fx[j], fy[j], spiderValueInnerPointRadius, paintValuePoint);
        }
    }

    /**
     * 获取雷达图上各个点的坐标（包括维度标题与图标的坐标）
     * 最顶部坐标点position == 0， 顺时针++
     * @param position    坐标位置
     * @param radius 坐标点所在圆的半径
     * @return 坐标
     */
    private Point getPoint(int position, float radius) {

        return getPoint(position, radius, 1);
    }


    /**
     * 获取雷达图上各个点的坐标（包括维度标题与图标的坐标）
     * 最顶部坐标点position == 0， 顺时针++
     * @param position    坐标位置
     * @param radius 坐标点所在圆的半径
     * @param percent 占总值的比例
     * @return 坐标
     */
    private Point getPoint(int position, float radius, float percent) {

        int x = 0;
        int y = 0;

        if (position == 0) {

            x = centerX;
            y = (int) (centerY - radius * percent);

        } else if (position == 1) {

            x = (int) (centerX + radius * Math.sin(radian) * percent);
            y = (int) (centerY - radius * Math.cos(radian) * percent);

        } else if (position == 2) {
            x = (int) (centerX + radius * Math.sin(radian / 2) * percent);
            y = (int) (centerY + radius * Math.cos(radian / 2) * percent);

        } else if (position == 3) {

            x = (int) (centerX - radius * Math.sin(radian / 2) * percent);
            y = (int) (centerY + radius * Math.cos(radian / 2) * percent);

        } else if (position == 4) {

            x = (int) (centerX - radius * Math.sin(radian) * percent);
            y = (int) (centerY - radius * Math.cos(radian) * percent);
        }

        return new Point(x, y);
    }

    /** 开始动画 */
    public void doAnimation(float[] insertData) {

        if(insertData == null || insertData.length != 5)
            return;

        ValueAnimator animator = ValueAnimator.ofObject(new DataEvaluator(), DEFAULT_DATA, insertData);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                data = (float[]) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setStartDelay(200);
        animator.setDuration(800);
        animator.setInterpolator(new LinearOutSlowInInterpolator());

        this.setAnimator(animator);
        animator.start();
    }

    /** Cancel Animator */
    public void cancelAnimator() {

        this.getAnimator().cancel();
    }

    /** 动画插值器 */
    private class DataEvaluator implements TypeEvaluator<float[]> {

        @Override
        public float[] evaluate(float fraction, float[] startValue, float[] endValue) {

            float[] newData = new float[startValue.length];
            for (int i=0; i<startValue.length; i++) {

                newData[i] = startValue[i] + fraction * (endValue[i] - startValue[i]);
            }
            return newData;
        }
    }
}
