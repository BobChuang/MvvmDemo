package com.bob.common.base.web;

/**
 * Created by Jimmy on 2018/3/1 0001.
 */
public interface IOnResponseAdapter<D> {

    void onResponse(HttpResponse<D> response, OnResponseListener<D> listener);

}
