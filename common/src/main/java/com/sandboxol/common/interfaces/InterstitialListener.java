package com.sandboxol.common.interfaces;

/**
 * Created by marvin on 2019/9/10
 * @author marvin
 */
public interface InterstitialListener extends AdsBaseListener{
    void onAdClosed();

    void onAdFailedToLoad(int var1);

    void onAdLeftApplication();

    void onAdOpened();

    void onAdLoaded();

    void onAdClicked();

    void onAdImpression();
}
