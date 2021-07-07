package com.bob.ezil.web;


import com.bob.common.config.HttpCode;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * Created by Bobcheung on 2021/06/30
 */
public class EzilSubscriber<T> extends Subscriber<T> {

    protected EzilOnResponseListener<T> listener;
    private Type clazz;

    public EzilSubscriber(EzilOnResponseListener<T> listener, Type clazz) {
        this.listener = listener;
        this.clazz = clazz;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (listener != null) {
            if (e instanceof HttpException) {
                int error = ((HttpException) e).code();

                listener.onServerError(error);
            } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
                listener.onServerError(HttpCode.NO_CONNECTED);
            } else if (e instanceof SocketTimeoutException) {
                listener.onServerError(HttpCode.TIMEOUT);
            } else if (e instanceof RuntimeException) {
                listener.onServerError(HttpCode.NOT_NET_ERROR);
            } else {
                listener.onServerError(HttpCode.UN_KNOW);
            }
        }
    }

    @Override
    public void onNext(T response) {
        if (listener != null) {
            listener.onSuccess((T) response);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
