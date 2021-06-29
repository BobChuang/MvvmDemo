package com.sandboxol.common.widget.rv.datarv;

import android.content.Context;

import com.sandboxol.common.base.web.HttpResponse;
import com.sandboxol.common.base.web.OnResponseListener;
import com.sandboxol.common.base.model.DefaultListModel;

import java.util.List;

/**
 * Created by Jimmy on 2017/9/28 0028.
 */
public abstract class DataListModel<T> extends DefaultListModel<T> {

    public DataListModel(Context context, int errorResId) {
        super(context, errorResId);
    }

    public abstract void onLoad(OnResponseListener<List<T>> listener);


}

