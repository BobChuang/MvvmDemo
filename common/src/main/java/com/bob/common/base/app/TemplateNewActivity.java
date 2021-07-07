package com.bob.common.base.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;

import com.bob.common.R;
import com.bob.common.base.viewmodel.ViewModel;
import com.bob.common.databinding.ActivityTemplateBinding;
import com.bob.common.utils.BeanUtils;


/**
 * Created by Jimmy on 2016/8/26 0026.
 */
public class TemplateNewActivity extends BaseActivity<ViewModel, ActivityTemplateBinding> implements View.OnClickListener {

    public static String NAME = "template.fragment.name";
    public static String TITLE = "template.title";
    public static String PARAMS = "template.fragment.params";
    public static String LEFT_RESOURCE_ID = "template.left.resource.id";
    public static String RIGHT_RESOURCE_ID = "template.right.resource.id";
    public static String RIGHT_TEXT = "template.right.text";

    private Fragment fragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_template;
    }

    @Override
    protected ViewModel getViewModel() {
        return new ViewModel() {
        };
    }

    @Override
    protected void bindViewModel(ActivityTemplateBinding binding, ViewModel viewModel) {
        initToolBar();
        initFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    private void initFragment() {
        fragment = getSupportFragmentManager().findFragmentById(R.id.flTemplateContainer);
        if (fragment == null) {
            fragment = BeanUtils.getFragment(((Class) getIntent().getSerializableExtra(NAME)));
            Bundle params = getIntent().getBundleExtra(PARAMS);
            if (params != null)
                fragment.setArguments(params);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_NONE);
            ft.replace(R.id.flTemplateContainer, fragment);
            ft.commitAllowingStateLoss();
        }
    }

    private void initToolBar() {
        binding.tbTemplateBar.setTitle("");
        binding.tvTemplateTitle.setText(getIntent().getStringExtra(TITLE));
        int leftResId = getIntent().getIntExtra(LEFT_RESOURCE_ID, -1);
        binding.ibTemplateLeft.setImageResource(leftResId == -1 ? R.drawable.btn_back : leftResId);
        int rightResId = getIntent().getIntExtra(RIGHT_RESOURCE_ID, -1);
        if (rightResId != -1) {
            binding.ibTemplateRight.setVisibility(View.VISIBLE);
            binding.ibTemplateRight.setImageResource(rightResId);
        } else {
            binding.ibTemplateRight.setVisibility(View.GONE);
        }
        String rightText = getIntent().getStringExtra(RIGHT_TEXT);
        if (rightText != null) {
            binding.tvTemplateRight.setVisibility(View.VISIBLE);
            binding.tvTemplateRight.setText(rightText);
        } else {
            binding.tvTemplateRight.setVisibility(View.GONE);
        }
        binding.ibTemplateLeft.setOnClickListener(this);
        binding.ibTemplateRight.setOnClickListener(this);
        binding.tvTemplateRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ibTemplateLeft) {
            if (fragment != null && fragment instanceof TemplateFragment) {
                ((TemplateFragment) fragment).onLeftButtonClick(v);
            }
        } else if (i == R.id.ibTemplateRight || i == R.id.tvTemplateRight) {
            if (fragment != null && fragment instanceof TemplateFragment) {
                ((TemplateFragment) fragment).onRightButtonClick(v);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragment != null && fragment instanceof TemplateFragment) {
            ((TemplateFragment) fragment).onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }
}
