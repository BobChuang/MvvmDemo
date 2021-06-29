package com.sandboxol.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sandboxol.common.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class TimeCountDownView extends LinearLayout {
    private Context context;
    private CountDownTimer timer;

    //自定义属性
    private int text_size = 10;
    private String color_text; //指定的颜色在common的colors.xml中必须存在
    private String delimiter_1, delimiter_2;
    private boolean isShowUnit;

    private TextView firstTimeView, secondTimeView, thirdTimeView;
    private TextView firstDelimiterView, secondDelimiterView; //分隔符

    private int day, hour, minute, second;

    private boolean isRunning;

    public TimeCountDownView(Context context) {
        this(context, null);
    }

    public TimeCountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeCountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflate(context, R.layout.layout_timer, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.TimeCountDownView);
        text_size = typedArray.getInteger(R.styleable.TimeCountDownView_text_size, text_size);
        color_text = typedArray.getString(R.styleable.TimeCountDownView_text_color);
        delimiter_1 = typedArray.getString(R.styleable.TimeCountDownView_delimiter_1);
        if (TextUtils.isEmpty(delimiter_1)) {
            delimiter_1 = " ";
        }
        delimiter_2 = typedArray.getString(R.styleable.TimeCountDownView_delimiter_2);
        if (TextUtils.isEmpty(delimiter_2)) {
            delimiter_2 = " ";
        }
        isShowUnit = typedArray.getBoolean(R.styleable.TimeCountDownView_isShowUnit, false);
        typedArray.recycle();
        initView();
    }

    @Override
    protected void onDetachedFromWindow() {
        closeTimer();
        super.onDetachedFromWindow();
    }

    private void initView() {
        firstTimeView = (TextView) findViewById(R.id.tv_first);
        secondTimeView = (TextView) findViewById(R.id.tv_second);
        thirdTimeView = (TextView) findViewById(R.id.tv_third);
        firstDelimiterView = (TextView) findViewById(R.id.tv_delimiter_1);
        secondDelimiterView = (TextView) findViewById(R.id.tv_delimiter_2);

        try {
            firstTimeView.setTextColor(Color.parseColor(color_text));
            secondTimeView.setTextColor(Color.parseColor(color_text));
            thirdTimeView.setTextColor(Color.parseColor(color_text));
            firstDelimiterView.setTextColor(Color.parseColor(color_text));
            secondDelimiterView.setTextColor(Color.parseColor(color_text));
        } catch (Exception e) {
            e.printStackTrace();
        }

        firstTimeView.setTextSize(text_size);
        secondTimeView.setTextSize(text_size);
        thirdTimeView.setTextSize(text_size);
        firstDelimiterView.setTextSize(text_size);
        secondDelimiterView.setTextSize(text_size);

        firstDelimiterView.setText(delimiter_1);
        secondDelimiterView.setText(delimiter_2);
    }

    public void setTime(long milliSeconds, @Unit int firstUnit, @Unit int secondUnit, @Unit int thirdUnit, OnTimeOver listener) {
        if (!isRunning) {
            isRunning = true;
            long countDownInterval = 1000;
            if (thirdUnit == UNIT_MINUTE) {
                countDownInterval = 60 * countDownInterval;
            }
            timer = new CountDownTimer(milliSeconds, countDownInterval) {
                @Override
                public void onTick(long l) {
                    try {
                        timeFormat((int) (l / 1000));
                        firstTimeView.setText(forMatString(firstUnit));
                        secondTimeView.setText(forMatString(secondUnit));
                        thirdTimeView.setText(forMatString(thirdUnit));
                    } catch (Exception e) {
                        e.printStackTrace();
                        cancel();
                        isRunning = false;
                    }
                }

                @Override
                public void onFinish() {
                    if (listener != null) {
                        listener.onFinish();
                    }
                    cancel();
                    isRunning = false;
                }
            }.start();
        }
    }


    private void timeFormat(int seconds) {
        day = seconds / (60 * 60 * 24);
        hour = (seconds % (60 * 60 * 24)) / (60 * 60);
        minute = (seconds % (60 * 60)) / 60;
        second = seconds % 60;
    }

    public String forMatString(@Unit int unit) {
        int time = 0;
        switch (unit) {
            case UNIT_DAY:
                time = day;
                break;
            case UNIT_HOUR:
                time = hour;
                break;
            case UNIT_MINUTE:
                time = minute;
                break;
            case UNIT_SECOND:
                time = second;
                break;
            default:
                break;
        }
        StringBuilder s = new StringBuilder();
        if (unit != UNIT_DAY) {
            if (String.valueOf(time).length() == 1) {
                s.append("0" + time);
            } else {
                s.append(time);
            }
        } else {
            s.append(time);
        }
        if (isShowUnit) {
            switch (unit) {
                case UNIT_DAY:
                    s.append(context.getString(R.string.time_count_down_unit_day));
                    break;
                case UNIT_HOUR:
                    s.append(context.getString(R.string.time_count_down_unit_hour));
                    break;
                case UNIT_MINUTE:
                    s.append(context.getString(R.string.time_count_down_unit_minute));
                    break;
                case UNIT_SECOND:
                    s.append(context.getString(R.string.time_count_down_unit_second));
                    break;
                default:
                    break;
            }
        }
        return s.toString();
    }

    public void closeTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    public interface OnTimeOver {
        void onFinish();
    }

    public static final int UNIT_DAY = 100;
    public static final int UNIT_HOUR = 101;
    public static final int UNIT_MINUTE = 102;
    public static final int UNIT_SECOND = 103;

    @IntDef(value = {
            UNIT_DAY,
            UNIT_HOUR,
            UNIT_MINUTE,
            UNIT_SECOND
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
