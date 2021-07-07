package com.bob.common.binding.adapter;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bob.common.command.ReplyCommand;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class SwipeRefreshLayoutBindingAdapters {

    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final ReplyCommand onRefreshCommand) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (onRefreshCommand != null) {
                onRefreshCommand.execute();
            }
        });
    }

    @BindingAdapter({"setRefreshing"})
    public static void setRefreshing(SwipeRefreshLayout swipeRefreshLayout, boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }
}
