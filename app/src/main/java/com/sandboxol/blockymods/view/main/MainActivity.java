package com.sandboxol.blockymods.view.main;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentTransaction;

import com.sandboxol.blockymods.R;
import com.sandboxol.blockymods.databinding.ActivityMainBinding;
import com.sandboxol.common.base.app.BaseActivity;
import com.sandboxol.common.base.app.BaseFragment;
import com.sandboxol.common.base.viewmodel.ViewModel;

/**
 * Created by Bob on 2017/10/12.
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
