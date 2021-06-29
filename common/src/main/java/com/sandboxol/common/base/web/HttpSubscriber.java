package com.sandboxol.common.base.web;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class HttpSubscriber<T, R extends HttpResponse<T>> extends BaseSubscriber<T, R> {

    public HttpSubscriber(OnResponseListener<T> listener) {
        super(listener);
    }

    @Override
    public boolean isCheckNetwork() {
        return false;
    }
}
