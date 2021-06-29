package com.bob.common.utils;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import com.bob.common.base.app.BaseApplication;

/**
 * Created by Jimmy on 2016/8/30 0030.
 */
public class ToastUtils {

    private static Toast toast;

    public static void showShortToast(Context context, String text) {
        try {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT);
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                SafeToastUtil.hook(toast);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && toast.getView().isShown()) {
                toast.cancel();
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showShortToast(Context context, int resId) {
        try {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(BaseApplication.getContext(), resId, Toast.LENGTH_SHORT);
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                SafeToastUtil.hook(toast);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && toast.getView().isShown()) {
                toast.cancel();
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLongToast(Context context, String text) {
        try {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_LONG);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLongToast(Context context, int resId) {
        try {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(BaseApplication.getContext(), resId, Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resId) {
        Toast.makeText(BaseApplication.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

}
