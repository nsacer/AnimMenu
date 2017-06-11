package com.example.zpf.animmenu;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.utils.FileUtils;
import utils.AnimMenuSetting;

public class PhotoSetActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 0X000;
    private static final int REQUEST_CAMERA = 0X001;
    private static final int REQUEST_IMAGE_CROP = 0X002;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    private RadioGroup rgSelectMode, rgShowMode;
    private EditText etSelectNum;
    private ImageView ivHeader;
    private PopupWindow popupWindow;
    private TextView tvPictureInfo;
    private ArrayList<String> imgList = new ArrayList<>();

    /** 拍照存储的图片文件 */
    private File mTmpFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_set);


        initView();

    }

    private void initView() {

        rgSelectMode = (RadioGroup) findViewById(R.id.rg_select);

        rgShowMode = (RadioGroup) findViewById(R.id.rg_show);

        etSelectNum = (EditText) findViewById(R.id.et_select_num);

        ivHeader = (ImageView) findViewById(R.id.iv_header);

        tvPictureInfo = (TextView) findViewById(R.id.tv_picture_info);

        initPopupWindow();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * PopupWindow的初始化设置
     */
    private void initPopupWindow() {

        popupWindow = new PopupWindow(
                LayoutInflater.from(this).inflate(R.layout.pop_select_photo, null, false),
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.AnimWindowChangeFade);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    /**
     * 弹出选取头像方式：拍照、相册
     */
    public void popSelectWin(View view) {

        popupWindow.showAtLocation((View) view.getParent(), Gravity.CENTER, 0, 0);
    }

    /**
     * 隐藏PopupWindow
     */
    public void popWinDismiss(View view) {

        popupWindow.dismiss();

    }

    /**
     * 调用系统相机拍照
     */
    public void goToTakePhoto(View view) {

        popupWindow.dismiss();
        showCameraAction();
    }

    /**
     * 打开相册
     */
    public void goToPicture(View view) {

        popupWindow.dismiss();
        pickImage();

    }

    /**
     * 取消选择
     */
    public void goToCancel(View view) {

        popupWindow.dismiss();
    }

    /**
     * 对于选取的图片进行处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            //拍完照片但是不OK时候删除缓存照片
            while (mTmpFile != null && mTmpFile.exists()){
                boolean success = mTmpFile.delete();
                if(success){
                    mTmpFile = null;
                }
            }
            return;
        }

        switch (requestCode) {

            //读取到了相册的照片
            case REQUEST_IMAGE:
                imgList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);

                StringBuilder sb = new StringBuilder();

                for (String sImg : imgList){

                    sb.append(sImg);
                    sb.append("\n");
                }

                tvPictureInfo.setText(sb.toString());
                goToCropActivity(imgList.get(0));
                break;

            //拍照成功之后
            case REQUEST_CAMERA:
                if (mTmpFile != null) {
                    // notify system the image has change
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(mTmpFile)));
                    goToCropActivity(mTmpFile.getAbsolutePath());
                }
                break;

            //裁剪图片之后
            case REQUEST_IMAGE_CROP:

                Bitmap bitmapCrop = getLocalBitmap(data.getStringExtra(AnimMenuSetting.IMAGE_CROP_PATH));
                ivHeader.setImageBitmap(bitmapCrop);
                tvPictureInfo.setText(data.getStringExtra(AnimMenuSetting.IMAGE_CROP_PATH));
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Open camera打开相机拍照
     */
    private void showCameraAction() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(me.nereo.multi_image_selector.R.string.mis_permission_rationale_write_storage),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        }else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(this.getPackageManager()) != null) {
                try {
                    mTmpFile = FileUtils.createTmpFile(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (mTmpFile != null && mTmpFile.exists()) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else {
                    Toast.makeText(this, me.nereo.multi_image_selector.R.string.mis_error_image_not_exist, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, me.nereo.multi_image_selector.R.string.mis_msg_no_camera, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** 从相册选取照片 */
    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        }else {
            boolean showCamera = rgShowMode.getCheckedRadioButtonId() == R.id.rb_show_camera;
            int maxNum = 9;

            if (!TextUtils.isEmpty(etSelectNum.getText())) {
                try {
                    maxNum = Integer.valueOf(etSelectNum.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
//            MultiImageSelector selector = MultiImageSelector.create(PhotoSetActivity.this);
            MultiImageSelector selector = MultiImageSelector.create();
            selector.showCamera(showCamera);
            selector.count(maxNum);
            if (rgSelectMode.getCheckedRadioButtonId() == R.id.rb_single) {
                selector.single();
            } else {
                selector.multi();
            }
            selector.origin(imgList);
            selector.start(PhotoSetActivity.this, REQUEST_IMAGE);
        }
    }

    /** 没有权限弹窗请求权限 */
    private void requestPermission(final String permission, String rationale, final int requestCode){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PhotoSetActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    /** 请求权限结果处理 */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //读取相册的权限
        if(requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImage();
            }
        }
        //拍照写入照片的权限
        else if(requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showCameraAction();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 根据图片地址获取Bitmap
     *
     * @param url:本地图片的地址
     * @return Bitmap:返回读取到的图片
     */
    public static Bitmap getLocalBitmap(String url) {

        if(TextUtils.isEmpty(url)){
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 跳转到图片裁剪界面 */
    private void goToCropActivity(String sSourcePath){

        if(TextUtils.isEmpty(sSourcePath))
            return;
        Intent intent2=new Intent(this, ClipActivity.class);
        intent2.putExtra(AnimMenuSetting.IMAGE_SOURCE_PATH, sSourcePath);
        startActivityForResult(intent2, REQUEST_IMAGE_CROP);
    }

    /** 返回按钮处理 */
    @Override
    public void onBackPressed() {
        if(popupWindow.isShowing()) {

            popupWindow.dismiss();
            return;
        }
        super.onBackPressed();
    }
}
