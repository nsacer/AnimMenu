package customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.zpf.animmenu.R;

/**
 * Created by zpf on 2016/8/1.
 * 波浪降低view
 */
public class WaveView extends View {

    private Paint paint;
    private Path path;
    private int waveLength = 600;
    private int waveF = 100;
    private int dx = 0;//横向波动值
    private int dy = 0;//纵向波动值

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();
    }

    public WaveView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.reset();

        int pivotY = 600;

        int halfWaveLength = waveLength / 2;

        path.moveTo(-waveLength + dx, pivotY + dy);

        dy += 6;

        for (int i = -waveLength; i <= getWidth() + waveLength; i += waveLength) {

            path.rQuadTo(halfWaveLength / 2, -waveF, halfWaveLength, 0);
            path.rQuadTo(halfWaveLength / 2, waveF, halfWaveLength, 0);
        }

        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();

        canvas.drawPath(path, paint);
    }

    /**
     * 启动动画（波动）
     */
    public void startAnim() {

        ValueAnimator animator = ValueAnimator.ofInt(0, waveLength);
        animator.setDuration(1200);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
