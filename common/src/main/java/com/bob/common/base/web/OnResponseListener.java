package com.bob.common.base.web;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public abstract class OnResponseListener<D> {

    private int retryTimes = 1;

    public abstract void onSuccess(D data);

    public abstract void onError(int code, String msg);

    public abstract void onServerError(int error);

    public boolean needRetry() {
        return retryTimes > 0;
    }

    public void retry() {
        retryTimes--;
    }

}