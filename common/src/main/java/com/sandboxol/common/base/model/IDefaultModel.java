package com.sandboxol.common.base.model;

import android.content.Context;

import com.sandboxol.common.base.web.OnResponseListener;

/**
 * Created by Bob on 2017/10/17.
 */
public interface IDefaultModel<T> extends IModel {

    void loadData(Context context, OnResponseListener<T> listener);

}
