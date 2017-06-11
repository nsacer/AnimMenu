package com.example.zpf.animmenu;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Random;

@ContentView(R.layout.activity_turntable)
public class TurnTableActivity extends BaseActivity {

    /**
     * 转圈的次数
     */
    private final int RUN_COUNT = 6;

    /**
     * 开始抽奖按钮
     * 每次点击后设置为enable=false,选出结果设置为ture;
     */
    @ViewInject(R.id.btn_start_turn)
    private Button btnStartTurn;

    /**
     * 抽奖结束后显示抽中的第几个奖品的TextView
     */
    @ViewInject(R.id.tv_result)
    private TextView tvResult;

    /**
     * 要添加到ArrayList<ImageButton>集合里的控件id，注意顺序
     */
    private int[] ibIds = {R.id.ib_0, R.id.ib_1, R.id.ib_2, R.id.ib_3, R.id.ib_4,
            R.id.ib_5, R.id.ib_6, R.id.ib_7};

    /**
     * 八个奖品的ImageButton
     */
    private ArrayList<ImageButton> ibs = new ArrayList<>();
    private int timeC = 60;//变色时间间隔
    private int lightPosition = 0;//当前亮灯位置,从0开始
    private int runCount = RUN_COUNT;//需要转多少圈
    private int luckyPosition = 4;//中奖的幸运位置,从0开始

    /**
     * Handler发送切换信息的时间间隔
     * */
    private long timeSwitch = 160;
    private int indexTurn = 0;
    private int maxCount = Integer.MAX_VALUE;

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

        initTableLayout();

    }

    private void initTableLayout() {

        for (int ibId : ibIds) {

            ImageButton ib = (ImageButton) findViewById(ibId);
            ib.setEnabled(false);
            ibs.add(ib);
        }

        AnimationDrawable ad = new AnimationDrawable();
        ad.addFrame(ContextCompat.getDrawable(this, R.mipmap.turn_bg_0), 300);
        ad.addFrame(ContextCompat.getDrawable(this, R.mipmap.turn_bg_1), 300);
        ad.setOneShot(false);

        FrameLayout layoutBg = (FrameLayout) findViewById(R.id.layout_bg_turn);
        layoutBg.setBackground(ad);

        ad.start();
    }

    /**
     * 开始按钮的
     */
    @Event(R.id.btn_start_turn)
    private void startTurntable(View view) {

        luckyPosition = randomNum(0, 7);

        view.setEnabled(false);
//        runCount = RUN_COUNT;
//        timeC = 100;
//        ibs.get(luckyPosition).setBackground(ContextCompat.getDrawable(this,
//                R.drawable.layer_turntable_item_btn_nor));
//        new TimeCount(timeC * 9, timeC).start();

        handler.sendEmptyMessage(0);
        handler.sendEmptyMessageDelayed(-1, 3000);
    }

    /**
     * 生成随机数
     *
     * @param minNum
     * @param maxNum
     * @return
     */
    private int randomNum(int minNum, int maxNum) {
        int max = maxNum;
        int min = minNum;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 用来控制ImageButton状态切换的倒计时
     */
    class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            lightPosition = 0;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            //如果是最后一次滚动
            if (runCount > 0) {
                if (lightPosition > 0) {
                    ibs.get(lightPosition - 1).setBackground(
                            ContextCompat.getDrawable(TurnTableActivity.this,
                                    R.drawable.layer_turntable_item_btn_nor));
                }
                if (lightPosition < 8) {
                    ibs.get(lightPosition).setBackground(ContextCompat.getDrawable(TurnTableActivity.this,
                            R.drawable.layer_turntable_item_btn_sel));
                }

            } else if (runCount == 0) {

                if (lightPosition <= luckyPosition) {
                    if (lightPosition > 0) {
                        ibs.get(lightPosition - 1).setBackground(ContextCompat.getDrawable(TurnTableActivity.this,
                                R.drawable.layer_turntable_item_btn_nor));
                    }
                    if (lightPosition < 8) {
                        ibs.get(lightPosition).setBackground(ContextCompat.getDrawable(TurnTableActivity.this,
                                R.drawable.layer_turntable_item_btn_sel));
                    }
                }
            }

            lightPosition++;
        }

        @Override
        public void onFinish() {
            ImageButton ibLast = ibs.get(7);
            if (runCount != 0) {

                ibLast.setBackground(ContextCompat.getDrawable(TurnTableActivity.this,
                        R.drawable.layer_turntable_item_btn_nor));
                //最后几转速度变慢
                if (runCount < 3) timeC += 200;
                //在设定的所转圈数内开启新的倒计时切换item样式
                new TimeCount(timeC * 9, timeC).start();
                runCount--;
            }
            //如果是最后一圈且计时也已经结束
            if (runCount == 0 && lightPosition == 8) {

                btnStartTurn.setEnabled(true);
                tvResult.setText("恭喜你抽中: " + luckyPosition);
                if (luckyPosition != ibs.size())
                    ibLast.setBackground(ContextCompat.getDrawable(TurnTableActivity.this,
                            R.drawable.layer_turntable_item_btn_nor));

            }

        }
    }

    private void setLightBtn() {

        for (ImageButton ib : ibs)
            ib.setEnabled(false);

        ibs.get(indexTurn).setEnabled(true);

        if (maxCount <= 2) {

            timeSwitch += 60;
            if (indexTurn == 7)
                maxCount--;

        }

        if (maxCount <= 0 && indexTurn == luckyPosition) {

            showToast("选中奖品" + luckyPosition);
            btnStartTurn.setEnabled(true);
            timeSwitch = 160;
            indexTurn = 0;
            maxCount = Integer.MAX_VALUE;
            return;
        }

        int i= indexTurn % ibs.size() + 1;
        indexTurn = i > 7 ? 0 : i;

        handler.sendEmptyMessageDelayed(0, timeSwitch);
    }

    private void doMessage(Message message) {

        if (message.what == 0) {

            setLightBtn();

        } else if (message.what == -1) {

            maxCount = 2;
        }
    }
}
