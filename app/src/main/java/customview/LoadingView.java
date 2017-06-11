package customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.zpf.animmenu.R;

/**
 * Created by zpf on 2017/2/3.
 */

public class LoadingView extends View {

    private static final String COLOR_ARROW = "#E94B4B";
    private static final String COLOR_TEXT = "#A5A5A5";
    private static final int TEXT_SIZE = 14;
    private final float DIVIDER_HEIGHT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            12, getResources().getDisplayMetrics());
    private static final long ANIMATOR_TIME = 1200;
    private Bitmap bitmapGu;
    private Bitmap bitmapTransparent;
    private Paint paintRed;
    private int defaultWidth;
    private int defaultHeight;
    private int xCurrent = 0;
    private ValueAnimator animator;

    /**
     * "加载中..."文字大小size
     */
    private String loadingText = "加载中...";
    private float textHeight = 0;
    private float loadingDividerHeight = DIVIDER_HEIGHT;
    private Paint paintTxt;

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefineAttr(context, attrs);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefineAttr(context, attrs);
        init(context);
    }

    /**
     * 获取自定义属性的信息
     */
    private void initDefineAttr(Context context, AttributeSet attrs) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);

        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {

            int attr = typedArray.getIndex(i);
            switch (attr) {

                case R.styleable.LoadingView_android_text:

                    loadingText = typedArray.getString(attr);
                    break;

                case R.styleable.LoadingView_android_dividerHeight:

                    loadingDividerHeight = typedArray.getDimension(attr, DIVIDER_HEIGHT);
                    break;
            }
        }

        typedArray.recycle();
    }

    private void init(Context context) {

        bitmapGu = BitmapFactory.decodeResource(context.getResources(), R.mipmap.load_gu);
        bitmapTransparent = BitmapFactory.decodeResource(context.getResources(), R.mipmap.load_arrow)
                .extractAlpha();

        defaultWidth = bitmapGu.getWidth();
        defaultHeight = bitmapGu.getHeight();

        paintRed = new Paint();
        paintRed.setAntiAlias(true);
        //箭头的颜色
        paintRed.setColor(Color.parseColor(COLOR_ARROW));

        paintTxt = new Paint();
        paintTxt.setAntiAlias(true);
        paintTxt.setColor(Color.parseColor(COLOR_TEXT));
        paintTxt.setStyle(Paint.Style.FILL);
        paintTxt.setTextAlign(Paint.Align.CENTER);
        paintTxt.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE,
                getResources().getDisplayMetrics()));

        textHeight = measureTextHeight(paintTxt, loadingText);

        animator = ValueAnimator.ofInt(0, bitmapTransparent.getWidth());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                xCurrent = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(ANIMATOR_TIME);

    }

    /**
     * 测量文本的高度
     */
    private int measureTextHeight(Paint paint, String string) {

        Rect rect = new Rect(0, 0, 0, 0);
        paint.getTextBounds(string, 0, string.length(), rect);

        return rect.height();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getViewSize(defaultWidth, widthMeasureSpec);
        int height = getViewSize(defaultHeight, heightMeasureSpec);

        setMeasuredDimension(width, (int) (height + loadingDividerHeight + textHeight));
    }

    private int getViewSize(int defaultSize, int measureSpec) {

        int mySize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        mySize = (mode == MeasureSpec.EXACTLY) ? size : defaultSize;

        return mySize;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawProgress(canvas);
        super.onDraw(canvas);

    }

    private void drawProgress(Canvas canvas) {

        canvas.drawBitmap(bitmapGu, 0, 0, null);

        canvas.drawText(loadingText, defaultWidth/2, defaultHeight+loadingDividerHeight+textHeight/3,
                paintTxt);

        canvas.clipRect(0, 0, xCurrent, bitmapTransparent.getHeight());

        canvas.drawBitmap(bitmapTransparent, 0, 0, paintRed);

    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            if (animator.isRunning())
                return;
            animator.start();
        } else {
            animator.end();
        }
    }
}
