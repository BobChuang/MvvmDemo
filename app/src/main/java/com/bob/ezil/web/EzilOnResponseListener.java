package com.bob.ezil.web;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public interface EzilOnResponseListener<D> {

    void onSuccess(D data);

    void onError(int code, String msg);

    void onServerError(int error);

}