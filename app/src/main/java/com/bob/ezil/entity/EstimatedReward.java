package com.bob.ezil.entity;

import android.annotation.SuppressLint;

/**
 * Created by BobCheung on 7/1/21 01:32
 */
public class EstimatedReward {
    private double ethCoinPrice;
    private double zilCoinPrice;
    private double ethDayReward;
    private double ethWeekReward;
    private double ethMonthReward;
    private double zilDayReward;
    private double zilWeekReward;
    private double zilMonthReward;
    private double ethTotalRevenue;
    private double zilTotalRevenue;
    private double ethRemainingTime;
    private double zilRemainingTime;

    public double getEthCoinPrice() {
        return ethCoinPrice;
    }

    @SuppressLint("DefaultLocale")
    public String getEthCoinPriceStr() {
        return "$" + String.format("%.2f", ethCoinPrice);
    }

    public void setEthCoinPrice(double ethCoinPrice) {
        this.ethCoinPrice = ethCoinPrice;
    }

    public double getZilCoinPrice() {
        return zilCoinPrice;
    }

    @SuppressLint("DefaultLocale")
    public String getZilCoinPriceStr() {
        return "$" + String.format("%.5f", zilCoinPrice);
    }

    public void setZilCoinPrice(double zilCoinPrice) {
        this.zilCoinPrice = zilCoinPrice;
    }

    public double getEthDayReward() {
        return ethDayReward;
    }

    @SuppressLint("DefaultLocale")
    public String getEthDayRewardStr() {
        return "$" + String.format("%.2f", ethDayReward);
    }

    public void setEthDayReward(double ethDayReward) {
        this.ethDayReward = ethDayReward;
    }

    public double getEthWeekReward() {
        return ethWeekReward;
    }

    @SuppressLint("DefaultLocale")
    public String getEthWeekRewardStr() {
        return "$" + String.format("%.2f", ethWeekReward);
    }

    public void setEthWeekReward(double ethWeekReward) {
        this.ethWeekReward = ethWeekReward;
    }

    public double getEthMonthReward() {
        return ethMonthReward;
    }

    @SuppressLint("DefaultLocale")
    public String getEthMonthRewardStr() {
        return "$" + String.format("%.2f", ethMonthReward);
    }

    public void setEthMonthReward(double ethMonthReward) {
        this.ethMonthReward = ethMonthReward;
    }

    public double getZilDayReward() {
        return zilDayReward;
    }

    @SuppressLint("DefaultLocale")
    public String getZilDayRewardStr() {
        return "$" + String.format("%.2f", zilDayReward);
    }

    public void setZilDayReward(double zilDayReward) {
        this.zilDayReward = zilDayReward;
    }

    public double getZilWeekReward() {
        return zilWeekReward;
    }

    @SuppressLint("DefaultLocale")
    public String getZilWeekRewardStr() {
        return "$" + String.format("%.2f", zilWeekReward);
    }

    public void setZilWeekReward(double zilWeekReward) {
        this.zilWeekReward = zilWeekReward;
    }

    public double getZilMonthReward() {
        return zilMonthReward;
    }

    @SuppressLint("DefaultLocale")
    public String getZilMonthRewardStr() {
        return "$" + String.format("%.2f", zilMonthReward);
    }

    public void setZilMonthReward(double zilMonthReward) {
        this.zilMonthReward = zilMonthReward;
    }

    public double getEthTotalRevenue() {
        return ethTotalRevenue;
    }

    @SuppressLint("DefaultLocale")
    public String getEthTotalRevenueStr() {
        return "$" + String.format("%.2f", ethTotalRevenue);
    }

    public void setEthTotalRevenue(double ethTotalRevenue) {
        this.ethTotalRevenue = ethTotalRevenue;
    }

    public double getZilTotalRevenue() {
        return zilTotalRevenue;
    }

    @SuppressLint("DefaultLocale")
    public String getZilTotalRevenueStr() {
        return "$" + String.format("%.2f", zilTotalRevenue);
    }

    public void setZilTotalRevenue(double zilTotalRevenue) {
        this.zilTotalRevenue = zilTotalRevenue;
    }

    public double getEthRemainingTime() {
        return ethRemainingTime;
    }

    @SuppressLint("DefaultLocale")
    public String getEthRemainingTimeStr() {
        return String.format("%.1f", ethRemainingTime) + " Days";
    }

    public void setEthRemainingTime(double ethRemainingTime) {
        this.ethRemainingTime = ethRemainingTime;
    }

    public double getZilRemainingTime() {
        return zilRemainingTime;
    }

    @SuppressLint("DefaultLocale")
    public String getZilRemainingTimeStr() {
        return String.format("%.1f", zilRemainingTime) + " Days";
    }

    public void setZilRemainingTime(double zilRemainingTime) {
        this.zilRemainingTime = zilRemainingTime;
    }
}
