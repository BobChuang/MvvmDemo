package com.sandboxol.common.binding.adapter;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.View;

import com.sandboxol.common.widget.PageLoadingView;

/**
 * Created by Bob on 2017/11/13.
 */
public class PageLoadingViewBindingAdapters {

    @BindingAdapter(value = {"errorText", "isSuccess", "isLoading", "isVisible"}, requireAll = false)
    public static void initPageLoadingView(PageLoadingView pageLoadingView, String errorText, boolean isSuccess, boolean isLoading, boolean isVisible) {
        if (!isVisible) {
            pageLoadingView.setVisibility(View.GONE);
            return;
        }
        pageLoadingView.setVisibility(View.VISIBLE);
        if (!isLoading) {
            if (!isSuccess) {
                if (TextUtils.isEmpty(errorText)) {
                    pageLoadingView.failed();
                } else {
                    pageLoadingView.failed(errorText);
                }
            } else {
                pageLoadingView.success();
            }
        } else {
            pageLoadingView.setFailedHint(errorText);
            pageLoadingView.start();
        }
    }
}
