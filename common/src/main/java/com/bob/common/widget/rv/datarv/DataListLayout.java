package com.bob.common.widget.rv.datarv;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bob.common.R;
import com.bob.common.widget.rv.IListLayout;

/**
 * Created by Jimmy on 2017/10/31 0031.
 */
public class DataListLayout implements IListLayout {

    @Override
    public ViewDataBinding bind(Context context, ViewGroup parent, boolean attachToParent) {
       return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.data_list_view, parent, attachToParent);
    }
}
