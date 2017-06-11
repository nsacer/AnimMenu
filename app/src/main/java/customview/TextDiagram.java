package customview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.example.zpf.animmenu.R;


/**
 * Created by zpf on 2016/11/7.
 * 横向柱状图
 */
public class TextDiagram extends View {

    /**
     * 默认的股票名称文字大小
     */
    private final float DEFAULT_STOCK_TEXT_SIZE = 16;

    /**
     * 默认的股票数据值文字大小
     */
    private final float DEFAULT_VALUE_TEXT_SIZE = 10;

    /**
     * 默认的股票名字距离右边图形的边距
     */
    private final float DEFAULT_TEXT_MARGIN_RIGHT = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

    /**
     * 默认的数据条的宽度
     */
    private final float DEFAULT_LINE_WIDTH = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());

    /**
     * 默认的蚂蚁线的线条长度
     */
    private final float DEFAULT_DASH_LINE_LENGTH = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());

    /**
     * 默认的蚂蚁线的间隔
     */
    private final float DEFAULT_DASH_LINE_PADDING = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

    /**
     * 默认的蚂蚁线的宽度
     */
    private final float DEFAULT_DASH_LINE_WIDTH = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());

    /**
     * 顶部的文本
     */
    private String sTop = "";

    /**
     * 底部的文本
     */
    private String sBottom = "";

    /**
     * 一句话描述（xxx是xxx的多少倍）
     */
    private String sContent = "";

    /**
     * 底部文字距离第二个股票距离
     * */
    private float contentPaddingTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            8, getResources().getDisplayMetrics());

    /**
     * 控件高度的1/3
     */
    private float singleY = 0;

    /**
     * 最长标题的长度（也就是股票名字的最右边）
     */
    private float maxTextLength = 0;

    /**
     * 两个股票数值都为正数的时候起始坐标x
     */
    private float posStartX = 0;

    /**
     * 两个股票数值都为负数时候的终点坐标x
     */
    private float width = 0;

    /**
     * 可以绘制长条的长度
     */
    private float lineTotalWidth = 0;

    /**
     * 两个股票一正一负时候的中心坐标点
     */
    private float douCenterX = 0;

    /**
     * 股票名字的textSize, textColor
     */
    private float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            DEFAULT_STOCK_TEXT_SIZE, getResources().getDisplayMetrics());
    private int textColor = ContextCompat.getColor(getContext(), R.color.diagram_title);

    /**
     * 股票数据值的textSize
     */
    private float textValueSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            DEFAULT_VALUE_TEXT_SIZE, getResources().getDisplayMetrics());

    /**
     * 数据值的Paint
     */
    private Paint paintValue;

    /**
     * 最下边一句话的paint
     */
    private Paint paintContent;

    /**
     * 股票名字距离右边图形的距离
     */
    private float textMarginRight = DEFAULT_TEXT_MARGIN_RIGHT;

    /**
     * 股票名字的画笔
     */
    private Paint paintStockName;

    /**
     * 数据值对应的长条的Paint（在绘制上下两条线的时候更改画笔颜色）
     */
    private Paint paintLine;

    /**
     * 数据值对应的长条的Paint（在绘制上下两条线的时候更改画笔颜色）
     */
    private Paint paintLinePath;

    /**
     * 数据值线条的宽度
     */
    private float lineWidth = DEFAULT_LINE_WIDTH;

    /**
     * 数据值线条的Path
     */
    private Path pathLine;

    /**
     * 顶部数据颜色
     */
    private int topColor = ContextCompat.getColor(getContext(), R.color.diagram_blue);

    /**
     * 底部数据颜色
     */
    private int bottomColor = ContextCompat.getColor(getContext(), R.color.diagram_red);

    /**
     * 用来接收传入的顶部数值
     */
    private float fTop;
    private float fBtm;

    /**
     * 用来存储需要绘制的数据值
     */
    private String sTopValue = "";
    private String sBtmValue = "";

    /**
     * 用来绘制的top数据的startX / endX / 动画中间值
     */
    private float topStartX, topEndX, topPosEndX, topNegStartX;

    /**
     * 判断顶部数据是否是正值
     */
    private boolean topIsPos = Boolean.TRUE;

    /**
     * 用来绘制的bottom数据的startX 、 endX 、动画中间值
     */
    private float btmStartX, btmEndX, btmPosEndX, btmNegStartX;

    /**
     * 判断底部数据是否是正值
     */
    private boolean btmIsPos = Boolean.TRUE;

    /**
     * 顶部数据值绘制所在的x坐标点
     */
    private float topValueX;

    /**
     * 底部数据值绘制所在的x坐标点
     */
    private float btmValueX;

    /**
     * 蚂蚁线所在的x点坐标
     */
    private float dashLineX;

    /**
     * 蚂蚁线
     */
    private Paint paintDashLine;

    /**
     * 蚂蚁线颜色
     */
    private int dashLineColor = Color.GRAY;

    /**
     * 蚂蚁线线长度
     */
    private float dashLineLength = DEFAULT_DASH_LINE_LENGTH;

    /**
     * 蚂蚁线之间间隔
     */
    private float dashLinePadding = DEFAULT_DASH_LINE_PADDING;

    /**
     * 蚂蚁线的宽度
     */
    private float dashLineWidth = DEFAULT_DASH_LINE_WIDTH;

    /**
     * 蚂蚁线是否可见
     * */
    private boolean dashLineVisible = Boolean.TRUE;

    public TextDiagram(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefineAttrs(context, attrs);
        initPaint();
    }

    public TextDiagram(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefineAttrs(context, attrs);
        initPaint();
    }

    /**
     * set stock name
     */
    public void setStockName(String stockTop, String stockBtm) {

        if (TextUtils.isEmpty(stockTop) || TextUtils.isEmpty(stockBtm))
            return;

        this.sTop = stockTop;
        this.sBottom = stockBtm;

    }

    /**
     * set stock value
     * @param valueTop 顶部数据值
     * @param valueBtm 底部数据值
     * @param dashLineVisible 蚂蚁线是否可见（主力深度为false）
     */
    public void setStockValue(String valueTop, String valueBtm, boolean dashLineVisible) {

        if (TextUtils.isEmpty(sTop) || TextUtils.isEmpty(sBottom))
            throw new IllegalArgumentException("必须先调用setStockName()设置股票名称！");

        if(TextUtils.isEmpty(valueTop) || TextUtils.isEmpty(valueBtm))
            return;

        this.fTop = Float.valueOf(valueTop);
        this.fBtm = Float.valueOf(valueBtm);

        sTopValue = valueTop;
        sBtmValue = valueBtm;

        this.dashLineVisible = dashLineVisible;

    }

    public void setContent(String content) {

        if (TextUtils.isEmpty(content))
            return;
        this.sContent = content;
        invalidate();
    }

    /**
     * 设置自定义属性
     */
    private void initDefineAttrs(Context context, AttributeSet attrs) {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextDiagram);
        int count = array.getIndexCount();

        for (int i = 0; i < count; i++) {

            int attr = array.getIndex(i);
            switch (attr) {

                case R.styleable.TextDiagram_textSize:

                    textSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, attr, metrics));
                    break;

                case R.styleable.TextDiagram_textColor:

                    textColor = array.getColor(attr,
                            ContextCompat.getColor(getContext(), R.color.diagram_title));
                    break;

                case R.styleable.TextDiagram_textMarginRight:

                    textMarginRight = array.getDimension(attr, DEFAULT_TEXT_MARGIN_RIGHT);
                    break;

                case R.styleable.TextDiagram_textValueSize:

                    textValueSize = array.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, attr, metrics));
                    break;

                case R.styleable.TextDiagram_topColor:

                    topColor = array.getColor(attr,
                            ContextCompat.getColor(getContext(), R.color.diagram_blue));
                    break;

                case R.styleable.TextDiagram_bottomColor:

                    bottomColor = array.getColor(attr,
                            ContextCompat.getColor(getContext(), R.color.diagram_red));
                    break;

                case R.styleable.TextDiagram_lineWidth:

                    lineWidth = array.getDimension(attr, DEFAULT_LINE_WIDTH);
                    break;

                case R.styleable.TextDiagram_dashLineColor:

                    dashLineColor = array.getColor(attr, Color.GRAY);
                    break;

                case R.styleable.TextDiagram_dashLineLength:

                    dashLineLength = array.getDimension(attr, DEFAULT_DASH_LINE_LENGTH);
                    break;

                case R.styleable.TextDiagram_dashLinePadding:

                    dashLinePadding = array.getDimension(attr, DEFAULT_DASH_LINE_PADDING);
                    break;

                case R.styleable.TextDiagram_dashLineWidth:

                    dashLineWidth = array.getDimension(attr, DEFAULT_DASH_LINE_WIDTH);
                    break;

                case R.styleable.TextDiagram_mainDeepTop:

                    contentPaddingTop = array.getDimension(attr, DEFAULT_TEXT_MARGIN_RIGHT);
                    break;

                default:
                    break;
            }
        }

        array.recycle();

    }

    /**
     * 初始化Paint
     */
    private void initPaint() {

        paintStockName = new Paint();
        paintStockName.setAntiAlias(true);
        paintStockName.setColor(textColor);
        paintStockName.setStyle(Paint.Style.FILL);
        paintStockName.setTextSize(textSize);
        paintStockName.setTextAlign(Paint.Align.RIGHT);

        paintLine = new Paint();
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paintLine.setAntiAlias(true);
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
        paintLine.setStrokeWidth(lineWidth);

        pathLine = new Path();

        paintLinePath = new Paint();
        paintLinePath.setAntiAlias(true);
        paintLinePath.setStyle(Paint.Style.FILL);
        paintLinePath.setStrokeWidth(lineWidth);

        /**
         * 设置发光效果BlurMaskFilter(float radius, Blur blur)
         * @param radius 弥散半径
         * @param blur 发光效果类型
         * */
//        paintLine.setMaskFilter(new BlurMaskFilter(6, BlurMaskFilter.Blur.INNER));
//        paintLine.setMaskFilter(new EmbossMaskFilter());

        /** 设置阴影
         * @param radius 半径
         * @param offX x方向偏移量
         * @param offY y方向偏移量
         * @param color 阴影颜色
         * */
//        paintLine.setShadowLayer(2, 0, 0, Color.GRAY);


        paintDashLine = new Paint();
        paintDashLine.setAntiAlias(true);
        paintDashLine.setColor(dashLineColor);
        paintDashLine.setStyle(Paint.Style.STROKE);
        paintDashLine.setStrokeWidth(dashLineWidth);

        //Phase偏移量，可以使线动起来（配合invalidate()刷新）
        DashPathEffect effect = new DashPathEffect(new float[]{dashLineLength, dashLinePadding}, 0);
        paintDashLine.setPathEffect(effect);

        paintValue = new Paint();
        paintValue.setAntiAlias(true);
        paintValue.setTextSize(textValueSize);
        paintValue.setTextAlign(Paint.Align.CENTER);

        paintContent = new Paint();
        paintContent.setAntiAlias(true);
        paintContent.setTextSize(textValueSize);
        paintContent.setTextAlign(Paint.Align.LEFT);
        paintContent.setColor(topColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawText(canvas, sTop, true);

        drawText(canvas, sBottom, false);

        if(dashLineVisible) {

            drawTopLinePath(canvas, topIsPos, topStartX, topEndX);

            drawBottomLinePath(canvas, btmIsPos, btmStartX, btmEndX);

            drawDashLine(canvas, dashLineX);
        } else {

            drawTopLine(canvas, topIsPos, topStartX, topEndX);

            drawBottomLine(canvas, btmIsPos, btmStartX, btmEndX);
        }

        drawValueText(canvas, sTopValue, sBtmValue, topValueX, btmValueX);

        drawContent(canvas, sContent);

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);

        singleY = h / 3;
        width = w - (lineWidth / 2 + dashLineWidth);

        confirmXY();

        doAnimation();

    }


    /**
     * 测量两个股票名字的长度
     *
     * @param string 文本
     */
    private float measureText(String string) {

        return measureText(paintStockName, string);
    }

    /**
     * 测量两个股票名字的长度
     *
     * @param paint  文本的画笔
     * @param string 文本
     */
    private float measureText(Paint paint, String string) {

        float f;

        if (TextUtils.isEmpty(string))
            f = 0;
        else
            f = paint.measureText(string);
        return f;
    }

    /**
     * 计算填充数据之后绘制的坐标点
     */
    private void confirmXY() {

        if (TextUtils.isEmpty(sTop))
            return;

        confirmStartAndCenterX(sTop, sBottom);

        confirmDrawPointInfo(fTop, fBtm);
    }


    /**
     * 确定条形图的左边起点，中间点的位置x坐标
     */
    private void confirmStartAndCenterX(String strTop, String strBtm) {

        if (TextUtils.isEmpty(strTop) || TextUtils.isEmpty(strBtm))
            return;

        float fTop = measureText(strTop);
        float fBtm = measureText(strBtm);
        maxTextLength = Math.max(fTop, fBtm);

        posStartX = maxTextLength + textMarginRight + lineWidth / 2;
        lineTotalWidth = width - posStartX;
        douCenterX = posStartX + lineTotalWidth / 2;

    }

    /**
     * 画上下两个股票名字
     *
     * @param canvas 画布
     * @param string 股票名字
     */
    private void drawText(Canvas canvas, String string, boolean isTop) {

        if (isTop)
            canvas.drawText(string, maxTextLength, singleY + textSize / 3, paintStockName);
        else
            canvas.drawText(string, maxTextLength, singleY * 2 + (textSize / 3), paintStockName);
    }

    /**
     * draw top line
     */
    private void drawTopLine(Canvas canvas, boolean isPos, float startX, float endX) {

        paintLine.setColor(topColor);

        if (isPos && topPosEndX != 0f) {

            canvas.drawLine(startX, singleY, topPosEndX, singleY, paintLine);
        } else if (topNegStartX != 0f) {

            canvas.drawLine(topNegStartX, singleY, endX, singleY, paintLine);
        }
    }

    /**
     * draw top line path
     */
    private void drawTopLinePath(Canvas canvas, boolean isPos, float startX, float endX) {

        paintLinePath.setColor(topColor);

        if (isPos && topPosEndX != 0f) {

            pathLine.reset();

            pathLine.moveTo(startX - lineWidth / 2, singleY - lineWidth / 2);
            pathLine.lineTo(topPosEndX, singleY - lineWidth / 2);
            pathLine.lineTo(topPosEndX, singleY + lineWidth / 2);
            pathLine.lineTo(startX - lineWidth / 2, singleY + lineWidth / 2);

            pathLine.addCircle(topPosEndX, singleY, lineWidth / 2, Path.Direction.CW);

            canvas.drawPath(pathLine, paintLinePath);
        } else if (topNegStartX != 0f) {

            pathLine.reset();

            pathLine.moveTo(endX + lineWidth / 2, singleY - lineWidth / 2);
            pathLine.lineTo(endX + lineWidth / 2, singleY + lineWidth / 2);
            pathLine.lineTo(topNegStartX, singleY + lineWidth / 2);
            pathLine.lineTo(topNegStartX, singleY - lineWidth / 2);

            pathLine.addCircle(topNegStartX, singleY, lineWidth / 2, Path.Direction.CW);

            canvas.drawPath(pathLine, paintLinePath);
        }
    }

    /**
     * draw bottom line
     */
    private void drawBottomLinePath(Canvas canvas, boolean isPos, float startX, float endX) {

        paintLinePath.setColor(bottomColor);

        if (isPos && btmPosEndX != 0f) {

            pathLine.reset();
            pathLine.moveTo(startX - lineWidth / 2, singleY * 2 - lineWidth / 2);
            pathLine.lineTo(btmPosEndX, singleY * 2 - lineWidth / 2);
            pathLine.lineTo(btmPosEndX, singleY * 2 + lineWidth / 2);
            pathLine.lineTo(startX - lineWidth / 2, singleY * 2 + lineWidth / 2);
            pathLine.addCircle(btmPosEndX, singleY * 2, lineWidth / 2, Path.Direction.CW);

            canvas.drawPath(pathLine, paintLinePath);
        } else if(btmNegStartX != 0f){

            pathLine.reset();
            pathLine.moveTo(endX + lineWidth / 2, singleY * 2 - lineWidth / 2);
            pathLine.lineTo(endX + lineWidth / 2, singleY * 2 + lineWidth / 2);
            pathLine.lineTo(btmNegStartX, singleY * 2 + lineWidth / 2);
            pathLine.lineTo(btmNegStartX, singleY * 2 - lineWidth / 2);
            pathLine.addCircle(btmNegStartX, singleY * 2, lineWidth / 2, Path.Direction.CW);

            canvas.drawPath(pathLine, paintLinePath);
        }
    }

    /**
     * draw bottom line
     */
    private void drawBottomLine(Canvas canvas, boolean isPos, float startX, float endX) {

        paintLine.setColor(bottomColor);

        if (isPos) {

            if (btmPosEndX != 0f)
                canvas.drawLine(startX, singleY * 2, btmPosEndX, singleY * 2, paintLine);
        } else {

            if (btmNegStartX != 0f)
                canvas.drawLine(btmNegStartX, singleY * 2, endX, singleY * 2, paintLine);
        }
    }

    /**
     * draw value text
     */
    private void drawValueText(Canvas canvas, String sTop, String sBtm, float xTop, float xBtm) {

        paintValue.setColor(topColor);
        canvas.drawText(sTop, xTop, singleY - (textValueSize / 3 + lineWidth / 2), paintValue);

        paintValue.setColor(bottomColor);
        canvas.drawText(sBtm, xBtm, singleY * 2 - (textValueSize / 3 + lineWidth / 2), paintValue);
    }

    /**
     * draw dash line
     */
    private void drawDashLine(Canvas canvas, float x) {

        Path pathDash = new Path();
        pathDash.moveTo(x, singleY / 2);
        pathDash.lineTo(x, singleY * 5 / 2);

        canvas.drawPath(pathDash, paintDashLine);
    }

    /**
     * 数据为正数的时候动画
     */
    private void doPosAnimation(final boolean isTop, float startX, float endX) {

        ValueAnimator animator = ValueAnimator.ofObject(new PositiveEvaluator(), startX, endX);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                if (isTop)
                    topPosEndX = (float) valueAnimator.getAnimatedValue();
                else
                    btmPosEndX = (float) valueAnimator.getAnimatedValue();

                invalidate();

            }
        });

        animator.setStartDelay(200);
        animator.setDuration(800);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.start();
    }

    private class PositiveEvaluator implements TypeEvaluator<Float> {

        @Override
        public Float evaluate(float v, Float startValue, Float endValue) {

            return startValue + v * (endValue - startValue);
        }
    }

    /**
     * 数据为正数的时候动画
     */
    private void doNegAnimation(final boolean isTop, float startX, float endX) {

        ValueAnimator animator = ValueAnimator.ofObject(new NegativeEvaluator(), startX, endX);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                if (isTop)
                    topNegStartX = (float) valueAnimator.getAnimatedValue();
                else
                    btmNegStartX = (float) valueAnimator.getAnimatedValue();

                invalidate();

            }
        });

        animator.setStartDelay(200);
        animator.setDuration(800);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.start();
    }

    private class NegativeEvaluator implements TypeEvaluator<Float> {

        @Override
        public Float evaluate(float v, Float startValue, Float endValue) {

            return endValue - v * (endValue - startValue);
        }
    }

    /**
     * 根据传入的fTop、fBtm计算相应的绘制的坐标点
     */
    private void confirmDrawPointInfo(float fTop, float fBtm) {

        boolean topIsPos;
        boolean btmIsPos;

        //虚线所在的坐标点位置
        float dashLineX;

        //上部线条的坐标信息
        float topStartX, topEndX;

        //下部线条的坐标信息
        float btmStartX, btmEndX;

        //上部数据值的坐标
        float topValueX;

        //下部数据值的坐标
        float btmValueX;

        //两个数据都为0
        if (fTop == 0 && fBtm == 0) {

            topIsPos = btmIsPos = true;
            dashLineX = posStartX - lineWidth / 2;
            topStartX = posStartX;
            topEndX = posStartX;
            btmStartX = posStartX;
            btmEndX = posStartX;
            topValueX = posStartX + measureText(paintValue, sTopValue) / 2;
            btmValueX = posStartX + measureText(paintValue, sBtmValue) / 2;
        }
        //两个数据都为+
        else if (fTop >= 0 && fBtm >= 0) {

            topIsPos = btmIsPos = true;
            dashLineX = posStartX - lineWidth / 2;
            //顶部为大值
            if (Math.max(fTop, fBtm) == fTop) {

                topStartX = posStartX;
                topEndX = width;

                btmStartX = posStartX;
                btmEndX = posStartX + (lineTotalWidth * fBtm / fTop);

            } else {

                topStartX = posStartX;
                topEndX = posStartX + (lineTotalWidth * fTop / fBtm);

                btmStartX = posStartX;
                btmEndX = width;
            }

            topValueX = posStartX + measureText(paintValue, sTopValue) / 2;
            btmValueX = posStartX + measureText(paintValue, sBtmValue) / 2;
        }
        //两个数据都为-
        else if (fTop <= 0 && fBtm <= 0) {

            topIsPos = btmIsPos = false;
            dashLineX = width + lineWidth / 2;
            //第二条占据满条
            if (Math.max(fTop, fBtm) == fTop) {

                topStartX = width - (lineTotalWidth * fTop / fBtm);
                topEndX = width;

                btmStartX = posStartX;
                btmEndX = width;
            } else {

                topStartX = posStartX;
                topEndX = width;

                btmStartX = width - (lineTotalWidth * fBtm / fTop);
                btmEndX = width;

            }

            topValueX = btmValueX = width - (measureText(paintValue, sTopValue) / 2 + textMarginRight);
        }
        //两个数据一个 + 一个 -
        else {


            float faTop = Math.abs(fTop);
            float faBtm = Math.abs(fBtm);
            //顶部满格
            if (Math.max(faTop, faBtm) == faTop) {

                //顶部数据正数，底部数据为负数
                if (fTop > 0) {

                    topIsPos = true;
                    btmIsPos = false;

                    topStartX = douCenterX + lineWidth / 2;
                    topEndX = width;

                    btmStartX = douCenterX - (width - douCenterX) * faBtm / faTop;
                    btmEndX = douCenterX - lineWidth / 2;

                    topValueX = douCenterX + measureText(paintValue, sTopValue) / 2 + textMarginRight;
                    btmValueX = douCenterX - measureText(paintValue, sBtmValue) / 2 - textMarginRight;

                }
                //顶部数据为负数，底部数据为正数
                else {

                    topIsPos = false;
                    btmIsPos = true;

                    topStartX = posStartX;
                    topEndX = douCenterX - lineWidth / 2;

                    btmStartX = douCenterX + lineWidth / 2;
                    btmEndX = douCenterX + ((width - douCenterX) * faBtm / faTop);

                    topValueX = douCenterX - measureText(paintValue, sTopValue) / 2 - textMarginRight;
                    btmValueX = douCenterX + measureText(paintValue, sBtmValue) / 2 + textMarginRight;

                }

            }
            //底部满格
            else {

                //底部数据正数， 顶部数据为负数
                if (fBtm > 0) {

                    topIsPos = false;
                    btmIsPos = true;

                    topStartX = douCenterX - (width - douCenterX) * faTop / faBtm;
                    topEndX = douCenterX - lineWidth / 2;

                    btmStartX = douCenterX + lineWidth / 2;
                    btmEndX = width;

                    topValueX = douCenterX - measureText(paintValue, sTopValue) / 2 - textMarginRight;
                    btmValueX = douCenterX + measureText(paintValue, sBtmValue) / 2 + textMarginRight;
                }
                //底部数据为负值，顶部数据为正值
                else {

                    topIsPos = true;
                    btmIsPos = false;

                    topStartX = douCenterX + lineWidth / 2;
                    topEndX = douCenterX + (width - douCenterX) * faTop / faBtm;

                    btmStartX = posStartX;
                    btmEndX = douCenterX - lineWidth / 2;

                    topValueX = douCenterX + measureText(paintValue, sTopValue) / 2 + textMarginRight;
                    btmValueX = douCenterX - measureText(paintValue, sBtmValue) / 2 - textMarginRight;
                }
            }

            dashLineX = douCenterX;
        }

        this.dashLineX = dashLineX;
        this.topStartX = topStartX;
        this.topEndX = topEndX;
        this.btmStartX = btmStartX;
        this.btmEndX = btmEndX;
        this.topValueX = topValueX;
        this.btmValueX = btmValueX;
        this.topIsPos = topIsPos;
        this.btmIsPos = btmIsPos;

    }

    /**
     * 绘制一句话描述
     */
    private void drawContent(Canvas canvas, String content) {

        canvas.drawText(content, 0, singleY*3 - contentPaddingTop, paintContent);
    }

    /**
     * 供外部调用的启动动画
     */
    public void startAnimation() {

        topPosEndX = topNegStartX = btmPosEndX = btmNegStartX = 0f;
        invalidate();

        doAnimation();
    }

    private void doAnimation() {

        if (width == 0)
            return;

        if (topIsPos)
            doPosAnimation(true, topStartX, topEndX);
        else
            doNegAnimation(true, topStartX, topEndX);

        if (btmIsPos)
            doPosAnimation(false, btmStartX, btmEndX);
        else
            doNegAnimation(false, btmStartX, btmEndX);
    }

}
