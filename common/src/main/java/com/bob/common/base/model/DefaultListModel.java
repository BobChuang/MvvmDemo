package com.bob.common.base.model;

import android.content.Context;
import android.text.TextUtils;

import com.bob.common.R;
import com.bob.common.base.viewmodel.IListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 2017/10/30 0030.
 */
public abstract class DefaultListModel<T> implements IListModel<T> {

    protected Context context;
    private String errorHint;
    private IListViewModel viewModel;

    public DefaultListModel(Context context, int errorResId) {
        this.context = context;
        this.errorHint = context.getResources().getString(errorResId);
    }

    @Override
    public String getRemoveToken() {
        return null;
    }

    @Override
    public String getInsertToken() {
        return null;
    }

    @Override
    public String getReplaceToken() {
        return null;
    }

    @Override
    public String getRefreshToken() {
        return null;
    }

    @Override
    public void setViewModel(IListViewModel<T> viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public IListViewModel<T> getViewModel() {
        return viewModel;
    }

    @Override
    public List<T> getData() {
        if (viewModel != null) {
            return viewModel.getData();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public String getErrorHint() {
        if (TextUtils.isEmpty(errorHint))
            errorHint = context.getResources().getString(R.string.no_data);
        return errorHint;
    }

}
