package com.example.zpf.animmenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_glide)
public class GlideActivity extends BaseActivity {

    private final String URL_PIC = "http://isujin.com/wp-content/uploads/2017/03/wallhaven-78430.jpg";

    @ViewInject(R.id.iv_glide)
    private ImageView ivGlide;

    @ViewInject(R.id.tv_info_glide)
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(R.id.btn_load_glide)
    private void LoadImage(View view) {

        loadImageThumbnailRequest();
    }

    private void loadImageThumbnailRequest() {
        // setup Glide request without the into() method
        DrawableRequestBuilder<String> thumbnailRequest = Glide
                .with(this)
                .load(URL_PIC)
                .thumbnail(0.5f)
                .listener(requestListener);

        // pass the request as a a parameter to the thumbnail request
        Glide.with(this)
                .load(URL_PIC)
                .thumbnail(thumbnailRequest)
                .into(ivGlide);
    }

    private RequestListener<String, GlideDrawable> requestListener =
            new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model,
                                   Target<GlideDrawable> target, boolean isFirstResource) {
            // todo log exception

            // important to return false so the error placeholder can be placed
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource,
                                       String model, Target<GlideDrawable> target,
                                       boolean isFromMemoryCache, boolean isFirstResource) {

            int width = resource.getIntrinsicWidth();
            int height = resource.getIntrinsicHeight();
            tvInfo.setText(width + "---" + height);

            ViewGroup.LayoutParams ll = ivGlide.getLayoutParams();
//            ll.height = ivGlide.getWidth() / (resource.getIntrinsicWidth() / resource.getIntrinsicHeight());
            ll.width = ivGlide.getWidth();
            ll.height = ivGlide.getWidth() * height / width ;
            ivGlide.setLayoutParams(ll);

            showToast("ok");

            return false;
        }
    };
}
