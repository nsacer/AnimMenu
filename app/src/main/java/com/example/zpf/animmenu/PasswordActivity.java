package com.example.zpf.animmenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import customview.LocusPassWordView;
import utils.Md5Utils;
import utils.SharedPreferencesHelper;

public class PasswordActivity extends AppCompatActivity {

    private Context mContext;
    private TextView tvMessage;
    private LocusPassWordView mPwdView;
    private SharedPreferencesHelper spHelper;
    private boolean hasGesturePassword = false;
    private boolean isFirstPassword = true;
    private String firstPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        initView();
    }

    /**
     * 初始化控件
     * */
    private void initView(){

        mContext = getApplicationContext();
        spHelper = SharedPreferencesHelper.getInstance(mContext);

        hasGesturePassword = !TextUtils.isEmpty(spHelper.getString(Info.KEY_GESTURE_PASSWORD, ""));

        tvMessage = (TextView) findViewById(R.id.tv_message);

        initPassword();
    }

    /**
     * Button按钮的点击事件
     * */
    public void btnReset(View view) {

        spHelper.putString(Info.KEY_GESTURE_PASSWORD, "");
    }

    /**
     * 手势密码相关监听设置
     * */
    private void initPassword(){

        mPwdView = (LocusPassWordView) this.findViewById(R.id.mPassWordView);
        mPwdView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String mPassword) {

                Md5Utils md5 = new Md5Utils();

                //设置过密码：进行验证
                if(hasGesturePassword) {

                    if (spHelper.getString(Info.KEY_GESTURE_PASSWORD, "")
                            .equals(md5.toMd5(mPassword, ""))) {//输入密码正确

                        tvMessage.setText("Password is right!");
                        mPwdView.clearPassword(400);
                    } else {//输入密码不正确

                        tvMessage.setText("Password is error!");
                        mPwdView.markError();
                    }
                } else {

                    if (isFirstPassword) {//第一次设置密码

                        isFirstPassword = Boolean.FALSE;
                        firstPassword = mPassword;
                        mPwdView.clearPassword();
                        tvMessage.setText("Please reDraw!");
                    } else {//第二次设置

                        if (firstPassword.equals(mPassword)) {//密码一致

                            tvMessage.setText("Password set complete!");
                            spHelper.putString(Info.KEY_GESTURE_PASSWORD, md5.toMd5(mPassword, ""));
                            mPwdView.clearPassword(600);
                            finish();
                        } else {//密码不一致

                            tvMessage.setText("Password is not same!");
                            mPwdView.clearPassword(400);
                        }

                    }
                }

            }
        });
    }
}
