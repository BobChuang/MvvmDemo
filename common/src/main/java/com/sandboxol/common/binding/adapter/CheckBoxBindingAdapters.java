package com.sandboxol.common.binding.adapter;

import android.databinding.BindingAdapter;
import android.widget.CheckBox;

import com.sandboxol.common.command.ReplyCommand;

/**
 * Created by Jimmy on 2017/12/1 0001.
 */
public class CheckBoxBindingAdapters {

    @BindingAdapter(value = {"onCheckCommand", "isPressed"}, requireAll = false)
    public static void onCheckedChangeListener(CheckBox checkBox, ReplyCommand<Boolean> onCheckCommand, boolean isPressed) {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isPressed) {
                if (onCheckCommand != null && buttonView.isPressed()) {
                    onCheckCommand.execute(isChecked);
                }
            } else {
                if (onCheckCommand != null) {
                    onCheckCommand.execute(isChecked);
                }
            }
        });
    }


}
