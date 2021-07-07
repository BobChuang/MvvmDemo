package com.bob.ezil.view.fragment.home;

import android.content.Context;

import com.bob.common.interfaces.OnDataListener;
import com.bob.ezil.entity.AccountBalance;
import com.bob.ezil.entity.CoinMining;
import com.bob.ezil.entity.CoinPrice;
import com.bob.ezil.entity.Reported;
import com.bob.ezil.web.EzilApi;
import com.bob.ezil.web.EzilOnResponseListener;

/**
 * Created by BobCheung on 6/30/21 00:49
 */
public class HomeModel {

    /**
     * 获取算力
     * @param context
     */
    public void getReward(Context context, OnDataListener<Reported> listener) {
        EzilApi.getReported(context, "0x426a77C7F2d74331e328B53281234fB6803D18F7", "zil1d5x96nvdl6fy2l3yk92uppj3tle6us73apvluc", new EzilOnResponseListener<Reported>() {
            @Override
            public void onSuccess(Reported data) {
                listener.onSuccess(data);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onServerError(int error) {

            }
        });
    }

    /**
     * 获取预估挖矿
     * @param context
     */
    public void getCoinMining(Context context, OnDataListener<CoinMining> listener) {
        EzilApi.getCoinMining(context, "0x426a77C7F2d74331e328B53281234fB6803D18F7", "zil1d5x96nvdl6fy2l3yk92uppj3tle6us73apvluc", new EzilOnResponseListener<CoinMining>() {
            @Override
            public void onSuccess(CoinMining data) {
                listener.onSuccess(data);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onServerError(int error) {

            }
        });
    }

    /**
     * 获取预估挖矿
     * @param context
     */
    public void getAccountBalance(Context context, OnDataListener<AccountBalance> listener) {
        EzilApi.getAccountBalance(context, "0x426a77C7F2d74331e328B53281234fB6803D18F7", "zil1d5x96nvdl6fy2l3yk92uppj3tle6us73apvluc", new EzilOnResponseListener<AccountBalance>() {
            @Override
            public void onSuccess(AccountBalance data) {
                listener.onSuccess(data);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onServerError(int error) {

            }
        });
    }

    /**
     * 获取币价
     * @param context
     */
    public void getCoinPrice(Context context, OnDataListener<CoinPrice> listener) {
        EzilApi.getCoinPrice(context, new EzilOnResponseListener<CoinPrice>() {
            @Override
            public void onSuccess(CoinPrice data) {
                listener.onSuccess(data);
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onServerError(int error) {

            }
        });
    }

}
