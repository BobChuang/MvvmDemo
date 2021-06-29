package com.sandboxol.common.base.web;

import java.util.List;

/**
 * Created by Jimmy on 2017/9/28 0028.
 */
public class HttpListSubscriber<T, R extends HttpResponse<List<T>>> extends BaseSubscriber<List<T>, R> {

    public HttpListSubscriber(OnResponseListener<List<T>> listener) {
        super(listener);
    }

    @Override
    public boolean isCheckNetwork() {
        return false;
    }
}
