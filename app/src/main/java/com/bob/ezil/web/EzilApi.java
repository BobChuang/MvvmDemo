package com.bob.ezil.web;

import android.content.Context;

import com.bob.common.base.web.HttpSubscriber;
import com.bob.common.base.web.OnResponseListener;
import com.bob.common.retrofit.RetrofitFactory;
import com.bob.ezil.entity.AccountBalance;
import com.bob.ezil.entity.CoinMining;
import com.bob.ezil.entity.CoinPrice;
import com.bob.ezil.entity.Friend;
import com.bob.ezil.entity.Reported;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class EzilApi {

    private static final IEzilApi stats = RetrofitFactory.create("https://stats.ezil.me", IEzilApi.class);
    private static final IEzilApi billing = RetrofitFactory.create("https://billing.ezil.me", IEzilApi.class);

    /**
     * 获取算力
     *
     * @param context
     * @param ethAddress
     * @param zilAddress
     * @param listener
     */
    public static void getReported(Context context, String ethAddress, String zilAddress, EzilOnResponseListener<Reported> listener) {
        stats.getReported(ethAddress, zilAddress)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EzilSubscriber<>(listener, Reported.class));
    }

    /**
     * 获取预估挖币
     *
     * @param context
     * @param ethAddress
     * @param zilAddress
     * @param listener
     */
    public static void getCoinMining(Context context, String ethAddress, String zilAddress, EzilOnResponseListener<CoinMining> listener) {
        billing.getCoinMining(ethAddress, zilAddress)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EzilSubscriber<>(listener, CoinMining.class));
    }

    /**
     * 获取当前账号价值
     *
     * @param context
     * @param ethAddress
     * @param zilAddress
     * @param listener
     */
    public static void getAccountBalance(Context context, String ethAddress, String zilAddress, EzilOnResponseListener<AccountBalance> listener) {
        billing.getAccountBalance(ethAddress, zilAddress)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EzilSubscriber<>(listener, AccountBalance.class));
    }

    /**
     * 获取币价
     *
     * @param context
     * @param listener
     */
    public static void getCoinPrice(Context context, EzilOnResponseListener<CoinPrice> listener) {
        billing.getCoinPrice()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EzilSubscriber<>(listener, CoinPrice.class));
    }



}
