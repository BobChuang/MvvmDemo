package com.sandboxol.common.binding.adapter;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sandboxol.common.R;
import com.sandboxol.common.command.ReplyCommand;
import com.sandboxol.common.utils.SizeUtil;

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
