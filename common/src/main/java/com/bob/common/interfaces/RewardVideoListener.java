package com.bob.common.interfaces;

/**
 * @author marvin
 */
public interface RewardVideoListener extends AdsBaseListener {
    /**
     * 视频加载
     * @param isLoad
     */
    void onRewardedVideoAdLoaded(boolean isLoad);

    /**
     * 视频点击
     */
    void onRewardedVideoAdOpened();

    /**
     * 视频开始播放
     */
    void onRewardedVideoStarted();

    /**
     * 视频关闭
     */
    void onRewardedVideoAdClosed();

    /**
     * 视频加载失败
     */
    void onRewardedVideoAdFailedToLoad();

    /***
     * 视频播放完成
     */
    void onRewardedVideoCompleted();
}
