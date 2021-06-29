package com.bob.ezil.view.main.chat;

import android.databinding.ViewDataBinding;

import com.bob.ezil.R;
import com.bob.common.base.app.BaseFragment;
import com.bob.common.base.viewmodel.ViewModel;

/**
 * Created by Jimmy on 2017/10/13 0013.
 */
public class ChatFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected ViewModel getViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel(ViewDataBinding binding, ViewModel viewModel) {

    }

}
