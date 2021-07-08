package com.bob.ezil.entity;

import android.annotation.SuppressLint;

import java.util.Map;

/**
 * Created by BobCheung on 6/30/21 00:56
 */
public class Reported {
    /*
       "eth":{
            "current_hashrate":437848055,
            "average_hashrate":428303683,
            "last_share_timestamp":1624985532,
            "last_share_coin":"eth",
            "wallet":"0x426a77c7f2d74331e328b53281234fb6803d18f7.zil1d5x96nvdl6fy2l3yk92uppj3tle6us73apvluc",
            "reported_hashrate":433319823
        },
        "etc":{
            "current_hashrate":0,
            "average_hashrate":0,
            "last_share_timestamp":0,
            "last_share_coin":"etc",
            "wallet":"0x426a77c7f2d74331e328b53281234fb6803d18f7.zil1d5x96nvdl6fy2l3yk92uppj3tle6us73apvluc",
            "reported_hashrate":0
        },
        "last_share_coin":"eth",
        "current_hashrate":437848055,
        "average_hashrate":428303683,
        "last_share_timestamp":1624985532,
        "reported_hashrate":433319823
     */
    private Map<String, Object> eth;
    private Map<String, String> etc;
    private String last_share_coin;
    private String wallet;
    private long current_hashrate;
    private long average_hashrate;
    private long last_share_timestamp;
    private long reported_hashrate;

    public String getLast_share_coin() {
        return last_share_coin;
    }

    public void setLast_share_coin(String last_share_coin) {
        this.last_share_coin = last_share_coin;
    }

    @SuppressLint("DefaultLocale")
    public String getCurrent_hashrate() {
        return String.format("%.3f", Double.parseDouble(String.valueOf(current_hashrate)) / 1000000);
    }

    public void setCurrent_hashrate(long current_hashrate) {
        this.current_hashrate = current_hashrate;
    }

    @SuppressLint("DefaultLocale")
    public String getAverage_hashrate() {
        return String.format("%.3f", Double.parseDouble(String.valueOf(average_hashrate)) / 1000000);
    }

    public void setAverage_hashrate(long average_hashrate) {
        this.average_hashrate = average_hashrate;
    }

    public long getLast_share_timestamp() {
        return last_share_timestamp;
    }

    public void setLast_share_timestamp(long last_share_timestamp) {
        this.last_share_timestamp = last_share_timestamp;
    }

    public long getReportedHashrate() {
        return reported_hashrate;
    }

    @SuppressLint("DefaultLocale")
    public String getReported_hashrate() {
        return String.format("%.3f", Double.parseDouble(String.valueOf(reported_hashrate)) / 1000000);
    }

    public void setReported_hashrate(long reported_hashrate) {
        this.reported_hashrate = reported_hashrate;
    }

    public String getCurrentHashRateType() {
        if (current_hashrate < 1000000000L) {
            return "MH/s";
        } else if (current_hashrate < 1000000000000L) {
            return "GH/s";
        } else {
            return "TH/s";
        }
    }

    public String getAverageHashRateType() {
        if (average_hashrate < 1000000000L) {
            return "MH/s";
        } else if (average_hashrate < 1000000000000L) {
            return "GH/s";
        } else {
            return "TH/s";
        }
    }

    public String getReportedHashRateType() {
        if (reported_hashrate < 1000000000L) {
            return "MH/s";
        } else if (reported_hashrate < 1000000000000L) {
            return "GH/s";
        } else {
            return "TH/s";
        }
    }
}
