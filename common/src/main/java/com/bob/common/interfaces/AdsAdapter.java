package com.bob.common.interfaces;

import android.app.Activity;
import android.content.Context;

/**
 * Created by marvin on 2019/9/9
 * @author marvin
 */
public class AdsAdapter implements IAdsIronSourceListener, IAdsAdMobListener {

    @Override
    public void initAdMobAds(Context context,String adMobKey,String interstitialKey) {

    }

    @Override
    public void setMobRewardVideoListener(RewardVideoAdapter adapter) {

    }

    @Override
    public void setMobInterstitialListener(InterstitialAdapter adapter) {

    }

    @Override
    public boolean isMobRewardVideoLoad() {
        return false;
    }

    @Override
    public boolean isMobInterstitialLoad() {
        return false;
    }

    @Override
    public void showMobRewardedVideoAd() {

    }

    @Override
    public void showMobInterstitialAd() {

    }

    @Override
    public void loadMobRewardedVideoAd(String videoKey) {

    }

    @Override
    public void loadMobInterstitialAd() {

    }

    @Override
    public void onMobResume(Context context) {

    }

    @Override
    public void onMobPause(Context context) {

    }

    @Override
    public void onMobDestroy(Context context) {

    }

    @Override
    public void initIronSource(Activity activity, boolean isDebug, String appKey) {

    }

    @Override
    public void showIronRewardVideo(String placementName) {

    }

    @Override
    public void showIronInterstitialAd(String placementName) {

    }

    @Override
    public void showIronInterstitialAd() {

    }

    @Override
    public void showIronRewardVideo() {

    }

    @Override
    public void setIronRewardVideoListener(RewardVideoAdapter adapter) {

    }

    @Override
    public void setIronInterstitialListener(InterstitialAdapter listener) {

    }

    @Override
    public void onResume(Activity activity) {

    }

    @Override
    public void onPause(Activity activity) {

    }

    @Override
    public boolean isIronRewardVideoLoad() {
        return false;
    }

    @Override
    public boolean isIronInterstitialLoad() {
        return false;
    }

    @Override
    public void loadIronInterstitialAd() {

    }
}
