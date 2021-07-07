package com.bob.common.base.viewmodel;

import androidx.databinding.ObservableList;

import com.bob.common.widget.rv.msg.InsertMsg;
import com.bob.common.widget.rv.msg.RemoveMsg;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;


/**
 * Created by Jimmy on 2017/10/31 0031.
 */
public interface IListViewModel<T> {

    ObservableList<ListItemViewModel<T>> getItemViewModel();

    void onRefresh();

    void setRefreshing(boolean refreshing);

    void addItemViewModel(ListItemViewModel<T> item);

    void addItemViewModel(ListItemViewModel<T> item, int index, InsertMsg.INSERT_MODE mode);

    void addItem(T item);

    void addItem(T item, int index, InsertMsg.INSERT_MODE mode);

    void addItems(List<T> items);

    void addItems(List<T> items, int index, InsertMsg.INSERT_MODE mode);

    void clearItems();

    void removeItem(T item);

    void removeItems(Object items);

    void removeIndex(int index);

    void remove(Object data, int index, RemoveMsg.REMOVE_MODE mode);

    void add(Object data, int index, InsertMsg.INSERT_MODE mode);

    void replaceAll(List<T> data);

    void hideEmptyView();

    void showEmptyView(String error);

    void onSuccess();

    void onError(String error);

    void bindView(ItemBinding itemView, int position, ListItemViewModel item);

    void registerMessenger();

    int getViewTypeCount();

    List<T> getData();
}
