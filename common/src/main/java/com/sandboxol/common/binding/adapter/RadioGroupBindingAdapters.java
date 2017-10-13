package com.sandboxol.common.binding.adapter;

import android.databinding.BindingAdapter;
import android.widget.RadioGroup;

import com.sandboxol.common.command.ReplyCommand;

/**
 * Created by Jimmy on 2017/10/13 0013.
 */
public class RadioGroupBindingAdapters {

    @BindingAdapter(value = {"onCheckCommand"}, requireAll = false)
    public static void onCheckedChangeListener(RadioGroup radioGroup, ReplyCommand<CheckedDataWrapper> onCheckCommand) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (onCheckCommand != null) {
                onCheckCommand.execute(new CheckedDataWrapper(checkedId));
            }
        });
    }

    public static class CheckedDataWrapper {

        private int checkedId;

        public CheckedDataWrapper(int checkedId) {
            this.checkedId = checkedId;
        }

        public int getCheckedId() {
            return checkedId;
        }

        public void setCheckedId(int checkedId) {
            this.checkedId = checkedId;
        }
    }

}
