package com.example.zpf.animmenu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineBackgroundSpan;
import android.widget.CalendarView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.xutils.x;

import java.util.Calendar;

import static org.xutils.common.util.DensityUtil.dip2px;

public class CalendarViewActivity extends AppCompatActivity {

    private CalendarDay dayLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        x.Ext.init(getApplication());

        initView();
    }

    private void initView() {

        initSystemCalendarView();

        initMcv();
    }

    private void initSystemCalendarView() {

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        assert calendarView != null;

        calendarView.setWeekDayTextAppearance(R.style.CalendarViewWeekDayTextAppearance);
        calendarView.setWeekDayTextAppearance(R.style.CalendarViewDateTextAppearance);

        //设置第一列开始显示的星期
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        //设定选定的日期为当前日的前两天
        long lTwoDaysAgo = System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 2);
        calendarView.setDate(lTwoDaysAgo);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Toast.makeText(CalendarViewActivity.this, year + "年" + (month + 1) + "月" + dayOfMonth + "日",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initMcv() {

        MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.mcv);
        if (mcv == null)
            return;
        mcv.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                StringBuffer buffer = new StringBuffer();
                int yearOne = day.getYear();
                int monthOne = day.getMonth() + 1;
                buffer.append(yearOne).append("年").append(monthOne).append("月");
                return buffer;
            }
        });

        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                widget.setSelectedDate(date);
            }
        });

        mcv.addDecorators(new HighlightWeekendsDecorator(),
                new TodayDecorator());

    }

    /**
     * 改周末日期添加特殊颜色
     */
    public class HighlightWeekendsDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(CalendarViewActivity.this,
                    R.color.history_calendar_text_color_gray)));
        }
    }

    /**
     * 给日历当前增加圆圈背景修饰
     */
    private class TodayDecorator implements DayViewDecorator {

        private CalendarDay date;

        TodayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }


        @Override
        public void decorate(DayViewFacade view) {

            Drawable drawable = ContextCompat.getDrawable(CalendarViewActivity.this,
                    R.drawable.shape_calendar_today_ring);
            view.setBackgroundDrawable(drawable);
        }
    }

    private class RingSpan implements LineBackgroundSpan {
        @Override
        public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline,
                                   int bottom, CharSequence text, int start, int end, int lnum) {
            Paint paint = new Paint();
            paint.setAntiAlias(true); //消除锯齿
            paint.setStyle(Paint.Style.STROKE);//绘制空心圆或 空心矩形
            int ringWidth = dip2px(2);//圆环宽度
            //绘制圆环
            paint.setColor(Color.parseColor("#E94B4B"));
            paint.setStrokeWidth(ringWidth);
            c.drawCircle((right - left) / 2, (bottom - top) / 2, dip2px(24), paint);
        }
    }

    private class CircleBackGroundSpan implements LineBackgroundSpan {
        @Override
        public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline,
                                   int bottom, CharSequence text, int start, int end, int lnum) {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#E94B4B"));
            c.drawCircle((right - left) / 2, (bottom - top) / 2, dip2px(24), paint);
        }
    }

}
