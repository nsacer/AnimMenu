package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zpf on 2018/3/5.
 * 多空算法图
 * 0、短线电波
 */

public class StockLongAndShortChart extends View {

    //短线电波
    public static final String TYPE_SHORT_WAVE = "0";
    //大盘趋势
    public static final String TYPE_MARKET_TRENDS = "1";
    //强弱线
    public static final String TYPE_STRENGTH_LINE = "2";
    //资金动能、资金活跃度
    public static final String TYPE_FUNDING_RELATED = "3";

    //股票红色、绿色
    private final int COLOR_RED = Color.parseColor("#ff0000");
    private final int COLOR_GREEN = Color.parseColor("#22ac38");
    //资金动能、资金活跃度两条线
    private final int COLOR_FUNDING_RELATED_GRAY = Color.parseColor("#F07BA4");
    private final int COLOR_FUNDING_RELATED_BLUE = Color.parseColor("#E9E83B");
    //强弱线颜色
    private final int COLOR_LINE_STRONG = Color.parseColor("#CD1A70");
    private final int COLOR_LINE_WEEK = Color.parseColor("#019576");

    //一屏幕显示item的个数
    private final int NUM_SHOW = 30;

    //竖屏标题的大小
    private float mTitleSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
    //dp4
    private float dp4 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
    private float dp8 = dp4 * 2;
    //是否是竖屏
    private boolean isVerticalScreen = true;
    //控件的宽度、高度
    private int mWidth = 0, mHeight = 0;
    //用来存储图表数据集合map
    private HashMap<String, List<String>> mMapDatas = new HashMap<>();
    //图表数据集合、该数据对应的图表类型
    private List<String> mDatas = new ArrayList<>();
    private List<String> mDatas1 = new ArrayList<>();
    private String chartType;
    //绘制竖屏标题的paint
    private Paint mPaintText;
    //绘制图标的paint
    private Paint mPaintChart;
    //竖屏标题的绘制y坐标
    private float mYText = 0;
    //竖屏图表上横线y坐标/下横线y坐标
    private float mYLineTop = 0, mYLineBtm = 0, mYLineCenter = 0;
    private int colorLineTopBtm = Color.parseColor("#F5F5F5");
    private int colorLineCenter = Color.parseColor("#DADADA");
    //电波item高度/大盘趋势item的高度
    private float mHeightShortWave;
    //记录上次移动的x数据值
    private float preMoveX = 0;
    //记录可以横滑展示数据的最左边的数据在集合里边的index
    private int mIndexLeftFundingRelated = NUM_SHOW;
    //绘制的item的宽度
    private int mWidthFundingRelated = 0;


    public StockLongAndShortChart(Context context) {
        super(context);
        initPaint();
    }

