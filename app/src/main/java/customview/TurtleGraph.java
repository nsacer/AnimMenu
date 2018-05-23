package customview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import utils.DisplayUtil;

/**
 * Created by zpf on 2016/11/3.
 */
public class TurtleGraph extends View {


    /** 度量最大值 */
    private final float DEFAULT_MAX_VALUE = 200;
    /** 标题的默认大小 */
    private final int DEFAULT_TEXT_SIZE = 40;
    /** 环线的默认宽度 */
    private final float DEFAULT_RING_LINE_WIDTH = 2;
    /** 默认的圆环层数 */
    private final int DEFAULT_RING_NUM = 5;

    //数据个数
    private int dataCount = 5;
    //每个角的弧度
    private float radian = (float) (Math.PI * 2 / dataCount);
    //雷达图半径
    private float radius;
    //中心X坐标
    private int centerX;
    //中心Y坐标
    private int centerY;
    //各维度标题
    private String[] titles = {"履约能力", "信用历史", "人脉关系", "行为偏好", "身份特质", "消费能力"};
    //各维度图标
    private int[] icons = {R.mipmap.ic_check_white_24dp, R.mipmap.ic_3d_rotation_white_24dp, R.mipmap.ic_accessibility_white_24dp,
            R.mipmap.ic_account_balance_white_24dp, R.mipmap.ic_accessible_white_24dp};

    //各维度分值
    private float[] defaultData = {0, 0, 0, 0, 0};
    private float[] data;
//    private float[] data = {170, 180, 160, 170, 180};
    private float[] setData = {170, 180, 160, 170, 180};

    private ValueAnimator animator;

    //数据最大值
    private float maxValue = DEFAULT_MAX_VALUE;

    //雷达图与标题的间距
    private int radarMargin = DisplayUtil.dip2px(getContext(), 15);
    //雷达区画笔
    private Paint mainPaint;
    private int colorRingLine = ContextCompat.getColor(getContext(), R.color.colorBlack);
    private float ringLineWidth = DEFAULT_RING_LINE_WIDTH;
    //数据区画笔
    private Paint valuePaint;
    private int colorValue = ContextCompat.getColor(getContext(), R.color.colorAccent);
    //分数画笔
    private Paint scorePaint;
    private int colorScore = ContextCompat.getColor(getContext(), R.color.colorBlack);
    //标题画笔
    private Paint titlePaint;
    private int colorTitle = ContextCompat.getColor(getContext(), R.color.colorBlack);
    //图标画笔
    private Paint iconPaint;
    private int colorIcon = ContextCompat.getColor(getContext(), R.color.colorBlack);
    //分数大小
    private float scoreSize = DEFAULT_TEXT_SIZE;
    //标题文字大小
    private float titleSize = DEFAULT_TEXT_SIZE;

