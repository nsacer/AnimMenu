package com.example.zpf.animmenu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.hanks.htextview.HTextView;

import java.util.ArrayList;

import top.wefor.circularanim.CircularAnim;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LauncherActivity extends AppCompatActivity implements View.OnClickListener{

    /** 触发切换的message.what */
    private static final int MESSAGE_SWITCH_TXT = 0;
    /** 图片切换的时间间隔 */
    private static final int DELAY_TIME = 4000;
    /** 等待图片的切换次数 */
    private static final int SWITCH_TIMES = 6;
    private static final String[] strings = new String[] {

            "曾经沧海难为水",
            "除却巫山不是云",
            "长风破浪会有时",
            "直挂云帆济沧海",
            "天阶夜色凉如水"
    };
    private static final int[] catImages = new int[] {

            R.mipmap.img_one, R.mipmap.img_two, R.mipmap.img_three,
            R.mipmap.img_four, R.mipmap.img_five, R.mipmap.img_six,
            R.mipmap.img_sev, R.mipmap.img_eig, R.mipmap.img_nin
    };


    private ArrayList<View> views = new ArrayList<>();
    private int index = 1;
    private int currentShowIndex = 0;
    private int currentImageIndex = 0;
    private ImageSwitcher is;
    private HTextView htv;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            doMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        initListView();

        initView();

    }

    private void initListView() {

        for (int i:catImages) {

            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setImageResource(i);

            views.add(iv);
        }
    }

    private void initView() {

        TextView tvJump = (TextView) findViewById(R.id.tv_jump);
        tvJump.setOnClickListener(this);

        htv = (HTextView) findViewById(R.id.htv);
        htv.animateText(strings[currentShowIndex]);

        is = (ImageSwitcher) findViewById(R.id.is);
        is.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {

                return createImageView();
            }
        });
    }

    private ImageView createImageView() {

        ImageView iv = new ImageView(LauncherActivity.this);
        iv.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setImageResource(catImages[currentImageIndex]);

        return iv;
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(MESSAGE_SWITCH_TXT, DELAY_TIME);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_jump:

                openActivity(MainActivity.class, v);
                break;

            default:
                break;
        }
    }

    private void doMessage(Message message) {

        switch (message.what) {

            case MESSAGE_SWITCH_TXT:

                currentImageIndex = index % (catImages.length - 1);
                is.setImageResource(catImages[currentImageIndex]);

                currentShowIndex = index % (strings.length - 1);
                htv.animateText(strings[currentShowIndex]);
                handler.sendEmptyMessageDelayed(MESSAGE_SWITCH_TXT, DELAY_TIME);

                index++;
                if (index > SWITCH_TIMES) {

                    startActivity(new Intent(this, MainActivity.class));
                    this.finish();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(MESSAGE_SWITCH_TXT);
    }

    /**
     * 动画跳转页面
     * */
    private void openActivity(final Class<?> clazz, View view) {

        CircularAnim.fullActivity(this, view)
                .colorOrImageRes(R.color.colorAccent)
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {

                        startActivity(new Intent(LauncherActivity.this, clazz));
                        LauncherActivity.this.finish();
                    }
                });
    }
}
