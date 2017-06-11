package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zpf.animmenu.R;

/**
 * Created by twx on 10/21/16.
 */
public class RedPackageView extends RelativeLayout {

    private CircleImageView headView;
    private TextView textView;
    private ImageView footView;


    public RedPackageView(Context context) {
        this(context, null);
    }

    public RedPackageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //在构造函数中将Xml中定义的布局解析出来。
        View view = LayoutInflater.from(context).inflate(R.layout.red_package_layout, this, true);

        textView = (TextView) view.findViewById(R.id.vote_text_view);
        headView = (CircleImageView) view.findViewById(R.id.header_image_view);
        footView = (ImageView) view.findViewById(R.id.footer_image_view);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RedPackageView);
        CharSequence text = a.getText(R.styleable.RedPackageView_android_text);
        if(text!=null) textView.setText(text);
        Drawable drawable = a.getDrawable(R.styleable.RedPackageView_android_src);
        if (drawable != null) {
            headView.setImageDrawable(drawable);
            footView.setImageDrawable(drawable);
        }
        a.recycle();

    }
    public void setImageResource(int resId) {
        headView.setImageResource(resId);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setNextImageResource(int resId) {
        footView.setImageResource(resId);
    }

    public TextView getTextView() {
        return textView;
    }
}
