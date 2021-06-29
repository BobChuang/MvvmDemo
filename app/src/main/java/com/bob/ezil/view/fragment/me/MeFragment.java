package com.bob.ezil.view.fragment.me;

import android.databinding.ViewDataBinding;

import com.bob.ezil.R;
import com.bob.common.base.app.BaseFragment;
import com.bob.common.base.viewmodel.ViewModel;

/**
 * Created by Bob on 2021/06/30.
 */
public class MeFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected ViewModel getViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel(ViewDataBinding binding, ViewModel viewModel) {

    }
}
