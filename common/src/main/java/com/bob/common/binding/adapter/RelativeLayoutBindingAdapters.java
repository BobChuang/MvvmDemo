package com.bob.common.binding.adapter;

import android.databinding.BindingAdapter;
import android.widget.RelativeLayout;

import com.bob.common.command.ReplyCommand;

/**
 * Created by marvin on 2018/4/19 0019.
 */

public class RelativeLayoutBindingAdapters {

    @BindingAdapter("onLongClickCommand")
    public static void LongClick(RelativeLayout relativeLayout, ReplyCommand onLongClickCommand) {
        relativeLayout.setOnLongClickListener(v -> {
            onLongClickCommand.execute();
            return true;
        });
    }
}
