package com.bob.ezil.view.fragment.home;

import com.bob.common.base.app.TemplateFragment;
import com.bob.ezil.R;
import com.bob.ezil.databinding.FragmentHomeBinding;

/**
 * Created by Bob on 2021/06/30.
 */
public class HomeFragment extends TemplateFragment<HomeViewModel, FragmentHomeBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomeViewModel getViewModel() {
        return new HomeViewModel(context);
    }

    @Override
    protected void bindViewModel(FragmentHomeBinding binding, HomeViewModel viewModel) {
        binding.setHomeViewModel(viewModel);
    }
}
