package customview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import utils.DisplayUtil;

/**
 * Created by zpf on 2017/8/8.
 * SmartRefreshFooter
 */

public class SmartRefreshFooter extends RelativeLayout implements RefreshFooter {

    public static String REFRESH_FOOTER_PULLUP = "上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = "释放立即刷新";
    public static String REFRESH_FOOTER_REFRESHING = "正在加载...";
    public static String REFRESH_FOOTER_FINISH_SUCCESS = "加载完成";
    public static String REFRESH_FOOTER_FINISH_FAIL = "加载完成";

    /**
     * 加载状态描述TextView
     */
    private TextView tvContent;
    /**
     * 加载圈progressbar
     */
    private ProgressBar progressBar;

    public SmartRefreshFooter(Context context) {
        super(context);
        initView(context);
    }

    public SmartRefreshFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SmartRefreshFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SmartRefreshFooter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {

        setMinimumHeight(DisplayUtil.dip2px(context, 48));

        tvContent = new TextView(context);
        tvContent.setId(android.R.id.content);
        tvContent.setText(REFRESH_FOOTER_PULLUP);
        tvContent.setTextColor(0xff666666);
        tvContent.setTextSize(16);

        LayoutParams llText = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llText.addRule(CENTER_IN_PARENT);
        addView(tvContent, llText);

        progressBar = new ProgressBar(context);
        progressBar.getIndeterminateDrawable().setColorFilter(0xffe94b4b,
                android.graphics.PorterDuff.Mode.SRC_IN);
        int w = DisplayUtil.dip2px(context, 16);
        LayoutParams llPb = new LayoutParams(w, w);
        llPb.rightMargin = DisplayUtil.dip2px(context, 8);
        llPb.addRule(CENTER_VERTICAL);
        llPb.addRule(LEFT_OF, android.R.id.content);

        addView(progressBar, llPb);

    }

    @Override
    public void onPullingUp(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onPullReleasing(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onLoadmoreReleased(RefreshLayout layout, int footerHeight, int extendHeight) {

    }

    @Override
    public boolean setLoadmoreFinished(boolean finished) {
        return false;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

        progressBar.setVisibility(VISIBLE);
        tvContent.setText(REFRESH_FOOTER_REFRESHING);
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {

        progressBar.setVisibility(INVISIBLE);
        if (success)
            tvContent.setText(REFRESH_FOOTER_FINISH_SUCCESS);
        else
            tvContent.setText(REFRESH_FOOTER_FINISH_FAIL);
        return 400;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

        switch (newState) {

            case None:
            case PullToUpLoad:
                tvContent.setText(REFRESH_FOOTER_PULLUP);
                break;

            case Refreshing:
                tvContent.setText(REFRESH_FOOTER_REFRESHING);
                break;

            case ReleaseToLoad:
                tvContent.setText(REFRESH_FOOTER_RELEASE);
                break;
        }
    }
}
