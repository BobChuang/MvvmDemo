package com.sandboxol.blockymods.view.main;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;

import com.sandboxol.blockymods.R;
import com.sandboxol.blockymods.databinding.ActivityMainBinding;
import com.sandboxol.common.base.app.BaseActivity;
import com.sandboxol.common.base.app.BaseFragment;

/**
 * Created by Bob on 2017/10/12.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void bindView() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainViewModel(new MainViewModel(this));
    }

    protected void replaceFragment(BaseFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        ft.replace(R.id.flHomePage, fragment);
        ft.commit();
    }

}