    public TurtleGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefineAttr(context, attrs);
        initPaint();
    }

    public TurtleGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefineAttr(context, attrs);
        initPaint();
    }

    private ValueAnimator getAnimator() {
        return animator;
    }

    private void setAnimator(ValueAnimator animator) {
        this.animator = animator;
    }

    /** 设置xml自定义属性 */
    private void initDefineAttr(Context context, AttributeSet attrs) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TurtleGraph);
        int count = array.getIndexCount();

        for (int i=0; i<count; i++) {

            int attr = array.getIndex(i);
            switch (attr) {

                case R.styleable.TurtleGraph_turtleMaxValue:

                    maxValue = array.getFloat(attr, DEFAULT_MAX_VALUE);
                    break;

                case R.styleable.TurtleGraph_turtleRingLineColor:

                    colorRingLine = array.getColor(attr, ContextCompat.getColor(context, R.color.colorBlack));
                    break;

                case R.styleable.TurtleGraph_turtleRingLineWidth:

                    ringLineWidth = array.getDimension(attr, DEFAULT_RING_LINE_WIDTH);
                    break;

                case R.styleable.TurtleGraph_turtleColorValue:

                    colorValue = array.getColor(attr, ContextCompat.getColor(context, R.color.colorAccent));
                    break;

                case R.styleable.TurtleGraph_turtleColorTitle:

                    colorTitle = array.getColor(attr, ContextCompat.getColor(context, R.color.colorBlack));
                    break;

                case R.styleable.TurtleGraph_turtleTitleSize:

                    titleSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                            array.getDimensionPixelSize(attr, DEFAULT_TEXT_SIZE), metrics);
                    break;

                default:
                    break;
            }
        }
        array.recycle();
    }

    /**
     * 初始化Paint
     */
    private void initPaint() {

        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeWidth(ringLineWidth);
        mainPaint.setColor(colorRingLine);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(colorValue);
        valuePaint.setAlpha(120);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        valuePaint.setStrokeWidth(1);

        scorePaint = new Paint();
        scorePaint.setAntiAlias(true);
        scorePaint.setColor(colorScore);
        scorePaint.setStyle(Paint.Style.FILL);
        scorePaint.setStrokeWidth(1);
        scorePaint.setTextSize(56);

        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setColor(colorTitle);
        titlePaint.setStyle(Paint.Style.FILL);
        titlePaint.setStrokeWidth(1);
        titlePaint.setTextSize(titleSize);

        iconPaint = new Paint();
        iconPaint.setAntiAlias(true);
        iconPaint.setColor(colorIcon);
        iconPaint.setStyle(Paint.Style.STROKE);
        iconPaint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPolygon(canvas);

        drawLines(canvas);

        drawRegion(canvas);

//        drawScore(canvas);

        drawTitle(canvas);

        drawIcon(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //雷达图半径
        radius = Math.min(h, w) / 2 * 0.5f;
        //中心坐标
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 绘制多边形
     *
     * @param canvas 画布
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            if (i == 0) {
                path.moveTo(getPoint(i).x, getPoint(i).y);
            } else {
                path.lineTo(getPoint(i).x, getPoint(i).y);
            }
        }

        //闭合路径
        path.close();
        canvas.drawPath(path, mainPaint);

    }

    /**
     * 绘制连接线
     *
     * @param canvas 画布
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            path.lineTo(getPoint(i).x, getPoint(i).y);
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 获取雷达图上各个点的坐标
     *
     * @param position 坐标位置（右上角为0，顺时针递增）
     * @return 坐标
     */
    private Point getPoint(int position) {
        return getPoint(position, 0, 1);
    }

    /**
     * 获取雷达图上各个点的坐标（包括维度标题与图标的坐标）
     *
     * @param position    坐标位置
     * @param radarMargin 雷达图与维度标题的间距
     * @param percent     覆盖区的的百分比
     * @return 坐标
     */
    private Point getPoint(int position, int radarMargin, float percent) {
        int x = 0;
        int y = 0;

        if (position == 0) {
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 1) {
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 2) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 3) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 4) {
            x = centerX;
            y = (int) (centerY - (radius + radarMargin) * percent);
        }

        return new Point(x, y);
    }

    /**
     * 绘制覆盖区域
     *
     * @param canvas 画布
     */
    private void drawRegion(Canvas canvas) {

        if(data == null)
            return;

        Path path = new Path();

        for (int i = 0; i < dataCount; i++) {
            //计算百分比
            float percent = data[i] / maxValue;
            int x = getPoint(i, 0, percent).x;
            int y = getPoint(i, 0, percent).y;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        //绘制填充区域的边界
        path.close();
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);

        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);

    }

    /** 添加数据动态改变的动画 */
    public void doAnimator() {

        ValueAnimator animator = ValueAnimator.ofObject(new DataEvaluator(), defaultData, setData);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                data = (float[]) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(800);
//        animator.setInterpolator(new BounceInterpolator());
        animator.setInterpolator(new LinearOutSlowInInterpolator());
//        animator.setInterpolator(new FastOutSlowInInterpolator());

        this.setAnimator(animator);
        animator.start();
    }

    /** Cancel Animator */
    public void cancelAnimator() {

        this.getAnimator().cancel();
    }

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

    /**
     * 绘制分数
     *
     * @param canvas 画布
     */
    private void drawScore(Canvas canvas) {
        int score = 0;
        //计算总分
        if (data == null)
            return;

        for (int i = 0; i < dataCount; i++) {
            score += data[i];
        }
        canvas.drawText(score + "", centerX, centerY + scoreSize / 2, scorePaint);
    }

    /**
     * 绘制标题
     *
     * @param canvas 画布
     */
    private void drawTitle(Canvas canvas) {
        for (int i = 0; i < dataCount; i++) {
            int x = getPoint(i, radarMargin, 1).x;
            int y = getPoint(i, radarMargin, 1).y;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icons[i]);
            int iconHeight = bitmap.getHeight();
            float titleWidth = titlePaint.measureText(titles[i]);

            //底下两个角的坐标需要向下移动半个图片的位置（1、2）
            if (i == 1) {
                y += (iconHeight / 2);
            } else if (i == 2) {
                x -= titleWidth;
                y += (iconHeight / 2);
            } else if (i == 3) {
                x -= titleWidth;
            } else if (i == 4) {
                x -= titleWidth / 2;
            }
            canvas.drawText(titles[i], x, y, titlePaint);
        }
    }

    /**
     * 绘制图标
     *
     * @param canvas 画布
     */
    private void drawIcon(Canvas canvas) {
        for (int i = 0; i < dataCount; i++) {
            int x = getPoint(i, radarMargin, 1).x;
            int y = getPoint(i, radarMargin, 1).y;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icons[i]);
            int iconWidth = bitmap.getWidth();
            int iconHeight = bitmap.getHeight();
            float titleWidth = titlePaint.measureText(titles[i]);

            //上面获取到的x、y坐标是标题左下角的坐标
            //需要将图标移动到标题上方居中位置
            if (i == 0) {
                x += (titleWidth - iconWidth) / 2;
                y -= (iconHeight + getTextHeight(titlePaint));
            } else if (i == 1) {
                x += (titleWidth - iconWidth) / 2;
                y -= (iconHeight / 2 + getTextHeight(titlePaint));
            } else if (i == 2) {
                x -= (iconWidth + (titleWidth - iconWidth) / 2);
                y -= (iconHeight / 2 + getTextHeight(titlePaint));
            } else if (i == 3) {
                x -= (iconWidth + (titleWidth - iconWidth) / 2);
                y -= (iconHeight + getTextHeight(titlePaint));
            } else if (i == 4) {
                x -= iconWidth / 2;
                y -= (iconHeight + getTextHeight(titlePaint));
            }

            canvas.drawBitmap(bitmap, x, y, iconPaint);
        }
    }

    /**
     * 获取文本的高度
     *
     * @param paint 文本绘制的画笔
     * @return 文本高度
     */
    private int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }
}
