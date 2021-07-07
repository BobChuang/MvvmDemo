package com.bob.common.widget.rv;

import android.content.Context;
import androidx.databinding.ViewDataBinding;
import android.view.ViewGroup;

/**
 * Created by Jimmy on 2017/10/31 0031.
 */
public interface IListLayout {

    ViewDataBinding bind(Context context, ViewGroup parent, boolean attachToParent);

}
