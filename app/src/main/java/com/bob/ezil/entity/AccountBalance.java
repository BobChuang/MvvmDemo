package com.bob.ezil.entity;

import android.annotation.SuppressLint;

/**
 * Created by BobCheung on 7/1/21 08:12
 */
public class AccountBalance {
    /**
     * {
     *     "eth":0.022986864652203277,
     *     "etc":0,
     *     "zil":26.062122880239,
     *     "eth_wallet":"0x426a77c7f2d74331e328b53281234fb6803d18f7",
     *     "zil_wallet":"zil1d5x96nvdl6fy2l3yk92uppj3tle6us73apvluc",
     *     "eth_min_payout":0.05,
     *     "etc_min_payout":0.1,
     *     "zil_min_payout":30,
     *     "eth_leftovers_withdrawal":true,
     *     "created_at":"2021-06-25T00:00:47Z",
     *     "promocode_cashback_share":0,
     *     "promocode_expires_at":0,
     *     "promocode_code":0
     * }
     */
    private double eth;
    private double etc;
    private double zil;
    private String eth_wallet;
    private String zil_wallet;
    private double eth_min_payout;
    private double etc_min_payout;
    private double zil_min_payout;
    private boolean eth_leftovers_withdrawal;
    private String created_at;
    private double promocode_cashback_share;

    public double getEth() {
        return eth;
    }

    @SuppressLint("DefaultLocale")
    public String getEthStr() {
        return String.format("%.7f", eth);
    }

    public void setEth(double eth) {
        this.eth = eth;
    }

    public double getEtc() {
        return etc;
    }

    @SuppressLint("DefaultLocale")
    public String getEtcStr() {
        return String.format("%.7f", etc);
    }

    public void setEtc(double etc) {
        this.etc = etc;
    }

    public double getZil() {
        return zil;
    }

    @SuppressLint("DefaultLocale")
    public String getZilStr() {
        return String.format("%.7f", zil);
    }

    public void setZil(double zil) {
        this.zil = zil;
    }

    public String getEth_wallet() {
        return eth_wallet;
    }

    public void setEth_wallet(String eth_wallet) {
        this.eth_wallet = eth_wallet;
    }

    public String getZil_wallet() {
        return zil_wallet;
    }

    public void setZil_wallet(String zil_wallet) {
        this.zil_wallet = zil_wallet;
    }

    public double getEth_min_payout() {
        return eth_min_payout;
    }

    public String getEthMinPayoutStr() {
        return String.valueOf(eth_min_payout);
    }

    public void setEth_min_payout(double eth_min_payout) {
        this.eth_min_payout = eth_min_payout;
    }

    public double getEtc_min_payout() {
        return etc_min_payout;
    }

    public String getEtcMinPayoutStr() {
        return String.valueOf(etc_min_payout);
    }

    public void setEtc_min_payout(double etc_min_payout) {
        this.etc_min_payout = etc_min_payout;
    }

    public double getZil_min_payout() {
        return zil_min_payout;
    }

    public String getZilMinPayoutStr() {
        return String.valueOf(zil_min_payout);
    }

    public void setZil_min_payout(double zil_min_payout) {
        this.zil_min_payout = zil_min_payout;
    }

    public boolean isEth_leftovers_withdrawal() {
        return eth_leftovers_withdrawal;
    }

    public void setEth_leftovers_withdrawal(boolean eth_leftovers_withdrawal) {
        this.eth_leftovers_withdrawal = eth_leftovers_withdrawal;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public double getPromocode_cashback_share() {
        return promocode_cashback_share;
    }

    public void setPromocode_cashback_share(double promocode_cashback_share) {
        this.promocode_cashback_share = promocode_cashback_share;
    }

}
