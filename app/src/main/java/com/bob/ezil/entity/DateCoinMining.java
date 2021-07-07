package com.bob.ezil.entity;

import android.annotation.SuppressLint;

/**
 * Created by BobCheung on 7/1/21 00:45
 */
public class DateCoinMining {
    private double day;
    private double week;
    private double thirty_days;

    public double getDay() {
        return day;
    }

    @SuppressLint("DefaultLocale")
    public String getDayStr() {
        return String.format("%.7f", day);
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getWeek() {
        return week;
    }

    @SuppressLint("DefaultLocale")
    public String getWeekStr() {
        return String.format("%.7f", week);
    }

    public void setWeek(double week) {
        this.week = week;
    }

    public double getThirty_days() {
        return thirty_days;
    }

    @SuppressLint("DefaultLocale")
    public String getMonthStr() {
        return String.format("%.7f", thirty_days);
    }

    public void setThirty_days(double thirty_days) {
        this.thirty_days = thirty_days;
    }
}
