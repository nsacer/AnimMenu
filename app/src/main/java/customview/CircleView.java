package customview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import model.Circle;

/**
 * Created by zpf on 2016/11/4.
 */
public class CircleView extends View {

    private Circle circle;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(circle != null) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(8);

            float centerX = getWidth() / 2;
            float centerY = getHeight() / 2;
            canvas.drawCircle(centerX, centerY, circle.getRadius(), paint);
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void doAnimation() {

        float startR = getWidth() / 16;
        float endR = getWidth() / 3;

        ValueAnimator animator = ValueAnimator
                .ofObject(new CircleEvaluator(), new Circle(startR), new Circle(endR));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                circle = (Circle) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(1600);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();

    }

    private class CircleEvaluator implements TypeEvaluator<Circle> {

        @Override
        public Circle evaluate(float fraction, Circle startValue, Circle endValue) {

            float startRadius = startValue.getRadius();
            float endRadius = endValue.getRadius();
            float curRadius = startRadius + fraction * (endRadius - startRadius);
            return new Circle(curRadius);
        }
    }
}
