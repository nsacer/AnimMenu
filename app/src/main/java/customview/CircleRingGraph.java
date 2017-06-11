package customview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.zpf.animmenu.R;

/**
 * Created by zpf on 2016/11/7.
 */
public class CircleRingGraph extends View {

    /**
     * 默认的Padding
     */
    private final float DEFAULT_PADDING = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            16, getResources().getDisplayMetrics());

    /** 默认的进度条的宽度 */
    private final float DEFAULT_STROKE_WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            16, getResources().getDisplayMetrics());

    /**
     * centerX:中心坐标点x
     * centerY:中心坐标点y
     * radiusOut:背景色圆形的半径
     * radiusIn:前景色圆形的半径
     * */
    private float centerX, centerY, radiusOut, radiusIn;

    /**
     * paintOut:背景色圆形画笔
     * paintIn:前景色圆形画笔
     * paintPro:进度条的画笔
     * paintTxt:中心数据值文本的画笔
     * */
    private Paint paintOut, paintIn, paintPro, paintTxt;

    /**
     * colorOut: 进度条背景色
     * colorIn: 圆心背景色
     * colorPro: 进度条的颜色
     */
    private int colorOut = getResources().getColor(R.color.colorGray),
            colorIn = getResources().getColor(R.color.circle_in),
            colorPro = getResources().getColor(R.color.circle_progress_blue);
    /**
     * 用来画圆弧的RectF
     */
    private RectF rectF;

    /**
     * 进度条的宽度
     */
    private float strokeWidth = DEFAULT_STROKE_WIDTH;

    /**
     * 显示圆形进度条的头部
     */
    private boolean roundCap = Boolean.FALSE;
    /**
     * 进度条显示的进度
     */
    private float progress = 0;
    /**
     * 圆心位置显示的Text
     * centerText: 圆心位置的文本信息
     * colorCenterText: TextView文字颜色
     * centerTextSize: TextView文字大小
     */
    private String centerText;
    private int centerTextColor = getResources().getColor(R.color.colorBlack);
    private float centerTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
            getResources().getDisplayMetrics());

    /**
     * textBaseLineX:绘制文本的基线x坐标点
     * textBaseLineY：绘制文本的基线y坐标点
     * */
    private float textBaseLineX, textBaseLineY;

    public CircleRingGraph(Context context) {
        super(context);
    }

    public CircleRingGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefineAttr(context, attrs);
    }

    public CircleRingGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefineAttr(context, attrs);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        doAnimation();
    }

    /**
     * 获取自定义的属性值
     */
    private void initDefineAttr(Context context, AttributeSet attrs) {

        if (attrs == null)
            return;

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleRingGraph);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {

            int attr = typedArray.getIndex(i);
            switch (attr) {

                case R.styleable.CircleRingGraph_progressBackgroundColor:

                    colorOut = typedArray.getColor(attr, context.getResources().getColor(R.color.circle_out_blue));
                    break;

                case R.styleable.CircleRingGraph_progressColor:

                    colorPro = typedArray.getColor(attr, context.getResources().getColor(R.color.circle_progress_blue));
                    break;

                case R.styleable.CircleRingGraph_circleHeartColor:

                    colorIn = typedArray.getColor(attr, context.getResources().getColor(R.color.circle_in));
                    break;

                case R.styleable.CircleRingGraph_progressRoundCap:

                    roundCap = typedArray.getBoolean(attr, Boolean.FALSE);
                    break;

                case R.styleable.CircleRingGraph_progressWidth:

                    strokeWidth = typedArray.getDimension(attr, DEFAULT_STROKE_WIDTH);
                    break;

                case R.styleable.CircleRingGraph_centerText:

                    centerText = typedArray.getString(attr);
                    break;

                case R.styleable.CircleRingGraph_centerTextColor:

                    centerTextColor = typedArray.getColor(attr, context.getResources().getColor(R.color.colorBlack));
                    break;

                case R.styleable.CircleRingGraph_centerTextSize:

                    centerTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                            typedArray.getDimensionPixelSize(attr, 16), displayMetrics);
                    break;

                default:
                    break;
            }
        }

        typedArray.recycle();

        initPaint();
    }

    /**
     * init Paint
     */
    private void initPaint() {

        paintOut = new Paint();
        paintOut.setAntiAlias(true);
        paintOut.setStyle(Paint.Style.FILL);
        paintOut.setColor(colorOut);

        paintIn = new Paint();
        paintIn.setAntiAlias(true);
        paintIn.setStyle(Paint.Style.FILL);
        paintIn.setColor(colorIn);

        paintPro = new Paint();
        paintPro.setAntiAlias(true);
        if (roundCap)
            paintPro.setStrokeCap(Paint.Cap.ROUND);
        paintPro.setStyle(Paint.Style.STROKE);
        paintPro.setStrokeWidth(strokeWidth);
        paintPro.setColor(colorPro);

//        setLayerType(LAYER_TYPE_SOFTWARE, null);

        //setMaskFilter
//        paintPro.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.INNER));
        //set shadow
//        paintPro.setShadowLayer(40, 0, 0, getResources().getColor(R.color.black_alpha_3));

        //雷达渐变
//        Shader shaderPro = new SweepGradient(centerX, centerY,
//                getResources().getColor(R.color.colorPink), getResources().getColor(R.color.colorGreen));
//        paintPro.setShader(shaderPro);

        //线性渐变
//        Shader shaderLinear = new LinearGradient(0, 0, 200, 0, Color.WHITE, Color.RED,
//                Shader.TileMode.CLAMP);
//        paintPro.setShader(shaderLinear);

        paintTxt = new Paint();
        paintTxt.setAntiAlias(true);
        paintTxt.setTextSize(centerTextSize);
        paintTxt.setTextAlign(Paint.Align.CENTER);
        paintTxt.setColor(centerTextColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        radiusOut = Math.min(w, h) / 2 - DEFAULT_PADDING;
        radiusIn = radiusOut - strokeWidth;
        centerX = w / 2;
        centerY = h / 2;

        float left = centerX - radiusOut + strokeWidth / 2;
        float top = centerY - radiusOut + strokeWidth / 2;
        float right = left + radiusOut * 2 - strokeWidth;
        float bottom = top + radiusOut * 2 - strokeWidth;
        rectF = new RectF(left, top, right, bottom);

        textBaseLineX = centerX;
        textBaseLineY = centerY + centerTextSize / 3;

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawCircleRing(canvas);
        super.onDraw(canvas);
    }

    /**
     * draw circle ring
     */
    private void drawCircleRing(Canvas canvas) {

        // 画灰色的圆
        canvas.drawCircle(centerX, centerY, radiusOut, paintOut);

        // 画进度环
        if (progress != 0f) {

            canvas.drawArc(rectF, -90, progress, false, paintPro);
        }

        // 画内填充圆
        canvas.drawCircle(centerX, centerY, radiusIn, paintIn);

        //画中心位置文字
        canvas.drawText((int) progress + "", textBaseLineX, textBaseLineY, paintTxt);
    }

    public void doAnimation() {

        ValueAnimator animator = ValueAnimator.ofObject(new ProgressEvaluator(), 0f, progress);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(1000);
        animator.setInterpolator(new OvershootInterpolator());
        animator.start();
    }

    private class ProgressEvaluator implements TypeEvaluator<Float> {
        @Override
        public Float evaluate(float fraction, Float startValue, Float endValue) {

            return startValue + fraction * (endValue - startValue);
        }
    }
}
