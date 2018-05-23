package customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zpf on 2016/8/8.
 */
public class LineView extends View {

    private Paint paint = new Paint();
    private Path path;
    //控件的宽度
    private int width = 0;
    private int height = 0;
    private int xCurrent = 0;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.clipRect(0, 0, xCurrent, height, Region.Op.INTERSECT);
        canvas.clipRect(0, 0, xCurrent, height);
        path = getPath();
        canvas.drawPath(path, paint);

        canvas.translate(0, 160);
//        paint.setPathEffect(new DiscretePathEffect(4, 6));
        canvas.drawPath(path, paint);

        canvas.translate(0, 160);
//        paint.setPathEffect(new DiscretePathEffect(9, 15));
        canvas.drawPath(path, paint);


    }

    private void initPaint() {

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
    }

    private Path getPath() {

        Path path = new Path();
        path.moveTo(0, 0);

        for (int i = 0; i < 40; i++) {

            path.lineTo(i * 40, 20 - i * 2);
//            path.lineTo(i * 40, (float) (Math.random() * 150));
        }

        return path;
    }

    public void doAnimator() {

        if (width == 0) return;
        ValueAnimator animator = ValueAnimator.ofInt(0, width);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                xCurrent = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setDuration(3000);
        animator.start();
    }
}
