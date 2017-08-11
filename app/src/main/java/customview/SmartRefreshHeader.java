package customview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zpf.animmenu.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import utils.DisplayUtil;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by zpf on 2017/8/7.
 * SmartRefreshLayout用的自定义刷新头部
 */

public class SmartRefreshHeader extends LinearLayout implements RefreshHeader {

    public static String REFRESH_HEADER_PULLDOWN = "下拉可以刷新";
    public static String REFRESH_HEADER_REFRESHING = "正在加载...";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
    public static String REFRESH_HEADER_FINISH = "刷新完成";
    public static String REFRESH_HEADER_FAILED = "刷新失败";

    protected String KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";
    protected Date mLastTime;
    protected DateFormat mFormat = new SimpleDateFormat("上次更新 M-d HH:mm", Locale.CHINA);
    /**
     * 奔跑小牛
     */
    private AnimationDrawable animRun;
    /**
     * title
     */
    private TextView tvTitle;
    /**
     * time
     */
    private TextView tvTime;
    private SharedPreferences mShared;


    public SmartRefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public SmartRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SmartRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SmartRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {

        setMinimumHeight(DisplayUtil.dip2px(context, 80));
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        ImageView ivRun = new ImageView(context);
        ivRun.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ivRun.setBackgroundResource(R.drawable.cowboy_header_anim);
        addView(ivRun);

        animRun = (AnimationDrawable) ivRun.getBackground();
        animRun.setOneShot(false);

        LinearLayout layoutText = new LinearLayout(context);
        layoutText.setOrientation(VERTICAL);
        layoutText.setGravity(Gravity.CENTER_VERTICAL);

        tvTitle = new TextView(context);
        tvTitle.setText(REFRESH_HEADER_PULLDOWN);
        tvTitle.setTextColor(0xff666666);
        tvTitle.setTextSize(16);

        tvTime = new TextView(context);
        tvTime.setTextColor(0xff7c7c7c);
        tvTime.setTextSize(12);

        LayoutParams lpHeaderText = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutText.addView(tvTitle, lpHeaderText);
        LayoutParams lpUpdateText = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutText.addView(tvTime, lpUpdateText);

        addView(layoutText, WRAP_CONTENT, WRAP_CONTENT);

        if (isInEditMode())
            tvTitle.setText(REFRESH_HEADER_REFRESHING);

        KEY_LAST_UPDATE_TIME += context.getClass().getName();
        mShared = context.getSharedPreferences("ClassicsHeader", Context.MODE_PRIVATE);
        setLastUpdateTime(new Date(mShared.getLong(KEY_LAST_UPDATE_TIME, System.currentTimeMillis())));

    }

    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

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

        animRun.start();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {

        animRun.stop();
        if (success)
            tvTitle.setText(REFRESH_HEADER_FINISH);
        else
            tvTitle.setText(REFRESH_HEADER_FAILED);
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
            case PullDownToRefresh:
                tvTitle.setText(REFRESH_HEADER_PULLDOWN);
                break;
            case Refreshing:
                tvTitle.setText(REFRESH_HEADER_REFRESHING);
                break;
            case ReleaseToRefresh:
                tvTitle.setText(REFRESH_HEADER_RELEASE);
                break;
        }
    }

    public SmartRefreshHeader setLastUpdateTime(Date time) {
        mLastTime = time;
        tvTime.setText(mFormat.format(time));
        if (mShared != null && !isInEditMode()) {
            mShared.edit().putLong(KEY_LAST_UPDATE_TIME, time.getTime()).apply();
        }
        return this;
    }
}
