package com.bob.ezil.entity;

import android.annotation.SuppressLint;

/**
 * Created by BobCheung on 6/30/21 23:42
 */
public class CoinMining {
    /**
     * {
     * "eth":{
     * "day":0.012302284784121683,
     * "week":0.08611599348885178,
     * "thirty_days":0.3690685435236505
     * },
     * "etc":{
     * "day":0,
     * "week":0,
     * "thirty_days":0
     * },
     * "zil_eth":{
     * "day":8.594677811616,
     * "week":60.162744681312,
     * "thirty_days":257.84033434848
     * },
     * "zil_etc":{
     * "day":0,
     * "week":0,
     * "thirty_days":0
     * },
     * "eth_hashrate":412177671,
     * "etc_hashrate":0
     * }
     */
    private DateCoinMining etc;
    private DateCoinMining eth;
    private DateCoinMining zil_eth;
    private DateCoinMining zil_etc;
    private long eth_hashrate;
    private long etc_hashrate;

    public DateCoinMining getEtc() {
        return etc;
    }

    public void setEtc(DateCoinMining etc) {
        this.etc = etc;
    }

    public DateCoinMining getEth() {
        return eth;
    }

    public void setEth(DateCoinMining eth) {
        this.eth = eth;
    }

    public DateCoinMining getZil_eth() {
        return zil_eth;
    }

    public void setZil_eth(DateCoinMining zil_eth) {
        this.zil_eth = zil_eth;
    }

    public DateCoinMining getZil_etc() {
        return zil_etc;
    }

    public void setZil_etc(DateCoinMining zil_etc) {
        this.zil_etc = zil_etc;
    }

    public long getEth_hashrate() {
        return eth_hashrate;
    }

    @SuppressLint("DefaultLocale")
    public String getEthHashrate() {
        return String.format("%.3f", Double.parseDouble(String.valueOf(eth_hashrate)) / 1000000);
    }

    public void setEth_hashrate(long eth_hashrate) {
        this.eth_hashrate = eth_hashrate;
    }

    public long getEtc_hashrate() {
        return etc_hashrate;
    }

    public void setEtc_hashrate(long etc_hashrate) {
        this.etc_hashrate = etc_hashrate;
    }

    public String getEthHashRateType() {
        if (eth_hashrate < 1000000000L) {
            return "MH/s";
        } else if (eth_hashrate < 1000000000000L) {
            return "GH/s";
        } else {
            return "TH/s";
        }
    }
}
