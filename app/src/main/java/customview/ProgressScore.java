package customview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.zpf.animmenu.R;


/**
 * Created by zpf on 2017/7/25.
 * 积分页面的打败..%用户进度条
 */

public class ProgressScore extends View {

    /**
     * 默认的进度条的高度
     */
    private final float DEFAULT_PROGRESS_HEIGHT = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
    /**
     * 默认的进度条距离控件左边右边的margin
     */
    private final float DEFAULT_PROGRESS_MARGIN_START_END = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
    /**
     * 用户头像距离背景指示器的边距
     */
    private final float DEFAULT_HEAD_MARGIN = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

    /**
     * 默认的进度条的最大数据值为100
     */
    private final int DEFAULT_PROGRESS_MAX_VALUE = 100;

    /**
     * 进度条背景色
     */
    private int colorProgressBack = ContextCompat.getColor(getContext(), R.color.white_alpha_2);

    /**
     * 进度条前景色
     */
    private int colorProgressFore = ContextCompat.getColor(getContext(), R.color.colorWhite);

    /**
     * 进度条的高度
     */
    private float progressHeight = DEFAULT_PROGRESS_HEIGHT;

    /**
     * 进度条开始、结束横坐标/纵坐标
     */
    private float progressStartX = DEFAULT_PROGRESS_MARGIN_START_END;
    private float progressY;
    private float progressEndX;

    /**
     * 进度条最大数据值
     */
    private int progressMaxValue = DEFAULT_PROGRESS_MAX_VALUE;

    /**
     * 每份数据值对应的控件占用的宽度
     */
    private float progressSingleWidth = 0;

    /**
     * 进度条Paint
     * 背景色、前景色使用同一个
     */
    private Paint paintProgress;

    /**
     * 进度条数据值
     */
    private float progress = 0f;
    private float progressCurrent = DEFAULT_PROGRESS_MARGIN_START_END;

    /**
     * 指示器背景图
     */
    private Bitmap bitmapIndicatorBg = BitmapFactory.decodeResource(getResources(),
            R.mipmap.score_indicator_bg);
    private Bitmap bitmapIndicatorHead;
    private int widthBg;
    private int heightBg;
    private int leftBg = 0, topBg = 0, rightBg = 0, bottomBg = 0;
    private int leftHead = 0, topHead = 0, rightHead = 0, bottomHead = 0;
    private Context mContext;


    public ProgressScore(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initDefineAttrs(context, attrs);
        initPaint();
        confirmBitmapBgWidthHeight();
    }

    public ProgressScore(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initDefineAttrs(context, attrs);
        initPaint();
        confirmBitmapBgWidthHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        progressStartX = progressStartX + progressHeight / 2;
        progressEndX = w - DEFAULT_PROGRESS_MARGIN_START_END - progressHeight / 2;
        progressY = (float) (h - (progressHeight / 2.0));
        progressSingleWidth = (progressEndX - progressStartX) / progressMaxValue;

        bottomBg = (int) (progressY - progressHeight);
        topBg = bottomBg - heightBg;
        topHead = (int) (topBg + DEFAULT_HEAD_MARGIN);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawProgressBar(canvas);

        leftBg = (int) (progressCurrent - widthBg / 2);
        rightBg = leftBg + widthBg;
        leftHead = (int) (leftBg + DEFAULT_HEAD_MARGIN);
        rightHead = (int) (rightBg - DEFAULT_HEAD_MARGIN);
        bottomHead = (int) (topHead + (rightBg - leftBg - DEFAULT_HEAD_MARGIN * 2));

        drawIndicator(canvas);
    }

    /**
     * 初始化设置画笔
     */
    private void initPaint() {

        paintProgress = new Paint();
        paintProgress.setAntiAlias(true);
        paintProgress.setColor(colorProgressBack);
        paintProgress.setStrokeCap(Paint.Cap.ROUND);
        paintProgress.setStyle(Paint.Style.FILL);
        paintProgress.setStrokeWidth(progressHeight);
    }

