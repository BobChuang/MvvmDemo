package com.bob.common.web.adapter;

import com.bob.common.base.web.HttpResponse;
import com.bob.common.base.web.IOnResponseAdapter;
import com.bob.common.base.web.OnResponseListener;
import com.bob.common.config.HttpCode;
import com.bob.common.web.response.DataOnResponseListener;

/**
 * Created by Jimmy on 2018/3/1 0001.
 */
public class DataOnResponseAdapter<D> implements IOnResponseAdapter<D> {

    @Override
    public void onResponse(HttpResponse<D> response, OnResponseListener<D> listener) {
        if (listener != null) {
            if (response.getCode() == HttpCode.SUCCESS) {
                listener.onSuccess(response.getData());
            } else {
                if (listener instanceof DataOnResponseListener) {
                    if (response.getData() != null) {
                        ((DataOnResponseListener<D>) listener).onErrorWithData(response.getCode(), response.getMessage(), response.getData());
                    } else {
                        listener.onError(response.getCode(), response.getMessage());
                    }
                } else {
                    listener.onError(response.getCode(), response.getMessage());
                }
            }
        }
    }
}
