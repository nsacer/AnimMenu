package com.example.zpf.animmenu;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_video)
public class VideoActivity extends BaseActivity {

    @ViewInject(R.id.video_player)
    private StandardGSYVideoPlayer videoPlayer;

    //旋转工具类
    private OrientationUtils orientationUtils;

    private boolean isPause;
    private boolean isPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVideoPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {

        //返回键处理
        if (orientationUtils != null && StandardGSYVideoPlayer.backFromWindowFull(this)) {

            orientationUtils.backToProtVideo();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause)
            videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils);
    }

    private void initVideoPlayer() {

        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        orientationUtils.setEnable(false);

        String urlVideo = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";

        //设置封面
        ImageView ivCover = new ImageView(this);
        ivCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivCover.setImageResource(R.mipmap.bg_width);
        videoPlayer.setThumbImageView(ivCover);

        //title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orientationUtils != null && StandardGSYVideoPlayer.backFromWindowFull(VideoActivity.this))
                    orientationUtils.backToProtVideo();
                else
                    VideoActivity.this.finish();
            }
        });

        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orientationUtils.resolveByClick();
                videoPlayer.startWindowFullscreen(VideoActivity.this, true, true);
            }
        });

        //显示锁屏图标
        videoPlayer.setNeedLockFull(true);

        videoPlayer.setRotateViewAuto(true);
        videoPlayer.setLockLand(true);

        videoPlayer.setUp(urlVideo, true, "标题你知道的");


    }


}
