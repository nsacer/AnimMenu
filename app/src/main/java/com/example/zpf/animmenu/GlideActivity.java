package com.example.zpf.animmenu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class GlideActivity extends BaseActivity implements View.OnClickListener {

    private final String URL_PIC = "http://isujin.com/wp-content/uploads/2017/03/wallhaven-78430.jpg";
    private ImageView ivGlide;
    private TextView tvInfo;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_load_glide:
                loadImageThumbnailRequest();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        initView();
    }

    private void initView() {

        (findViewById(R.id.btn_load_glide)).setOnClickListener(this);

        ivGlide = (ImageView) findViewById(R.id.iv_glide);
        tvInfo = (TextView) findViewById(R.id.tv_info_glide);
    }

    private void loadImageThumbnailRequest() {
        // setup Glide request without the into() method
        RequestOptions options = new RequestOptions();
        options = options.error(R.mipmap.ic_header).error(R.mipmap.ic_header).centerInside();

        RequestBuilder<Drawable> builder = Glide.with(this).asDrawable().apply(options).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                int width = resource.getIntrinsicWidth();
                int height = resource.getIntrinsicHeight();

                tvInfo.setText("width: " + width + "_height: " + height);

                //根据图片实际宽高比例更改ImageView
                ViewGroup.LayoutParams ll = ivGlide.getLayoutParams();
                ll.width = ivGlide.getWidth();
                ll.height = ivGlide.getWidth() * height / width;
                ivGlide.setLayoutParams(ll);

                showToast("ok");
                return false;
            }
        });

        // pass the request as a a parameter to the thumbnail request
        builder.load(URL_PIC)
                .into(ivGlide);
    }
}
