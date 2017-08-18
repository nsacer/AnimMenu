package com.example.zpf.animmenu;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import customview.CircleRingGraph;
import customview.CircleView;
import customview.ProgressScore;
import customview.SpiderChart;
import customview.TextDiagram;
import customview.TurtleGraph;
import utils.DisplayUtil;

@ContentView(R.layout.activity_chart)
public class ChartActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvNumberChange;
    private CircleView circleView;
    private CircleRingGraph circleRingGraph;
    private EditText etProgress;

    private TurtleGraph turtleGraph;

    /** spiderchart数据 */
    private static final float[] spiderData = new float[] {96, 88, 60, 20, 46};
    private SpiderChart spiderChart;

    /** TextDiagram */
    private TextDiagram td;

    private PopupWindow popWin = new PopupWindow();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        initViewOne();

        initViewTwo();

        initViewThree();

        initViewFour();

        initViewFive();

        initPopWindow();

        initTextDiagram();
    }

    /////////////////////--------字母变换动画--------//////////////////////

    /**  */
    private void initViewOne() {

        Button btnOne = (Button) findViewById(R.id.btn_start_anim_one);
        btnOne.setOnClickListener(this);

        //转换TextView显示
        TextView tvCharInfo = (TextView) findViewById(R.id.tv_char_info);
        char temp = 'A';
        int startI = temp;

        temp = 'a';
        int starti = temp;

        temp = 'Z';
        int endZ = temp;

        temp = 'z';
        int endz = temp;

        String sInfo = "A -> " + startI +
                " | a -> " + starti +
                " |  Z -> " + endZ +
                " | z -> " + endz;

        tvCharInfo.setText(sInfo);

        //动态显示TextView
        tvNumberChange = (TextView) findViewById(R.id.tv_number);

    }

    /**
     * 从A -> Z 动画变换的Evaluator
     */
    private class CharEvaluator implements TypeEvaluator<Character> {
        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {

            int startI = startValue;
            int endI = endValue;
            int curI = (int) (startI + fraction * (endI - startI));
            return (char) curI;
        }
    }

    /**
     * 从A -> Z 变换动画
     */
    private void doCharChangeAnim() {

        ValueAnimator animator = ValueAnimator.ofObject(new CharEvaluator(), 'A', 'Z');
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                char result = (char) animation.getAnimatedValue();
                tvNumberChange.setText(String.valueOf(result));
            }
        });
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    /////////////////////--------圆形缩放动画--------//////////////////////

    private void initViewTwo() {

        Button btnTwo = (Button) findViewById(R.id.btn_start_anim_two);
        btnTwo.setOnClickListener(this);

        circleView = (CircleView) findViewById(R.id.circle_view);
    }

    /////////////////////--------进度条动画--------//////////////////////

    private void initViewThree() {

        Button btnThree = (Button) findViewById(R.id.btn_start_anim_three);
        btnThree.setOnClickListener(this);

        circleRingGraph = (CircleRingGraph) findViewById(R.id.circle_ring_progress);
        etProgress = (EditText) findViewById(R.id.et_progress);

    }

    /////////////////////--------TurtleGraph--------//////////////////////

    private void initViewFour() {

        Button btnTurtle = (Button) findViewById(R.id.btn_start_anim_four);
        btnTurtle.setOnClickListener(this);

        Button btnCancel = (Button) findViewById(R.id.btn_cancel_anim_four);
        btnCancel.setOnClickListener(this);

        turtleGraph = (TurtleGraph) findViewById(R.id.turtle_graph);
    }


    /////////////////////--------SpiderChart--------//////////////////////

    private void initViewFive() {

        spiderChart = (SpiderChart) findViewById(R.id.spiderChart);
        spiderChart.setOnClickListener(this);
        (findViewById(R.id.btn_spider_anim)).setOnClickListener(this);
    }

    ////////////////////---------横向柱状图对比---------///////////////////////
    private void initTextDiagram() {

        td = (TextDiagram) findViewById(R.id.td);
        td.setContent("this is the bottom");
        td.setStockName("重阳股份", "汾酒集团");
//        td.setStockValue("12.69", "36.08", true);
        td.setStockValue("12.69", "-36.08", true);
//        td.setStockValue("-12.69", "-36.08", true);
        findViewById(R.id.btn_td_anim).setOnClickListener(this);
    }

    /**
     * Button Click Event
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_start_anim_one:

                doCharChangeAnim();
                break;

            case R.id.btn_start_anim_two:

                circleView.doAnimation();
                break;

            case R.id.btn_start_anim_three:

                if (TextUtils.isEmpty(etProgress.getText()))
                    Snackbar.make(v, "请输入0~360之间的数", Snackbar.LENGTH_SHORT).show();
                else
                    circleRingGraph.setProgress(Integer.parseInt(etProgress.getText().toString()));
                break;

            case R.id.btn_start_anim_four:

                turtleGraph.doAnimator();
                break;

            case R.id.btn_cancel_anim_four:

                turtleGraph.cancelAnimator();
                break;

            case R.id.spiderChart:

                showPopWindow();
                break;
            case R.id.btn_spider_anim:

                spiderChart.doAnimation(spiderData);
                break;

            case R.id.btn_td_anim:

                td.startAnimation();
                break;

            default:
                break;
        }
    }

    /**
     * 初始化PopWindow
     */
    private void initPopWindow() {

        View view = LayoutInflater.from(this).inflate(R.layout.pop_window_layout, null);
        view.findViewById(R.id.iv_close_pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popWin.dismiss();
            }
        });

        popWin.setWidth(Info.DISPLAY_WIDTH - DisplayUtil.dip2px(this, 32));
        popWin.setHeight(Info.DISPLAY_HEIGHT * 8 / 11);
        popWin.setContentView(view);
        popWin.setAnimationStyle(R.style.PopWindowAnimation);

    }

    /**
     * show popWin
     */
    private void showPopWindow() {

        if (!popWin.isShowing())
            popWin.showAtLocation(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0),
                    Gravity.BOTTOM, 0, 0);
    }

    @Event(R.id.btn_progress_score_anim)
    private void startProgressScore(View view) {

        ProgressScore progressScore = (ProgressScore) findViewById(R.id.progress_score);
        progressScore.setBitmapIndicatorHead(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_cat));
        progressScore.setProgressAndStartAnim(120);
    }


}
