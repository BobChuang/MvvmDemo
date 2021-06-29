package com.sandboxol.common.base.web;

import com.sandboxol.common.config.HttpCode;

/**
 * Created by Jimmy on 2018/3/1 0001.
 */
public class OnResponseAdapter<D> implements IOnResponseAdapter<D> {

    @Override
    public void onResponse(HttpResponse<D> response, OnResponseListener<D> listener) {
        if (listener != null) {
            if (response.getCode() == HttpCode.SUCCESS) {
                listener.onSuccess(response.getData());
            } else {
                listener.onError(response.getCode(), response.getMessage());
            }
        }
    }

}
