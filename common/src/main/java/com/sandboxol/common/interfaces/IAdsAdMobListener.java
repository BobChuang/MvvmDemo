package com.sandboxol.common.interfaces;

import android.content.Context;

/**
 * Created by marvin on 2019/9/10
 * @author marvin
 */
public interface IAdsAdMobListener {

    void initAdMobAds(Context context,String adMobKey,String interstitialKey);

    void setMobRewardVideoListener(RewardVideoAdapter listener);

    void setMobInterstitialListener(InterstitialAdapter listener);

    boolean isMobRewardVideoLoad();

    boolean isMobInterstitialLoad();

    void showMobRewardedVideoAd();

    void showMobInterstitialAd();

    void loadMobRewardedVideoAd(String videoKey);

    void loadMobInterstitialAd();

    void onMobResume(Context context);

    void onMobPause(Context context);

    void onMobDestroy(Context context);
}
