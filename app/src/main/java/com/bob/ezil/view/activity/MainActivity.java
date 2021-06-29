package com.bob.ezil.view.activity;

import android.support.v4.app.FragmentTransaction;

import com.bob.ezil.R;
import com.bob.ezil.databinding.ActivityMainBinding;
import com.bob.common.base.app.BaseActivity;
import com.bob.common.base.app.BaseFragment;

/**
 * Created by Bob on 2021/06/30.
 */
public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel getViewModel() {
        return new MainViewModel(this);
    }

    @Override
    protected void bindViewModel(ActivityMainBinding binding, MainViewModel viewModel) {
        binding.setMainViewModel(viewModel);
    }

    protected void replaceFragment(BaseFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        ft.replace(R.id.flHomePage, fragment);
        ft.commit();
    }

}
