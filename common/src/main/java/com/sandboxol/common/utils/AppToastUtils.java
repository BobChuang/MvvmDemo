package com.sandboxol.common.utils;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sandboxol.common.R;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class AppToastUtils {

    private static final boolean POSITIVE = true;
    private static final boolean NEGATIVE = false;

    public static void showShortPositiveTipToast(Context context, int content) {
        showTipToast(context, context.getString(content), POSITIVE, Toast.LENGTH_SHORT);
    }

    public static void showShortPositiveTipToast(Context context, String content) {
        showTipToast(context, content, POSITIVE, Toast.LENGTH_SHORT);
    }

    public static void showShortNegativeTipToast(Context context, int content) {
        showTipToast(context, context.getString(content), NEGATIVE, Toast.LENGTH_SHORT);
    }

    public static void showShortNegativeTipToast(Context context, String content) {
        showTipToast(context, content, NEGATIVE, Toast.LENGTH_SHORT);
    }

    public static void showLongPositiveTipToast(Context context, int content) {
        showTipToast(context, context.getString(content), POSITIVE, Toast.LENGTH_LONG);
    }

    public static void showLongPositiveTipToast(Context context, String content) {
        showTipToast(context, content, POSITIVE, Toast.LENGTH_LONG);
    }

    public static void showLongNegativeTipToast(Context context, int content) {
        showTipToast(context, context.getString(content), NEGATIVE, Toast.LENGTH_LONG);
    }

    public static void showLongNegativeTipToast(Context context, String content) {
        showTipToast(context, content, NEGATIVE, Toast.LENGTH_LONG);
    }

    public static void showTipToast(Context context, String content, boolean type, int time) {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.toast_tip, null);
            ImageView ivTipType = view.findViewById(R.id.iv_tip_type);
            TextView tvTipContent = view.findViewById(R.id.tv_tip_content);
            if (type) {
                ivTipType.setImageResource(R.mipmap.ic_tip_positive);
            } else {
                ivTipType.setImageResource(R.mipmap.ic_tip_negative);
            }

            Toast toast = new Toast(context);
            toast.setView(view);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, (int) SizeUtil.dp2px(context, 58));
            tvTipContent.setText(content);
            toast.setDuration(time);
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

}
