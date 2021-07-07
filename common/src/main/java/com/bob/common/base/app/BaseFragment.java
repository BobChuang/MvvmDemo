package com.bob.common.base.app;

import android.app.Activity;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bob.common.base.viewmodel.ViewModel;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by Jimmy on 2016/7/31.
 */
public abstract class BaseFragment<VM extends ViewModel, D extends ViewDataBinding> extends RxFragment {

    protected Activity activity;
    protected Context context;
    protected VM viewModel;
    protected D binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();
        bindView(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        bindData();
        if (viewModel != null) {
            viewModel.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (viewModel != null) {
            viewModel.onPause();
        }
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected void bindView(LayoutInflater inflater, ViewGroup container) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            viewModel = getViewModel();
            bindViewModel(binding, viewModel);
        }
    }

    protected abstract VM getViewModel();

    protected abstract void bindViewModel(D binding, VM viewModel);

    protected void bindData() {

    }

    protected boolean isClearViewModel() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isClearViewModel() && viewModel != null) {
            viewModel.onDestroy();
            viewModel = null;
        }
    }

}