    public StockLongAndShortChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public StockLongAndShortChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public StockLongAndShortChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint() {

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(1);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor(Color.BLACK);
        mPaintText.setTextSize(mTitleSize);

        mPaintChart = new Paint();
        mPaintChart.setAntiAlias(true);
        mPaintChart.setStyle(Paint.Style.STROKE);
        mPaintChart.setStrokeWidth(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (isVerticalScreen) {

            mYText = dp4 + (mTitleSize / 6 * 5);
            mYLineTop = mTitleSize + dp8;
            mYLineBtm = mHeight - dp4;
        } else {

            mYLineTop = dp4;
            mYLineBtm = mHeight - dp4;
        }
        mYLineCenter = (mYLineBtm - mYLineTop) / 2 + mYLineTop;
        mHeightShortWave = (mYLineCenter - mYLineTop) / 9 * 7;
        mWidthFundingRelated = (mWidth - (NUM_SHOW + 1)) / NUM_SHOW;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawChart(canvas);
    }

    //根据不同类型绘制图标
    private void drawChart(Canvas canvas) {

        if (mDatas == null || mDatas.isEmpty() || TextUtils.isEmpty(chartType)) return;
        switch (chartType) {

            case TYPE_SHORT_WAVE:
                drawShortWave(canvas);
                break;
            case TYPE_MARKET_TRENDS:
                drawMarketTrends(canvas);
                break;
            case TYPE_STRENGTH_LINE:
                drawStrengthLine(canvas);
                break;
            case TYPE_FUNDING_RELATED:
                drawFundingRelated(canvas, "资金动能");
                break;
        }
    }

    //绘制短线电波
    private void drawShortWave(Canvas canvas) {

        if (isVerticalScreen) {

            drawChartTitle(canvas, "短线电波");
            drawVerticalLine(canvas, true);
            drawShortWaveChart(canvas);
        } else {

        }
    }

    //绘制标题
    private void drawChartTitle(Canvas canvas, String title) {

        canvas.drawText(title, dp4, mYText, mPaintText);
    }

    /**
     * 绘制竖屏上下横线和中轴线
     *
     * @param isDrawCenterLine 是否绘制中轴线
     */
    private void drawVerticalLine(Canvas canvas, boolean isDrawCenterLine) {

        mPaintChart.setStrokeWidth(2);
        mPaintChart.setColor(colorLineTopBtm);
        canvas.drawLine(0, mYLineTop, mWidth, mYLineTop, mPaintChart);
        canvas.drawLine(0, mYLineBtm, mWidth, mYLineBtm, mPaintChart);
        if (isDrawCenterLine) {

            mPaintChart.setColor(colorLineCenter);
            canvas.drawLine(0, mYLineCenter, mWidth, mYLineCenter, mPaintChart);
        }
    }

    //绘制短线电波数据
    private void drawShortWaveChart(Canvas canvas) {

        int mCount = mDatas.size();
        int mWidthShortWave = mWidth / (mCount * 6 + 5);
        int mDividerWidthShortWave = mWidthShortWave * 5;
        for (int i = 0; i < mCount; i++) {

            String data = mDatas.get(i);
            if ("0".equals(data)) continue;
            float xStart = (i + 1) * mDividerWidthShortWave + i * mWidthShortWave;
            float xCenter = xStart + mWidthShortWave / 2;
            float yCenter = mYLineCenter;
            switch (data) {

                case "1":
                    yCenter = mYLineCenter - mHeightShortWave;
                    mPaintChart.setColor(COLOR_RED);
                    break;
                case "-1":
                    mPaintChart.setColor(COLOR_GREEN);
                    yCenter = mYLineCenter + mHeightShortWave;
                    break;
            }
            float xEnd = xStart + mWidthShortWave;
            Path mPathShortWave = new Path();
            mPathShortWave.moveTo(xStart, mYLineCenter);
            mPathShortWave.lineTo(xCenter, yCenter);
            mPathShortWave.lineTo(xEnd, mYLineCenter);
            canvas.drawPath(mPathShortWave, mPaintChart);
        }
    }

    //绘制大盘趋势图
    private void drawMarketTrends(Canvas canvas) {

        if (isVerticalScreen) {

            drawChartTitle(canvas, "大盘趋势");
            drawVerticalLine(canvas, true);
            drawMarketTrendsChart(canvas);

        } else {


        }
    }

    //绘制大盘趋势数据值
    private void drawMarketTrendsChart(Canvas canvas) {

        int count = mDatas.size();
        mPaintChart.setStrokeWidth(2);
        int mWidthItem = mWidth / count;
        for (int i = 0; i < count; i++) {

            String data = mDatas.get(i);
            Path path = new Path();
            float xStart = mWidthItem * i;
            float y = mYLineCenter;
            float xEnd = xStart + mWidthItem;

            switch (data) {

                case "1":
                    y = mYLineCenter - mHeightShortWave;
                    mPaintChart.setColor(COLOR_RED);
                    break;
                case "0":
                    mPaintChart.setColor(Color.TRANSPARENT);
                    break;
                case "-1":
                    y = mYLineCenter + mHeightShortWave;
                    mPaintChart.setColor(COLOR_GREEN);
                    break;
            }

            int posPre = i - 1;
            if (posPre >= 0 && !mDatas.get(posPre).equals(data)) {

                path.moveTo(xStart, mYLineCenter);
                path.lineTo(xStart, y);
                path.lineTo(xEnd, y);

            } else {

                path.moveTo(xStart, y);
                path.lineTo(xEnd, y);
            }

            if (i + 1 < count) {

                String dataNext = mDatas.get(i + 1);
                if (!dataNext.equals(data)) {

                    path.lineTo(xEnd, mYLineCenter);
                }
            }

            canvas.drawPath(path, mPaintChart);
        }

    }

    //绘制强弱线
    private void drawStrengthLine(Canvas canvas) {

        if (isVerticalScreen) {

            drawChartTitle(canvas, "强弱线");
            drawVerticalLine(canvas, false);
            drawStrengthLineChart(canvas);

        } else {


        }
    }

    //绘制强弱线数据值
    private void drawStrengthLineChart(Canvas canvas) {

        //获取最大值和最小值
        float max = 0, min = 0;
        int count = mDatas.size();
        for (int i = 0; i < count; i++) {

            float xCurr = Float.parseFloat(mDatas.get(i));
            float yCurr = Float.parseFloat(mDatas1.get(i));
            if (xCurr > max) max = xCurr;
            if (yCurr > max) max = yCurr;
            if (xCurr < min) min = xCurr;
            if (yCurr < min) min = yCurr;
        }
        float hItem = (mYLineBtm - mYLineTop) / (max - min);
        float wItem = mWidth / (count - 1);
        Path pathStrong = new Path();
        Path pathWeek = new Path();
        float preX = 0, preY = 0, preY1 = 0;
        for (int j = 0; j < count; j++) {

            float xCurr = Float.parseFloat(mDatas.get(j));
            float yCurr = Float.parseFloat(mDatas1.get(j));
            if (j == 0) {

                preX = 0;
                preY = mYLineCenter - hItem * xCurr;
                preY1 = mYLineCenter - hItem * yCurr;
                pathStrong.moveTo(preX, preY);
                pathWeek.moveTo(preX, preY1);
            } else {

                float x = wItem * j;
                float y = mYLineCenter - hItem * xCurr;
                float y1 = mYLineCenter - hItem * yCurr;
                float endX = (preX + x) / 2;
                float endY = (preY + y) / 2;
                float endY1 = (preY1 + y1) / 2;
                pathStrong.quadTo(preX, preY, endX, endY);
                pathWeek.quadTo(preX, preY1, endX, endY1);
                preX = x;
                preY = y;
                preY1 = y1;
            }
        }

        mPaintChart.setStrokeWidth(3);
        mPaintChart.setStyle(Paint.Style.STROKE);
        mPaintChart.setColor(COLOR_LINE_STRONG);
        canvas.drawPath(pathStrong, mPaintChart);

        mPaintChart.setColor(COLOR_LINE_WEEK);
        canvas.drawPath(pathWeek, mPaintChart);
    }

    /**
     * 绘制资金动能、资金活跃度图表
     *
     * @param title 图表的标题
     */
    private void drawFundingRelated(Canvas canvas, String title) {

        if (isVerticalScreen) {

            drawChartTitle(canvas, title);
            drawVerticalLine(canvas, true);
            drawFundingRelatedChart(canvas);
        } else {


        }
    }

    //绘制资金动能、资金活跃度图表数据
    private void drawFundingRelatedChart(Canvas canvas) {

        float wItem = (mWidth - (NUM_SHOW + 1)) / NUM_SHOW;
        mPaintChart.setStyle(Paint.Style.FILL);
        mPaintChart.setStrokeWidth(wItem);

        //遍历获取数据集合里边的绝对值最大值，计算数据分割的item高度
        int count = mDatas.size();
        if (count <= NUM_SHOW) mIndexLeftFundingRelated = count;
        List<String> showList = mDatas.subList(mIndexLeftFundingRelated - NUM_SHOW, mIndexLeftFundingRelated);
        int max = 0;
        int countShow = showList.size();
        for (int i = 0; i < countShow; i++) {

            int currentAbs = Math.abs(Integer.parseInt(showList.get(i)));
            if (currentAbs > max) max = currentAbs;
        }

        float hItem = (mYLineCenter - mYLineTop) / max;
        for (int j = 0; j < countShow; j++) {

            float x = (j + 1) + j * wItem + wItem / 2;
            int curr = Integer.parseInt(showList.get(NUM_SHOW - 1 - j));
            float y = mYLineCenter - curr * hItem;
            if (curr > 0) mPaintChart.setColor(COLOR_RED);
            else if (curr < 0) mPaintChart.setColor(COLOR_GREEN);
            else mPaintChart.setColor(Color.TRANSPARENT);
            canvas.drawLine(x, mYLineCenter, x, y, mPaintChart);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                preMoveX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                //TODO 根据横竖屏禁止滑动
                float x = event.getX();
                float xMove = x - preMoveX;
                int moveNum = (int) (Math.abs(xMove) / mWidthFundingRelated);
                if (moveNum > 0) {

                    if (xMove > 0) {

                        int index = mIndexLeftFundingRelated + moveNum;
                        mIndexLeftFundingRelated = index > (mDatas.size() - 1) ? (mDatas.size() - 1) : index;

                    } else if (xMove < 0) {

                        int index = mIndexLeftFundingRelated - moveNum;
                        mIndexLeftFundingRelated = index < NUM_SHOW ? NUM_SHOW : index;
                    }
                    postInvalidate();
                    preMoveX = x;
                }
                return true;
            case MotionEvent.ACTION_UP:

                return true;
        }

        return false;
    }

    /**
     * 设置数据和类型
     *
     * @param datas     数据集合
     * @param chartType 图表类型
     */
    public void setDatas(List<String> datas, String chartType) {

        if (datas != null && !datas.isEmpty() && !TextUtils.isEmpty(chartType)) {

            this.mDatas = datas;
            this.chartType = chartType;
            invalidate();
        }
    }

    //追加数据
    public void appendDatas(List<String> datas, String chartType) {

        if (datas != null && !datas.isEmpty() && !TextUtils.isEmpty(chartType)) {

            this.mDatas.addAll(datas);
            this.chartType = chartType;
            invalidate();
        }
    }

    /**
     * 设置强弱线数据
     *
     * @param xList 强线数据
     * @param yList 弱线数据
     */
    public void setStrengthLineDatas(String[] xList, String[] yList) {

        if (xList != null && yList != null) {

            this.mDatas = Arrays.asList(xList);
            this.mDatas1 = Arrays.asList(yList);
            this.chartType = TYPE_STRENGTH_LINE;
            invalidate();
        }
    }

    //追加强弱线数据
    public void appendStrengthLineDatas(String[] xList, String[] yList) {

        if (xList != null && yList != null) {

            this.mDatas.addAll(Arrays.asList(xList));
            this.mDatas1.addAll(Arrays.asList(yList));
            this.chartType = TYPE_STRENGTH_LINE;
            invalidate();
        }
    }
}
