package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.zpf.animmenu.R;

/**
 * Created by zpf on 2016/7/28.
 * 根据手势绘制路径（贝塞尔曲线路径）
 */
public class MyView extends View {

    private Paint paint;
    private Path mPath;
    private float preX, preY;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mPath.moveTo(100, 300);
        //quadTo方法
//        mPath.quadTo(200,200,300,300);
//        mPath.quadTo(400,400,500,300);
        //rQuadTo方法
//        mPath.rQuadTo(100, -100, 200, 0);
//        mPath.rQuadTo(100, 100, 200, 0);

        canvas.drawPath(mPath, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            mPath.moveTo(event.getX(), event.getY());
            preX = event.getX();
            preY = event.getY();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            float endX = (preX + event.getX()) / 2;
            float endY = (preY + event.getY()) / 2;

            mPath.quadTo(preX, preY, endX, endY);

            preX = event.getX();
            preY = event.getY();

            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void reset() {

        mPath.reset();
        invalidate();
    }
}
