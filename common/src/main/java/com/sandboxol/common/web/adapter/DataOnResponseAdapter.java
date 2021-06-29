package com.sandboxol.common.web.adapter;

import com.sandboxol.common.base.web.HttpResponse;
import com.sandboxol.common.base.web.IOnResponseAdapter;
import com.sandboxol.common.base.web.OnResponseListener;
import com.sandboxol.common.config.HttpCode;
import com.sandboxol.common.web.response.DataOnResponseListener;

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
