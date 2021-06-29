package com.bob.common.binding.adapter;

import android.databinding.BindingAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bob.common.command.ReplyCommand;

/**
 * Created by marvin on 2018/3/21 0021.
 */

public class LinearLayoutBindingAdapters {

    @BindingAdapter("onLongClickCommand")
    public static void LongClick(LinearLayout linearLayout, ReplyCommand onLongClickCommand) {
        linearLayout.setOnLongClickListener(v -> {
            onLongClickCommand.execute();
            return true;
        });
    }

    /**
     * 动态添加图片(banner指示器)
     */
    @BindingAdapter(value = {"indicatorDrawableSelected", "indicatorDrawableUnselected", "indicatorQuantity", "indicatorPosition"}, requireAll = true)
    public static void onIndicator(LinearLayout linearLayout, int indicatorDrawableSelected, int indicatorDrawableUnselected, int indicatorQuantity, int indicatorPosition) {
        linearLayout.removeAllViews();
        for (int i = 0; i < indicatorQuantity; i++) {
            if (i == indicatorPosition) {
                View selectedView = LayoutInflater.from(linearLayout.getContext()).inflate(indicatorDrawableSelected, null, false);
                linearLayout.addView(selectedView);
            } else {
                View unselectedView = LayoutInflater.from(linearLayout.getContext()).inflate(indicatorDrawableUnselected, null, false);
                linearLayout.addView(unselectedView);
            }
        }
    }
}
