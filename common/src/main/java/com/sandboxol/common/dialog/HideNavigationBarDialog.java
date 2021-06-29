package com.sandboxol.common.dialog;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.sandboxol.common.dialog.FullScreenDialog;

/**
 * Created by marvin on 2018/8/1 0001.
 */
public class HideNavigationBarDialog extends FullScreenDialog {

    public HideNavigationBarDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }
    /**
     * 隐藏底部栏
     */
    private void initWindow() {
        try {
            toggleHideBar();
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    toggleHideBar();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleHideBar() {

        if (getWindow() == null || getWindow().getDecorView() == null) {
            return;
        }

        if (Build.VERSION.SDK_INT > 18) {
            int newUiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            toggleHideBar();
        }
    }
}
