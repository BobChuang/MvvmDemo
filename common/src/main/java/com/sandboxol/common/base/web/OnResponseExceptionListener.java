package com.sandboxol.common.base.web;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public abstract class OnResponseExceptionListener<D> extends OnResponseListener<D> {

    public abstract void onErrorWithData(int code, Throwable e);

}
