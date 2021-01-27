package com.example.zpf.animmenu;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import customview.CircleTextView;
import utils.TextViewUtil;

public class CircleTextViewAnim extends AppCompatActivity implements View.OnClickListener {

    private CircleTextView ctvOne, ctvTwo, ctvThree, ctvFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_text_view_anim);

        initView();

    }

    private void initView() {

        initCircleTextView();

        initGradientTextView();

        initPartClickTextView();
    }

    /**
     * init CircleTextView
     */
    private void initCircleTextView() {

        Button btnStartAnim = (Button) findViewById(R.id.btn_start_anim);
        btnStartAnim.setOnClickListener(this);

        Button btnCancelAnim = (Button) findViewById(R.id.btn_cancel_anim);
        btnCancelAnim.setOnClickListener(this);

        ctvOne = (CircleTextView) findViewById(R.id.ctv_one);
        ctvTwo = (CircleTextView) findViewById(R.id.ctv_two);
        ctvThree = (CircleTextView) findViewById(R.id.ctv_three);
        ctvFour = (CircleTextView) findViewById(R.id.ctv_four);
    }

    /**
     * 渐变文字
     */
    private void initGradientTextView() {

        TextView tvGradient = (TextView) findViewById(R.id.tv_gradient);
        float width = tvGradient.getPaint().measureText(tvGradient.getText().toString());

        Shader shader = new LinearGradient(0, 0, width, 0,
                Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
        tvGradient.getPaint().setShader(shader);
    }

    /**
     * init part click event textview
     */
    private void initPartClickTextView() {

        TextView tv = (TextView) findViewById(R.id.tv_part_click);

        if (TextUtils.isEmpty(tv.getText()))
            return;

        SpannableString ss = new SpannableString(tv.getText());
        int endIndex = ss.toString().length();
        int startIndex = endIndex - 6;
        ss.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv.setText(ss);

        TextViewUtil.getInstance().setPartOnClickListener(tv, startIndex, endIndex, new TextViewUtil.PartOnClickListener() {
            @Override
            public void partOnClick(View v) {

                Toast.makeText(CircleTextViewAnim.this, "你还要我怎样", Toast.LENGTH_SHORT).show();
            }
        });
//        ss.setSpan(new AbsoluteSizeSpan(44, true), 2, 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        ss.setSpan(new RelativeSizeSpan(2.0f), 4, 6, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_start_anim) {
            animStartCircle();
        } else if (v.getId() == R.id.btn_cancel_anim) {
            animCancelCircle();
        }
    }

    //开启旋转动画
    private void animStartCircle() {
        ctvOne.doAnimation();
        ctvTwo.doAnimation();
        ctvThree.doAnimation();
        ctvFour.doAnimation();
    }

    //关闭旋转动画
    private void animCancelCircle() {
        ctvOne.cancelAnimation();
        ctvTwo.cancelAnimation();
        ctvThree.cancelAnimation();
        ctvFour.cancelAnimation();
    }
}
