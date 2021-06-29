package com.bob.common.base.model;

import android.content.Context;

import com.bob.common.base.web.OnResponseListener;

/**
 * Created by Bob on 2017/10/17.
 */
public interface IDefaultModel<T> extends IModel {

    void loadData(Context context, OnResponseListener<T> listener);

}
