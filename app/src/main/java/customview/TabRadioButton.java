package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.example.zpf.animmenu.R;

/**
 * Created by zpf on 2016/10/27.
 * 自定义的有图文的RadioButton
 */
public class TabRadioButton extends RadioButton {

    private Drawable drawableLeft, drawableTop, drawableRight, drawableBottom;
    private int mLeftWith, mLeftHeight, mTopWith, mTopHeight, mRightWith, mRightHeight,
            mBottomWith, mBottomHeight;

    public TabRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefineAttr(context, attrs);
    }

    /**
     * 初始化自定义属性
     */
    private void initDefineAttr(Context context, AttributeSet attrs) {

        if (attrs != null) {

            float scale = context.getResources().getDisplayMetrics().density;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabRadioButton);
            int n = a.getIndexCount();

            for (int i = 0; i < n; i++) {

                int attr = a.getIndex(i);

                switch (attr) {
                    case R.styleable.TabRadioButton_zhao_drawableBottom:
                        drawableBottom = a.getDrawable(attr);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableTop:
                        drawableTop = a.getDrawable(attr);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableLeft:
                        drawableLeft = a.getDrawable(attr);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableRight:
                        drawableRight = a.getDrawable(attr);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableTopWith:
                        mTopWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableTopHeight:
                        mTopHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableBottomWith:
                        mBottomWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableBottomHeight:
                        mBottomHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableRightWith:
                        mRightWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableRightHeight:
                        mRightHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableLeftWith:
                        mLeftWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.TabRadioButton_zhao_drawableLeftHeight:
                        mLeftHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    default:
                        break;
                }
            }

            a.recycle();

            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0,
                    mLeftWith <= 0 ? left.getIntrinsicWidth() : mLeftWith,
                    mLeftHeight <= 0 ? left.getMinimumHeight() : mLeftHeight);
        }
        if (right != null) {
            right.setBounds(0, 0,
                    mRightWith <= 0 ? right.getIntrinsicWidth() : mRightWith,
                    mRightHeight <= 0 ? right.getMinimumHeight() : mRightHeight);
        }
        if (top != null) {
            top.setBounds(0, 0,
                    mTopWith <= 0 ? top.getIntrinsicWidth() : mTopWith,
                    mTopHeight <= 0 ? top.getMinimumHeight() : mTopHeight);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0,
                    mBottomWith <= 0 ? bottom.getIntrinsicWidth() : mBottomWith,
                    mBottomHeight <= 0 ? bottom.getMinimumHeight() : mBottomHeight);
        }

        setCompoundDrawables(left, top, right, bottom);
    }
}
