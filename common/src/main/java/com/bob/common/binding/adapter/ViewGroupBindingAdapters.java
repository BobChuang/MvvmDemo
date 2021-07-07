package com.bob.common.binding.adapter;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bob.common.base.vm.ViewModel;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class ViewGroupBindingAdapters {

    @BindingAdapter({"itemBinding", "viewModels"})
    public static void addViews(ViewGroup viewGroup, final ItemBinding itemBinding, final ObservableList<ViewModel> viewModelList) {
        if (viewModelList != null && !viewModelList.isEmpty()) {
            viewGroup.removeAllViews();
            for (ViewModel viewModel : viewModelList) {
                ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        itemBinding.layoutRes(), viewGroup, true);
                binding.setVariable(itemBinding.variableId(), viewModel);
            }
        }
    }

    @BindingAdapter("child")
    public static void addView(ViewGroup viewGroup, View child) {
        if (child != null) {
            viewGroup.addView(child);
        }
    }

}
