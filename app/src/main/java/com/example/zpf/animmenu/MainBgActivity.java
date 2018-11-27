package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;

import customview.MainBgView;
import utils.SPKey;
import utils.SharedPreferencesHelper;

public class MainBgActivity extends BaseActivity implements View.OnClickListener {

    private MainBgView mMainBgView;
    private TextInputEditText mEditTextUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bg);
        initEvent();
    }

    private void initEvent() {

        mMainBgView = findViewById(R.id.mainBgView);
        mEditTextUrl = findViewById(R.id.etMainPicUrl);

        findViewById(R.id.btnSaveMainPicUrl).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSaveMainPicUrl:

                if (TextUtils.isEmpty(mEditTextUrl.getText())) {

                    Snackbar.make(mMainBgView, R.string.inputMainPicUrl, BaseTransientBottomBar.LENGTH_SHORT).show();
                } else {

                    SharedPreferencesHelper.getInstance(this).putString(SPKey.getInstance().MAIN_BG_URL, mEditTextUrl.getText().toString());
                }
                break;
        }
    }
}
