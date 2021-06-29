package com.sandboxol.common.base.dao;

import com.sandboxol.common.base.web.OnResponseListener;
import com.sandboxol.common.config.HttpCode;

import rx.Subscriber;

/**
 * Created by Jimmy on 2017/12/1 0001.
 */
public class DaoSubscribe<T> extends Subscriber<T> {

    private OnResponseListener<T> listener;

    public DaoSubscribe(OnResponseListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {
        System.out.println();
    }

    @Override
    public void onError(Throwable e) {
        if (listener != null) {
            if (e != null && e.getCause() instanceof DaoException) {
                listener.onServerError(((DaoException) e.getCause()).getCode());
            } else {
                listener.onServerError(HttpCode.UN_KNOW);
            }
        }
    }

    @Override
    public void onNext(T response) {
        if (listener != null) {
            listener.onSuccess(response);
        }
    }

}
