package behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.example.zpf.animmenu.R;

import customview.CircleImageView;

/**
 * Created by zpf on 2016/12/7.
 */

public class CircleImageViewBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    private final Context mContext;
    /**
     * 完全展开后的头像的最大尺寸（宽度、高度）
     */
    private float mCircleHeaderMaxSize;
    /**
     * Toolbar收缩后的头像的高度
     */
    private float mCircleHeaderMinSize;
    /**
     * 圆形头像的水平中心位置
     */
    private float mCircleHeaderStartX;
    /**
     * 圆形头像的竖直中心位置
     */
    private float mCircleHeaderStartY;
    /**
     * 圆形头像的高度
     */
    private float mCircleHeaderStartHeight;
    /**
     * 圆形头像收缩到Toolbar上的水平中心位置
     */
    private float mCircleHeaderToolbarCenterX;
    /**
     * Toolbar的一般高度
     */
    private float mToolbarHalfHeight;
    /**
     * 起始Toolbar的中心竖直位置
     */
    private float mToolbarStartY;

    public CircleImageViewBehavior(Context context, AttributeSet attrs) {
        mContext = context;
        init();
    }

    private void init() {
        setCircleHeaderSize();
    }

    private void setCircleHeaderSize() {
        mCircleHeaderMaxSize = mContext.getResources().getDimension(R.dimen.circle_header_max_size);
        mCircleHeaderMinSize = mContext.getResources().getDimension(R.dimen.circle_header_min_size);
        mCircleHeaderToolbarCenterX = mContext.getResources().getDimensionPixelOffset(R.dimen.toolbar_left_padding)
                + mCircleHeaderMinSize / 2;
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        // 依赖Toolbar控件
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {

        confirmNeedViewPosition(child, dependency);

        /** 可滚动的最大距离 */
        float maxScrollY = mToolbarStartY - getStatusBarHeight();

        float scrollPercent = dependency.getY() / maxScrollY;

        float distanceScrollY = (mCircleHeaderStartY - mToolbarHalfHeight)
                * (1f - scrollPercent) + child.getHeight() / 2;

        float distanceScrollX = (mCircleHeaderStartX - mCircleHeaderToolbarCenterX)
                * (1f - scrollPercent) + child.getWidth() / 2;

        //CircleImageView高度减小
        float offsetCircleHeader = (mCircleHeaderMaxSize - mCircleHeaderMinSize)
                * (1f - scrollPercent);

        child.setY(mCircleHeaderStartY - distanceScrollY);
        child.setX(mCircleHeaderStartX - distanceScrollX);

        CoordinatorLayout.LayoutParams ll = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        ll.width = ll.height = (int) (mCircleHeaderMaxSize - offsetCircleHeader);
        child.setLayoutParams(ll);

        return Boolean.TRUE;
    }


    private void confirmNeedViewPosition(CircleImageView child, View dependency) {

        if (mCircleHeaderStartX == 0)
            mCircleHeaderStartX = child.getX() + child.getWidth() / 2;

        if (mCircleHeaderStartY == 0)
            mCircleHeaderStartY = child.getY() + child.getHeight() / 2;

        if (mCircleHeaderStartHeight == 0)
            mCircleHeaderStartHeight = child.getHeight();

        if (mToolbarHalfHeight == 0)
            mToolbarHalfHeight = dependency.getHeight() / 2;

        if(mToolbarStartY == 0)
            mToolbarStartY = dependency.getY() + mToolbarHalfHeight;

    }

    private float getStatusBarHeight() {

        float result = 0;
        int identify = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(identify > 0)
            result = mContext.getResources().getDimension(identify);
        return result;
    }

}
