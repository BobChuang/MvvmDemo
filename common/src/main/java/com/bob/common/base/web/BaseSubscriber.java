package com.bob.common.base.web;


import android.util.Log;

import com.bob.common.base.app.BaseApplication;
import com.bob.common.config.CommonMessageToken;
import com.bob.common.config.HttpCode;
import com.bob.common.utils.NetworkUtil;
import com.bob.messager.MessagerClient;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * Created by Jimmy on 2017/11/17 0017
 */
public abstract class BaseSubscriber<T, R extends HttpResponse<T>> extends Subscriber<R> {

    protected OnResponseListener<T> listener;

    public BaseSubscriber(OnResponseListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (listener != null) {
            if (e instanceof HttpException) {
                int error = ((HttpException) e).code();
                if (error == HttpCode.AUTH_FAILED) {
                    MessagerClient.getIns().sendMsg0(CommonMessageToken.TOKEN_REPEAT_LOGIN);
                }
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
    public void onNext(R response) {
        if (response.getCode() == HttpCode.INNER_ERROR) {
            listener.onServerError(HttpCode.INNER_ERROR);
            Log.e("INNER_ERROR", listener.toString());
        } else if (response.getCode() == HttpCode.TIME_OUT) {
            listener.onServerError(HttpCode.TIME_OUT);
        } else if (response.getCode() == HttpCode.NET_BUSY) {
            listener.onServerError(HttpCode.NET_BUSY);
        } else if (response.getCode() == HttpCode.NET_ERROR) {
            listener.onServerError(HttpCode.NET_ERROR);
        } else {
            getOnResponseAdapter().onResponse(response, listener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isCheckNetwork())
            return;
        if (!NetworkUtil.isNetworkConnected(BaseApplication.getContext())) {
            onError(new ConnectException("no connect exception"));
        }
    }

    public abstract boolean isCheckNetwork();

    public IOnResponseAdapter<T> getOnResponseAdapter() {
        return new OnResponseAdapter<>();
    }

}
