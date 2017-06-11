package customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zpf on 2016/8/4.
 */
public class FlowLayout extends ViewGroup {

    //记录每个控件的左，上，右，下margin和宽度、高度
    List<Integer[]> tvs = new ArrayList<>();
    //存储每个TextView的l, t, r, b数值
    List<Integer[]> lines = new ArrayList<>();

    private Integer[] line = new Integer[]{0, 0, 0, 0};


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int width = paddingLeft + paddingRight;
        int height = paddingTop + paddingBottom;

        int count = getChildCount();

        int lineWidth = paddingLeft + paddingRight;
        int lineHeight = 0;

        for (int i = 0; i < count; i++) {

            //测量子控件
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int leftMargin = lp.leftMargin;
            int topMargin = lp.topMargin;
            int rightMargin = lp.rightMargin;
            int bottomMargin = lp.bottomMargin;

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            int childTotalWidth = childWidth + leftMargin + rightMargin;
            int childTotalHeight = childHeight + topMargin + bottomMargin;

            //判断lineWidth是否大于measureWidth
            if (lineWidth + childTotalWidth > measureWidth) {

                width = Math.max(width, lineWidth);
                height += lineHeight;

                lineWidth = childTotalWidth + paddingLeft + paddingRight;
                lineHeight = childTotalHeight;
            } else {

                lineWidth += childTotalWidth;
                lineHeight = Math.max(lineHeight, childTotalHeight);
            }

            if (i == count - 1) {

                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }

        setMeasuredDimension(measureWidthMode == MeasureSpec.EXACTLY ? measureWidth : width,
                measureHeightMode == MeasureSpec.EXACTLY ? measureHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int count = getChildCount();

        int width = paddingLeft + paddingRight;
        int height = paddingTop + paddingBottom;

        int lineWidth = paddingLeft + paddingRight;
        int lineHeight =0;


        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int leftMargin = lp.leftMargin;
            int topMargin = lp.topMargin;
            int rightMargin = lp.rightMargin;
            int bottomMargin = lp.bottomMargin;

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            int childTotalWidth = leftMargin + childWidth + rightMargin;
            int childTotalHeight = topMargin + childHeight + bottomMargin;

            //判断lineWidth是否大于measureWidth
            if (lineWidth + childTotalWidth > getMeasuredWidth()) {

                int startLeft = paddingLeft + (getMeasuredWidth()-lineWidth)/2;
                int startTop = height - paddingBottom;

                for (int c=0; c<tvs.size(); c++) {
                    Integer[] tv = tvs.get(c);
                    Integer[] line = new Integer[]{
                            startLeft + tv[0],
                            startTop + tv[1],
                            startLeft + tv[0] + tv[4],
                            startTop + tv[1] + tv[5]
                    };

                    lines.add(line);
                    startLeft += (tv[0] + tv[2] + tv[4]);
                }

                tvs.clear();

                Integer[] tv = new Integer[]{
                        leftMargin,
                        topMargin,
                        rightMargin,
                        bottomMargin,
                        childWidth,
                        childHeight};

                tvs.add(tv);

                width = Math.max(width, lineWidth);
                height += lineHeight;

                lineWidth = childTotalWidth + paddingLeft + paddingRight;
                lineHeight = childTotalHeight;
            } else {

                Integer[] tv = new Integer[]{
                        leftMargin,
                        topMargin,
                        rightMargin,
                        bottomMargin,
                        childWidth,
                        childHeight};

                tvs.add(tv);

                lineWidth += childTotalWidth;
                lineHeight = Math.max(lineHeight, childTotalHeight);
            }

            if (i == count - 1) {

                int startLeft = paddingLeft + (getMeasuredWidth()-lineWidth)/2;
                int startTop = height - paddingBottom;

                for (int c=0; c<tvs.size(); c++) {

                    Integer[] tv = tvs.get(c);
                    line[0] = startLeft + tv[0];
                    line[1] = startTop + tv[1];
                    line[2] = startLeft + tv[0] + tv[4];
                    line[3] = startTop + tv[1] + tv[5];

                    lines.add(line);
                    startLeft += (tv[0] + tv[2] + tv[4]);
                }

                tvs.clear();
            }
        }

        for (int n=0; n<count; n++) {

            View child = getChildAt(n);

            Integer[] tv = lines.get(n);
            child.layout(tv[0], tv[1], tv[2], tv[3]);
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
}
