package com.sandboxol.common.base.web;

import com.sandboxol.common.widget.rv.pagerv.PageData;

/**
 * Created by Jimmy on 2017/9/28 0028.
 */
public class HttpPageListSubscriber<T, R extends HttpResponse<PageData<T>>> extends BaseSubscriber<PageData<T>, R> {

    public HttpPageListSubscriber(OnResponseListener<PageData<T>> listener) {
        super(listener);
    }

    @Override
    public boolean isCheckNetwork() {
        return false;
    }

}
