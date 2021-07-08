package com.bob.ezil;

import androidx.appcompat.app.AppCompatDelegate;

import com.bob.common.base.app.BaseApplication;
import com.bob.common.config.CommonSharedConstant;
import com.bob.common.utils.SharedUtils;

/**
 * Created by BobCheung on 7/8/21 01:23
 */
public class App  extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        boolean nightMode = SharedUtils.getBoolean(this, CommonSharedConstant.NIGHT_MODE_ON);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }
}
