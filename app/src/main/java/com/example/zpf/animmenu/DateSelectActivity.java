package com.example.zpf.animmenu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;

public class DateSelectActivity extends AppCompatActivity {

    private Calendar calendar;
    private int year, month, day, hour, minute;

    /** NumberPicker相关 */
    private TextView tvNumberSelected;

    /** Chronometer相关 */
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);

        init();

    }

    private void init() {

        calendar = Calendar.getInstance();

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        tvNumberSelected = (TextView) findViewById(R.id.tv_number_selected);
        initNumberPicker(numberPicker);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        initChronometer(chronometer);

        initCalendar();
    }

    /** init numberPicker */
    private void initNumberPicker(NumberPicker numberPicker) {

        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(6);
        numberPicker.setValue(16);

        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {

                if(scrollState == SCROLL_STATE_TOUCH_SCROLL ||
                        scrollState == SCROLL_STATE_IDLE) {

                }
            }
        });

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                tvNumberSelected.setText("selected: " + String.valueOf(newVal));
            }
        });
    }

    /** init Chronometer */
    private void initChronometer(Chronometer chronometer) {

        chronometer.setFormat("计时器：(%s)");
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                if(SystemClock.elapsedRealtime() - chronometer.getBase() > 20*1000) {

                    chronometer.stop();
                }
            }
        });

    }

    private void initCalendar() {

        Button btnCalendar = (Button) findViewById(R.id.btn_calendar);
        assert btnCalendar != null;
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DateSelectActivity.this, CalendarViewActivity.class));
            }
        });
    }

    /** 创建DatePickerDialog */
    public void createDatePickerDialog(View view) {

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(DateSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                setTitle(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, year, month, day);

        dialog.show();
    }

    /** 创建TimerPickerDialog */
    public void createTimePickerDialog(View view) {

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        new TimePickerDialog(DateSelectActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setTitle(hourOfDay + ":" + minute);
            }
        }, hour, minute, true).show();
    }

    /** 倒计时的相关点击事件处理 */
    public void chronometerStart(View view) {

        chronometer.start();
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    public void chronometerBase(View view) {

        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    public void chronometerStop(View view) {

        chronometer.stop();
    }

}
