package com.example.zpf.animmenu;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_image_switch_view_pager)
public class ImageSwitchViewPagerActivity extends BaseActivity implements View.OnTouchListener {


    @ViewInject(R.id.is_vp)
    private ImageSwitcher is;

    private int[] images = {R.mipmap.ic_header, R.mipmap.wall, R.mipmap.ic_photo, R.mipmap.red_envelope};

    int index = 0;

    float startX;//手指接触屏幕时X的坐标（演示左右滑动）
    float endX;//手指离开屏幕时的坐标（演示左右滑动）


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSwitcher();

    }

    private void initSwitcher() {

        is.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView iv = new ImageView(ImageSwitchViewPagerActivity.this);
                iv.setImageResource(images[index]);
                return iv;
            }
        });

        is.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //按下屏幕
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getX();//获取按下屏幕时X轴的坐标
            //手指抬起
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            endX = event.getX();
            //判断结束坐标大于起始坐标则为下一张（为避免误操作，设置30的判断区间）
            if (startX - endX > 30) {
                //三目运算判断当前图片已经为最后一张，则从头开始
                index = index + 1 < images.length ? ++index : 0;
                //使用系统自带的切换出入动画效果（也可以向ViewFlipper中一样自定义动画效果）
                is.setInAnimation(this, android.R.anim.fade_in);
                is.setOutAnimation(this, android.R.anim.fade_out);

                //判断结束坐标小于于起始坐标则为上一张（为避免误操作，设置30的判断区间）
            } else if (endX - startX > 30) {
                //三目运算判断当前图片已经为第一张，则上一张为数组内最后一张图片
                index = index - 1 >= 0 ? --index : images.length - 1;
                is.setInAnimation(this, android.R.anim.fade_in);
                is.setOutAnimation(this, android.R.anim.fade_out);
            }
            //设置ImageSwitcher的图片资源
            is.setImageResource(images[index]);
            //调用方法设置圆点对应状态
//            setImageBackground(index);
        }
        return true;
    }

}
