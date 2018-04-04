package customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by zpf on 2016/8/8.
 * 测试Paint属性的View
 */
public class PainView extends View {

    //控件的宽度、高度
    private int mWidth, mHeight;

    private Paint paint = new Paint();
    //竖线paint
    private Paint mPaintLine;

    private Path path = new Path();

    //拐角处为圆角的Path
    private Path mPathCorner = new Path();
    private Paint mPaintCorner;

    //虚线Path
    private Path mPathDash = new Path();
    private Paint mPaintDash;
    //虚线偏移量
    private int fx = 0;
    private float[] mDashFloat = new float[]{20, 10, 50, 20};
    private DashPathEffect mEffect = new DashPathEffect(mDashFloat, fx);


    public PainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    private void initPaint() {

        paint.setAntiAlias(true);
        paint.setStrokeWidth(66);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

        mPaintLine = new Paint();
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(2);
        mPaintLine.setColor(Color.DKGRAY);

        mPaintCorner = new Paint();
        mPaintCorner.setAntiAlias(true);
        mPaintCorner.setStrokeWidth(2);
        mPaintCorner.setStyle(Paint.Style.STROKE);
        mPaintCorner.setColor(Color.DKGRAY);
        mPaintCorner.setPathEffect(new CornerPathEffect(120));

        mPaintDash = new Paint();
        mPaintDash.setAntiAlias(true);
        mPaintDash.setStrokeWidth(4);
        mPaintDash.setStyle(Paint.Style.STROKE);
        mPaintDash.setColor(Color.BLUE);
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

        paint.setStrokeWidth(66);

        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(100, 100, 500, 100, paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100, 300, 500, 300, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(100, 500, 500, 500, paint);

        paint.setStrokeJoin(Paint.Join.MITER);
        path.moveTo(100, 700);
        path.lineTo(500, 700);
        path.lineTo(500, 900);
        canvas.drawPath(path, paint);

        paint.setStrokeJoin(Paint.Join.ROUND);
        path.moveTo(100, 1000);
        path.lineTo(500, 1000);
        path.lineTo(500, 1200);
        canvas.drawPath(path, paint);

        paint.setStrokeJoin(Paint.Join.BEVEL);
        path.moveTo(100, 1400);
        path.lineTo(500, 1400);
        path.lineTo(500, 1600);
        canvas.drawPath(path, paint);

        mPathCorner.moveTo(700, 100);
        mPathCorner.lineTo(800, 400);
        mPathCorner.lineTo(900, 100);
        canvas.drawPath(mPathCorner, mPaintCorner);

        mPathDash.moveTo(700, 500);
        mPathDash.lineTo(800, 800);
        mPathDash.lineTo(900, 500);
        mPaintDash.setPathEffect(mEffect);
        canvas.drawPath(mPathDash, mPaintDash);

        canvas.drawLine(100, 0, 100, mHeight, mPaintLine);
    }

    public void startAnim() {

        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(600);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                fx = (int) animation.getAnimatedValue();
                mEffect = updateEffect(fx);
                postInvalidate();
            }
        });
        animator.start();
    }

    //TODO 不停创建对象消耗内存问题
    private DashPathEffect updateEffect(int newData) {

        return new DashPathEffect(mDashFloat, newData);
    }
}
