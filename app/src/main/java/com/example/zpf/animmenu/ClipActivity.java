package com.example.zpf.animmenu;

import java.io.File;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import customview.ClipImageLayout;
import utils.AnimMenuSetting;
import utils.ImageTools;

public class ClipActivity extends AppCompatActivity{

	private static final String IMAGE_CROP_SAVE_PATH = "/ClipHeadPhoto/cache";
	private static final String IMAGE_CROP_SAVE_NAME = "/aaaa.jpg";
	private ClipImageLayout mClipImageLayout;
	private ProgressDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState){
//		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clipimage);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
	}

    private void init(){

        initToolbar();

        showProgressDialog();

        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        mClipImageLayout.setBitmap(loadImg());
    }

    /** init Toolbar */
    private void initToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("移动和缩放");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipActivity.this.finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menu_crop_confirm:

                        completeClip();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_img, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** 显示加载Dialog */
	private void showProgressDialog(){

        loadingDialog=new ProgressDialog(this);
        loadingDialog.setTitle("请稍后...");
    }

    /** 获取传过来的Intent存储的信息 */
    private Bitmap loadImg(){

        Intent intent = getIntent();
        String path = intent.getStringExtra(AnimMenuSetting.IMAGE_SOURCE_PATH);
        if(TextUtils.isEmpty(path)||!(new File(path).exists())){
            Toast.makeText(this, "图片加载失败",Toast.LENGTH_SHORT).show();
            return null;
        }
        Bitmap bitmap= ImageTools.convertToBitmap(path, 600,600);
        if(bitmap==null){
            Toast.makeText(this, "图片加载失败",Toast.LENGTH_SHORT).show();
        }

        return bitmap;
    }

    /** “确定”按钮的点击事件 */
    private void completeClip(){

        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = mClipImageLayout.clip();
                String path = Environment.getExternalStorageDirectory().getPath() +
                        IMAGE_CROP_SAVE_PATH;

                if(ImageTools.savePhotoToSDCard(bitmap,path, IMAGE_CROP_SAVE_NAME)){

                    Intent intent = new Intent();
                    intent.putExtra(AnimMenuSetting.IMAGE_CROP_PATH,path + IMAGE_CROP_SAVE_NAME);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(ClipActivity.this, "图片保存失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                loadingDialog.dismiss();
            }
        }).start();
    }

}
