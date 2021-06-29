package com.bob.common.interfaces;

import android.app.Activity;

/**
 * Created by marvin on 2019/9/6
 * @author marvin
 */
public interface IAdsIronSourceListener {

    void initIronSource(Activity activity, boolean isDebug, String appKey);

    void setIronRewardVideoListener(RewardVideoAdapter adapter);

    void setIronInterstitialListener(InterstitialAdapter listener);

    boolean isIronRewardVideoLoad();

    boolean isIronInterstitialLoad();

    void showIronRewardVideo(String placementName);

    void showIronInterstitialAd(String placementName);

    void showIronRewardVideo();

    void showIronInterstitialAd();

    void loadIronInterstitialAd();

    void onResume(Activity activity);

    void onPause(Activity activity);
}
