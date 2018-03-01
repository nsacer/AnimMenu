package com.example.zpf.animmenu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.xutils.x;

import top.wefor.circularanim.CircularAnim;
import utils.DisplayUtil;

public class BaseActivity extends AppCompatActivity {

    protected Handler mhandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    /**
     * 动画跳转页面
     */
    protected void openActivity(final Class<?> clazz, View view) {

        CircularAnim.fullActivity(this, view)
                .colorOrImageRes(R.color.colorAccent)
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {

                        startActivity(new Intent(BaseActivity.this, clazz));
                    }
                });
    }

    protected void openAct(Class<?> clazz) {

        startActivity(new Intent(this, clazz),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    protected void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resId) {

        Toast.makeText(this, getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 传入dp值得到px
     */
    protected int getDp(int dp) {

        return DisplayUtil.dip2px(this, dp);
    }

    protected void Logi(String content) {

        Log.i("====", content);
    }

}
