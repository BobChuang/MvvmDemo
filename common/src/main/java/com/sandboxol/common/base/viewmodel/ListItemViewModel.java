package com.sandboxol.common.base.viewmodel;

import android.content.Context;
import android.databinding.Bindable;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public abstract class ListItemViewModel<T> extends ViewModel {

    protected Context context;
    @Bindable
    protected T item;

    public ListItemViewModel(Context context, T item) {
        this.context = context;
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
        notifyChange();
    }
}
