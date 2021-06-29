package com.sandboxol.blockymods.view.main.index;

import android.databinding.ViewDataBinding;

import com.sandboxol.blockymods.R;
import com.sandboxol.common.base.app.BaseFragment;
import com.sandboxol.common.base.viewmodel.ViewModel;

/**
 * Created by Jimmy on 2017/10/13 0013.
 */
public class IndexFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected ViewModel getViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel(ViewDataBinding binding, ViewModel viewModel) {

    }
}
