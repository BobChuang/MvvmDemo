package com.bob.common.web.response;

import com.bob.common.base.web.OnResponseListener;

/**
 * Created by Jimmy on 2018/3/1 0001.
 */
public abstract class DataOnResponseListener<D> extends OnResponseListener<D> {

    public abstract void onErrorWithData(int code, String msg, D data);

}
