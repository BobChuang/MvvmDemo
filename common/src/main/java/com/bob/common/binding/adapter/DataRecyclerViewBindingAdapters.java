package com.bob.common.binding.adapter;

import androidx.databinding.BindingAdapter;

import com.bob.common.command.ReplyCommand;
import com.bob.common.widget.rv.IListLayout;
import com.bob.common.widget.rv.datarv.DataListLayout;
import com.bob.common.widget.rv.datarv.DataListModel;
import com.bob.common.widget.rv.datarv.DataRecyclerView;

import me.tatarka.bindingcollectionadapter2.LayoutManagers;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class DataRecyclerViewBindingAdapters {

    @BindingAdapter(value = {"listLayout", "model", "layoutFactory", "isReplace","onLastVisibleCommand","closeNestedScrollingEnabled","cacheSize"}, requireAll = false)
    public static void setDataRecyclerView(DataRecyclerView dataRecyclerView, IListLayout listLayout, DataListModel model, LayoutManagers.LayoutManagerFactory layoutFactory, boolean isReplace, ReplyCommand<Integer> replyCommand,boolean closeNestedScrollingEnabled,int cacheSize) {
        if (listLayout == null) {
            dataRecyclerView.setListLayout(new DataListLayout());
        } else {
            dataRecyclerView.setListLayout(listLayout);
        }
        dataRecyclerView.setModel(model, isReplace);
        if (layoutFactory == null) {
            dataRecyclerView.setLayoutFactory(LayoutManagers.linear());
        } else {
            dataRecyclerView.setLayoutFactory(layoutFactory);
        }
        if (replyCommand!=null){
            dataRecyclerView.lastVisibleListener(replyCommand);
        }
        dataRecyclerView.setNestedScrollingEnabled(closeNestedScrollingEnabled);
        if (cacheSize!=0){
            dataRecyclerView.setItemViewCacheSize(cacheSize);
        }
    }

}
