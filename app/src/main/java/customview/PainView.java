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

import com.example.zpf.animmenu.R;

/**
 * Created by zpf on 2016/8/8.
 */
public class PainView extends View {

    private Paint paint = new Paint();

    private int fx = 0;//虚线偏移量

    public PainView(Context context) {
        super(context);
    }

    public PainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(90);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(100, 100, 500, 100, paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100, 300, 500, 300, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(100, 500, 500, 500, paint);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        Path path = new Path();
        path.moveTo(100, 700);
        path.lineTo(500, 700);
        path.lineTo(100,900);
        canvas.drawPath(path, paint);

        paint.setStrokeJoin(Paint.Join.ROUND);
        path.moveTo(100, 1000);
        path.lineTo(500, 1000);
        path.lineTo(100, 1200);
        canvas.drawPath(path, paint);

        paint.setStrokeJoin(Paint.Join.BEVEL);
        path.moveTo(100, 1400);
        path.lineTo(500, 1400);
        path.lineTo(100, 1600);
        canvas.drawPath(path, paint);

        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);

        canvas.drawLine(100, 50, 100, 1500, paint);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(700, 100);
        path.lineTo(800, 400);
        path.lineTo(900, 100);
        paint.setPathEffect(new CornerPathEffect(120));
        canvas.drawPath(path, paint);

        path.moveTo(700, 500);
        path.lineTo(800, 800);
        path.lineTo(900, 500);
        paint.setPathEffect(new DashPathEffect(new float[]{20, 10, 50, 20}, fx));
        canvas.drawPath(path, paint);
    }

    public void startAnim(){

        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(600);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                fx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