    /**
     * 初始化读取xml自定义属性
     */
    private void initDefineAttrs(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressScore);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {

            int attr = array.getIndex(i);
            switch (attr) {

                case R.styleable.ProgressScore_progressHeight:

                    progressHeight = array.getDimension(attr, DEFAULT_PROGRESS_HEIGHT);
                    break;

                case R.styleable.ProgressScore_progressColorFore:

                    colorProgressFore = array.getColor(attr, ContextCompat.getColor(context, R.color.colorWhite));
                    break;

                case R.styleable.ProgressScore_progressColorBack:

                    colorProgressBack = array.getColor(attr, ContextCompat.getColor(context, R.color.white_alpha_2));
                    break;

                case R.styleable.ProgressScore_progressMaxValue:

                    progressMaxValue = array.getInt(attr, DEFAULT_PROGRESS_MAX_VALUE);
                    break;
            }
        }

        array.recycle();
    }

    /**
     * 绘制积分数据条
     */
    private void drawProgressBar(Canvas canvas) {

        paintProgress.setColor(colorProgressBack);
        canvas.drawLine(progressStartX, progressY, progressEndX, progressY, paintProgress);

        paintProgress.setColor(colorProgressFore);
        if (progressCurrent != DEFAULT_PROGRESS_MARGIN_START_END)
            canvas.drawLine(progressStartX, progressY, progressCurrent, progressY, paintProgress);
    }

    /**
     * 绘制进度条指示器
     */
    private void drawIndicator(Canvas canvas) {

        if (bitmapIndicatorHead != null) {

            Rect rectDes = new Rect(leftBg, topBg, rightBg, bottomBg);
            canvas.drawBitmap(bitmapIndicatorBg, null, rectDes, paintProgress);

            Rect rectDesHead = new Rect(leftHead, topHead, rightHead, bottomHead);
            canvas.drawBitmap(bitmapIndicatorHead, null, rectDesHead, paintProgress);
        }

    }

    public void doAnimation(float startX) {

        doAnimation(startX, 2000, 1600);
    }

    /**
     * 数据为正数的时候动画
     */
    private void doAnimation(float startX, long delay, long duration) {

        float endX = startX + progressSingleWidth * progress;
        ValueAnimator animator = ValueAnimator.ofObject(new PositiveEvaluator(), startX, endX);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                progressCurrent = (float) valueAnimator.getAnimatedValue();

                invalidate();

            }
        });

        animator.setStartDelay(delay);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    /**
     * 插值器
     */
    private class PositiveEvaluator implements TypeEvaluator<Float> {

        @Override
        public Float evaluate(float v, Float startValue, Float endValue) {

            return startValue + v * (endValue - startValue);
        }
    }

    public void setProgress(int progress) {

        if (progress < 0) {

            this.progress = 0;
        } else if (progress > DEFAULT_PROGRESS_MAX_VALUE) {

            this.progress = DEFAULT_PROGRESS_MAX_VALUE;
        } else {
            this.progress = progress;
        }
    }

    public void setProgressAndStartAnim(int progress) {

        if (progress < 0) {

            this.progress = 0;
            Toast.makeText(mContext, "数值过小", Toast.LENGTH_SHORT).show();
        } else if (progress > progressMaxValue) {

            this.progress = progressMaxValue;
            Toast.makeText(mContext, "数值过大", Toast.LENGTH_SHORT).show();
        } else {

            this.progress = progress;
        }

        doAnimation(progressStartX, 200, 800);
    }

    public void startAnim() {

        doAnimation(progressStartX);
    }

    private void confirmBitmapBgWidthHeight() {

        if (bitmapIndicatorBg != null) {

            widthBg = bitmapIndicatorBg.getWidth();
            heightBg = bitmapIndicatorBg.getHeight();
        }
    }

    /**
     * 通过本地图片资源id设置
     */
    public void setBitmapIndicatorHead(int resId) {

        //toRoundBitmap()是将图片裁剪成圆形方法
        this.bitmapIndicatorHead = toRoundBitmap(BitmapFactory.decodeResource(getResources(), resId));
    }

    /**
     * 直接传入bitmap
     */
    public void setBitmapIndicatorHead(Bitmap bitmapIndicatorHead) {
        this.bitmapIndicatorHead = toRoundBitmap(bitmapIndicatorHead);
    }

    /**
     * 将传入的bitmap裁剪成圆形
     */
    private Bitmap toRoundBitmap(Bitmap bitmap) {

        if (bitmap == null)
            return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
}
