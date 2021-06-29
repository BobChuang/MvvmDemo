package com.bob.common.widget.rv.pagerv;

import android.content.Context;

import com.bob.common.base.web.OnResponseListener;
import com.bob.common.base.model.DefaultListModel;

/**
 * Created by Jimmy on 2017/9/28 0028.
 */
public abstract class PageListModel<T> extends DefaultListModel<T> {

    public PageListModel(Context context, int errorResId) {
        super(context, errorResId);
    }

    public abstract void onLoad(int page, int size, OnResponseListener<PageData<T>> listener);

}

