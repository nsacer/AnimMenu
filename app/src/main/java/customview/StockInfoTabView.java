package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.zpf.animmenu.R;

import java.util.List;

/**
 * Created by zpf on 2018/3/1.
 * 股票行情：日K、分时、五日...tab展示切换View
 */

public class StockInfoTabView extends View {

    //标题
    private String[] mTitles = new String[]{"分时", "五日", "日k", "周k", "月k"};
    //tab边距、每个tab间隔
    private final float DEFAULT_TAB_DIVIDER = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            4, getResources().getDisplayMetrics());
    //tabBar宽度、高度
    private int tabBarWidth = 0;
    private int tabBarHeight = 0;
    private onTabChangeListener mOnTabChangeListener;
    //tabBar背景颜色
    private int mColorTabBG = Color.parseColor("#f0f0f0");
    //tab默认颜色、tab选中颜色
    private int mColorTab = Color.TRANSPARENT;
    private int mColorTabSelected = Color.parseColor("#ff4140");
    //tab文本默认、选中状态颜色
    private int mColorTabText = Color.parseColor("#666666");
    private int mColorTabTextSelected = Color.WHITE;
    //tab标题字体大小
    private float mTabTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
    //绘制背景、tab颜色画笔
    private Paint mPaintBG;
    private Paint mPaintText;
    //tab选中的index
    private int mTabSelectedIndex = 0;
    //tabBar的半径/内部tab的半径
    private float mTabBarR, mTabR;
    //tab的宽度
    private float mTabWidth;

    public StockInfoTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDefineAttrs(context, attrs);
        initPaint();
    }

    public StockInfoTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefineAttrs(context, attrs);
        initPaint();
    }

    public StockInfoTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initDefineAttrs(context, attrs);
        initPaint();
    }

    //获取自定义的属性值
    private void initDefineAttrs(Context context, AttributeSet attributeSet) {

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.StockInfoTabView);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {

            int attr = typedArray.getIndex(i);
            switch (attr) {

                case R.styleable.StockInfoTabView_tabBarBackground:
                    mColorTabBG = typedArray.getColor(attr, Color.parseColor("#f0f0f0"));
                    break;
                case R.styleable.StockInfoTabView_tabColor:
                    mColorTab = typedArray.getColor(attr, Color.TRANSPARENT);
                    break;
                case R.styleable.StockInfoTabView_tabSelectedColor:
                    mColorTabSelected = typedArray.getColor(attr, Color.parseColor("#ff4140"));
                    break;
                case R.styleable.StockInfoTabView_tabTextSize:
                    mTabTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                            typedArray.getDimensionPixelSize(attr, 14), displayMetrics);
                    break;
                case R.styleable.StockInfoTabView_tabTextColor:
                    mColorTabText = typedArray.getColor(attr, Color.parseColor("#666666"));
                    break;
                case R.styleable.StockInfoTabView_tabSelectedTextColor:
                    mColorTabTextSelected = typedArray.getColor(attr, Color.WHITE);
                    break;
            }
        }
        typedArray.recycle();
    }

    //初始化画笔
    private void initPaint() {

        mPaintBG = new Paint();
        mPaintBG.setAntiAlias(true);
        mPaintBG.setStyle(Paint.Style.FILL);
        mPaintBG.setStrokeCap(Paint.Cap.ROUND);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setStrokeWidth(1);
        mPaintText.setTextSize(mTabTextSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        tabBarWidth = w;
        tabBarHeight = h;
        mTabBarR = tabBarHeight / 2;
        mTabR = tabBarHeight / 2 - DEFAULT_TAB_DIVIDER;
        mTabWidth = (tabBarWidth - DEFAULT_TAB_DIVIDER * (mTitles.length + 1)) / mTitles.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTabBarBG(canvas);
        drawTabBGAndText(canvas);
    }

    //绘制背景
    private void drawTabBarBG(Canvas mCanvas) {

        mPaintBG.setStrokeWidth(tabBarHeight);
        mPaintBG.setColor(mColorTabBG);
        mCanvas.drawLine(mTabBarR, mTabBarR, tabBarWidth - mTabBarR, mTabBarR, mPaintBG);
    }

    //绘制tab背景
    private void drawTabBGAndText(Canvas mCanvas) {

        mPaintBG.setStrokeWidth(tabBarHeight - DEFAULT_TAB_DIVIDER * 2);

        int count = mTitles.length;
        for (int i = 0; i < count; i++) {

            boolean isSelected = mTabSelectedIndex == i;
            mPaintBG.setColor(isSelected ? mColorTabSelected : mColorTab);
            mPaintText.setColor(isSelected ? mColorTabTextSelected : mColorTabText);

            float startX = DEFAULT_TAB_DIVIDER * (i + 1) + i * mTabWidth + mTabR;
            float endX = (DEFAULT_TAB_DIVIDER + mTabWidth) * (i + 1) - mTabR;
            mCanvas.drawLine(startX, mTabBarR, endX, mTabBarR, mPaintBG);

            float textW = mPaintText.measureText(mTitles[i]);
            float textStartX = DEFAULT_TAB_DIVIDER * (i + 1) + i * mTabWidth + mTabWidth / 2 - textW / 2;
            mCanvas.drawText(mTitles[i], textStartX, mTabBarR + (mTabTextSize / 3), mPaintText);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                float x = event.getX();
                for (int i = 0; i < mTitles.length; i++) {

                    float startX = DEFAULT_TAB_DIVIDER * (i + 1) + i * mTabWidth;
                    float endX = (DEFAULT_TAB_DIVIDER + mTabWidth) * (i + 1);
                    if (startX <= x && x <= endX) {
                        mTabSelectedIndex = i;
                        if (mOnTabChangeListener != null)
                            mOnTabChangeListener.OnTabChanged(mTabSelectedIndex);
                        invalidate();
                    }
                }
                return true;
        }
        return true;
    }

    //tab点击切换监听接口
    public interface onTabChangeListener {

        void OnTabChanged(int index);
    }

    public void setOnTabChangeListener(onTabChangeListener listener) {

        if (listener != null) this.mOnTabChangeListener = listener;
    }

    //设置标题集合
    public void setmTitles(String[] titles) {

        this.mTitles = titles;
        invalidate();
    }

    public void setmTitles(List<String> titles) {

        int size = titles.size();
        String[] newTitles = new String[size];
        for (int i = 0; i < size; i++) {

            newTitles[i] = titles.get(i);
            this.mTitles = newTitles;
        }
        invalidate();
    }
}
