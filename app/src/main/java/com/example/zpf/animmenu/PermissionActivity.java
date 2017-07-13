package com.example.zpf.animmenu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PermissionActivity extends BaseActivity implements View.OnClickListener {

    private static final String LOG_TAG = "====";

    /**
     * 调用系统弹窗的权限
     */
    private static final int REQUEST_CODE_SYSTEM_ALERT = 1;
    /**
     * 调用更改系统设置的权限
     */
    private static final int REQUEST_CODE_WRITE_SYSTEM_SETTING = 2;
    /**
     * 调用拍照的权限
     */
    private static final int REQUEST_CODE_CAMERA = 3;
    /**
     * 发送短信的权限
     */
    private static final int REQUEST_SEND_SMS = 0x004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        initView();
    }

    private void initView() {

        LinearLayout layoutBtn = (LinearLayout) findViewById(R.id.layout_btn);
        int childCount = layoutBtn.getChildCount();

        for (int i = 0; i < childCount; i++) {

            layoutBtn.getChildAt(i).setOnClickListener(this);
        }
    }


    /**
     * Button 按钮的点击事件
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //获取系统弹窗的权限
            case R.id.btn_one:

                requestPermission(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, REQUEST_CODE_SYSTEM_ALERT);
                break;

            //更改系统设置的权限
            case R.id.btn_two:

                requestPermission(Settings.ACTION_MANAGE_WRITE_SETTINGS, REQUEST_CODE_WRITE_SYSTEM_SETTING);
                break;

            case R.id.btn_three:

                requestPermission(Manifest.permission.CAMERA, REQUEST_CODE_CAMERA);
                break;

            case R.id.btn_four:

                permissionSendMsg();
                break;

            default:
                break;
        }
    }

    /**
     * 请求权限调用的方法
     *
     * @param action      要请求的权限
     * @param requestCode 请求码
     */
    private void requestPermission(String action, int requestCode) {

        if (Build.VERSION.SDK_INT >= 23) {

            Intent intent = new Intent(action);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, requestCode);
        } else
            Toast.makeText(this, "您已经具有此权限", Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取权限结果处理
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_CODE_SYSTEM_ALERT:

                if (Settings.canDrawOverlays(this)) {
                    Log.i(LOG_TAG, "onActivityResult granted");
                }
                break;

            case REQUEST_CODE_WRITE_SYSTEM_SETTING:

                if (Settings.System.canWrite(this))
                    Log.i(LOG_TAG, "received");
                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case REQUEST_SEND_SMS:

                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    showToast("获取到了发短信权限");
                else
                    showToast("短信权限被拒绝！");
                break;

            default:
                break;
        }
    }

    /**
     * 短信权限处理
     */
    private void permissionSendMsg() {

        int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionState == PackageManager.PERMISSION_GRANTED)
            showToast("有发短信权限");
        else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SEND_SMS);
        }
    }

}
