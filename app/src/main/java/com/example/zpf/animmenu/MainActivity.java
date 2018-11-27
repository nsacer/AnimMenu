package com.example.zpf.animmenu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhouwei.blurlibrary.EasyBlur;

import fragment.BlankFragment2;
import fragment.BlankFragment3;
import fragment.BlankFragment4;
import fragment.BlankFragment5;
import fragment.Fragment1;
import utils.SPKey;
import utils.ScreenUtils;
import utils.SharedPreferencesHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private boolean isMenuOpen = false;
    //url
    private String mCover = "https://isujin.com/wp-content/uploads/2018/05/wallhaven-635742.jpg";
    //背景图
    private ImageView ivCover;

    private ImageView iv_menu1;
    private ImageView iv_menu2;
    private ImageView iv_menu3;
    private ImageView iv_menu4;
    private ImageView iv_menu5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDeviceInfo();

        initView();

        String bgUrl = SharedPreferencesHelper.getInstance(this).getString(SPKey.getInstance().MAIN_BG_URL, null);
        if (!TextUtils.isEmpty(bgUrl)) mCover = bgUrl;
        mRequestManager.asBitmap().load(mCover).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                ivCover.setImageBitmap(EasyBlur.with(mContext).bitmap(resource).radius(8).blur());
            }
        });
    }

    private void initDeviceInfo() {

        Info.DISPLAY_SCREEN_HEIGHT = ScreenUtils.getScreenHeightWithoutVirtualBar(this);
        Info.DISPLAY_SCREEN_WIDTH = ScreenUtils.getScreenWidth(this);
        Info.DISPLAY_STATUS_BAR_HEIGHT = ScreenUtils.getStatusBarHeight(this);
        Info.DISPLAY_VIRTUALS_BAR_HEIGHT = ScreenUtils.getVirtualBarHeight(this);
        Info.DISPLAY_APP_SHOW_HEIGHT = Info.DISPLAY_SCREEN_HEIGHT - Info.DISPLAY_STATUS_BAR_HEIGHT;
    }

    private void initView() {

        ivCover = findViewById(R.id.ivCoverMain);

        ImageView iv_switch = findViewById(R.id.menu_switch);
        iv_switch.setOnClickListener(this);

        iv_menu1 = findViewById(R.id.menu_1);
        iv_menu1.setOnClickListener(this);

        iv_menu2 = findViewById(R.id.menu_2);
        iv_menu2.setOnClickListener(this);

        iv_menu3 = findViewById(R.id.menu_3);
        iv_menu3.setOnClickListener(this);

        iv_menu4 = findViewById(R.id.menu_4);
        iv_menu4.setOnClickListener(this);

        iv_menu5 = findViewById(R.id.menu_5);
        iv_menu5.setOnClickListener(this);

    }

    /**
     * Fragment one
     */
    private void addOneFragment() {

        Fragment1 fragment1 = new Fragment1();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragmentCustomAnim(transaction);
        transaction.replace(R.id.layout_container, fragment1, "1");
        transaction.commit();
    }

    /**
     * FragmentTwo
     */
    private void replaceTwoFragment() {

        BlankFragment2 fragment2 = new BlankFragment2();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragmentCustomAnim(transaction);
        transaction.replace(R.id.layout_container, fragment2, "2");

        transaction.commit();
    }

    /**
     * FragmentThree
     */
    private void replaceThreeFragment() {

        BlankFragment3 fragment3 = new BlankFragment3();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragmentCustomAnim(transaction);
        transaction.replace(R.id.layout_container, fragment3, "3");

        transaction.commit();
    }

    /**
     * FragmentFour
     */
    private void replaceFourFragment() {

        BlankFragment4 fragment4 = new BlankFragment4();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragmentCustomAnim(transaction);
        transaction.replace(R.id.layout_container, fragment4, "4");

        transaction.commit();
    }

    /**
     * FragmentFive
     */
    private void replaceFiveFragment() {

        BlankFragment5 fragment5 = new BlankFragment5();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragmentCustomAnim(transaction);
        transaction.replace(R.id.layout_container, fragment5, "5");

        transaction.commit();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.menu_switch:

                if (isMenuOpen) {
                    closeAllMenu();
                } else {
                    openAllMenu();
                }
                break;
            case R.id.menu_1:
                addOneFragment();
                closeAllMenu();
                break;
            case R.id.menu_2:
                replaceTwoFragment();
                closeAllMenu();
                break;
            case R.id.menu_3:
                replaceThreeFragment();
                closeAllMenu();
                break;
            case R.id.menu_4:
                replaceFourFragment();
                closeAllMenu();
                break;
            case R.id.menu_5:
                replaceFiveFragment();
                closeAllMenu();
                break;
        }
    }

    /**
     * 子菜单的打开动画
     *
     * @param target 目标控件
     * @param index  目标index（从0开始）
     * @param total  子菜单的总个数
     * @param radius 展开半径
     */
    private void doAnimMenuOpen(View target, int index, int total, int radius) {

        if (target.getVisibility() != View.VISIBLE)
            target.setVisibility(View.VISIBLE);

        //根据index和total计算translationX/translationY的值
        double single = Math.PI / ((total - 1) * 2);
        float translationX = (float) -(radius * Math.sin(index * single));
        float translationY = (float) -(radius * Math.cos(index * single));

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(target, "translationX", 0f, translationX),
                ObjectAnimator.ofFloat(target, "translationY", 0f, translationY),
                ObjectAnimator.ofFloat(target, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(target, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(target, "alpha", 0f, 1f));

        //设置动画时间
        set.setDuration(500);
        set.setInterpolator(new OvershootInterpolator());
        set.start();

    }

    /**
     * 子菜单的关闭动画
     *
     * @param target 目标控件
     * @param index  目标index（从0开始）
     * @param total  子菜单的总个数
     * @param radius 展开半径
     */
    private void doAnimMenuClose(View target, int index, int total, int radius) {

        if (target.getVisibility() != View.VISIBLE)
            target.setVisibility(View.VISIBLE);

        //根据index和total计算translationX/translationY的值
        double single = Math.PI / ((total - 1) * 2);
        float translationX = (float) -(radius * Math.sin(index * single));
        float translationY = (float) -(radius * Math.cos(index * single));

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(target, "translationX", translationX, 0f),
                ObjectAnimator.ofFloat(target, "translationY", translationY, 0f),
                ObjectAnimator.ofFloat(target, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(target, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(target, "alpha", 1f, 0f));

        //设置动画时间
        set.setDuration(500);
        set.setInterpolator(new OvershootInterpolator());
        set.start();
    }

    //关闭所有的菜单方法
    private void closeAllMenu() {

        isMenuOpen = false;

        doAnimMenuClose(iv_menu1, 0, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
        doAnimMenuClose(iv_menu2, 1, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
        doAnimMenuClose(iv_menu3, 2, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
        doAnimMenuClose(iv_menu4, 3, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
        doAnimMenuClose(iv_menu5, 4, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
    }

    //打开所有的菜单的方法
    private void openAllMenu() {

        isMenuOpen = true;

        doAnimMenuOpen(iv_menu1, 0, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
        doAnimMenuOpen(iv_menu2, 1, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
        doAnimMenuOpen(iv_menu3, 2, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
        doAnimMenuOpen(iv_menu4, 3, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
        doAnimMenuOpen(iv_menu5, 4, 5, Info.DISPLAY_SCREEN_WIDTH / 3);
    }

    /**
     * Fragment默认动画
     */
    private void fragmentDefaultAnim(FragmentTransaction transaction) {

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }

    /**
     * Fragment自定义动画
     */
    private void fragmentCustomAnim(FragmentTransaction transaction) {

        //默认动画
        transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out);

        //绕Y轴翻转动画
//        transaction.setCustomAnimations(R.animator.fragment_rotate_enter, R.animator.fragment_rotate_exit,
//                R.animator.fragment_rotate_pop_enter, R.animator.fragment_rotate_pop_exit);

//        transaction.setCustomAnimations(R.animator.fragment_left_in, R.animator.fragment_left_out,
//                R.animator.fragment_right_in, R.animator.fragment_right_out);

        //旋转进入，退出
//        transaction.setCustomAnimations(R.animator.lase_in, R.animator.lase_out,
//                R.animator.fragment_right_in, R.animator.fragment_right_out);

        //中间底部放大pop，收起
//        transaction.setCustomAnimations(R.animator.pop_in, R.animator.pop_out,
//                R.animator.fragment_right_in, R.animator.fragment_right_out);
    }
}
