package customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.example.zpf.animmenu.R;

/**
 * Created by zpf on 2016/11/9.
 * 圆形路径旋转文字
 */
public class CircleTextView extends TextView {


    /**
     * 默认文字透明度
     */
    private final int DEFAULT_ALPHA = 120;

    /**
     * 默认的旋转一周的时间
     */
    private final int DEFAULT_ROTATION_TIME = 4000;
    /**
     * 文字旋转中心点坐标
     */
    private float centerX, centerY;

    /**
     * 根据控件长宽测量的半径
     */
    private float radius;

    /**
     * 文字画笔
     */
    private TextPaint paint;

    /**
     * text alpha
     */
    private int alpha = DEFAULT_ALPHA;

    /**
     * 是否顺时针旋转（默认逆时针）
     */
    private boolean rotationCM = false;

    /**
     * 旋转一圈的时间
     */
    private int rotationTime = DEFAULT_ROTATION_TIME;

    private ObjectAnimator animator;

    public CircleTextView(Context context) {
        super(context);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initDefineAttr(context, attrs);
        initPaint();
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initDefineAttr(context, attrs);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        radius = Math.min(w, h) / 2 - getTextSize();
        centerX = w / 2;
        centerY = h / 2;

        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void initDefineAttr(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView);
        int count = array.getIndexCount();

        for (int i = 0; i < count; i++) {

            int attr = array.getIndex(i);

            switch (attr) {

                case R.styleable.CircleTextView_circleTextAlpha:

                    alpha = array.getInt(attr, DEFAULT_ALPHA);
                    if (alpha < 0 || alpha > 255)
                        alpha = DEFAULT_ALPHA;
                    break;

                case R.styleable.CircleTextView_circleRotationCW:

                    rotationCM = array.getBoolean(attr, false);
                    break;

                case R.styleable.CircleTextView_circleRotationTime:

                    rotationTime = array.getInt(attr, DEFAULT_ROTATION_TIME);
                    break;

                default:
                    break;
            }
        }

        array.recycle();
    }

    /**
     * init Paint
     */
    private void initPaint() {

        paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setColor(getCurrentTextColor());
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(getTextSize());
        paint.setAlpha(alpha);

        //文本渐变
//        float textWidth = paint.measureText(getText().toString());
//        Shader shader = new LinearGradient(0, 0, textWidth, 0, Color.TRANSPARENT,
//                getCurrentTextColor(), Shader.TileMode.CLAMP);
//        paint.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircleText(canvas);
    }

    /**
     * draw circle text
     */
    private void drawCircleText(Canvas canvas) {

        Path path = new Path();
        path.addCircle(centerX, centerY, radius, Path.Direction.CW);
        canvas.drawTextOnPath(getText().toString(), path, 0, 0, paint);
    }

    public void doAnimation() {

        if (rotationCM)
            animator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);
        else
            animator = ObjectAnimator.ofFloat(this, "rotation", 360f, 0f);

        animator.setDuration(rotationTime);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public void cancelAnimation() {

        animator.cancel();
    }
}
