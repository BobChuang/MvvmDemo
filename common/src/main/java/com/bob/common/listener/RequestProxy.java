package com.bob.common.listener;

import android.util.Log;

/**
 * Created by Bob on 2020/02/26.
 */
public class RequestProxy {

    /**
     * 上次点击时间
     */
    private long lastClick;

    /**
     * 时间（单位：ms）
     */
    private long times = 1000;

    private IRequest mIRequest;

    public RequestProxy(IRequest request) {
        mIRequest = request;
        repeatRequest();
    }

    private void repeatRequest() {
        Log.e("clickPay", "click");
        if (System.currentTimeMillis() - lastClick >= times) {
            lastClick = System.currentTimeMillis();
            if (mIRequest != null) {
                mIRequest.onRepeatRequest();
                Log.e("clickPay", "request");
            }
        }
    }

    public interface IRequest {
        /**
         * 重复请求
         */
        void onRepeatRequest();
    }
}
