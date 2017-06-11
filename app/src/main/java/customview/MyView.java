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
        paint.setStrokeWidth(32);
        paint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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

    public void reset(){

        mPath.reset();
        invalidate();
    }
}
