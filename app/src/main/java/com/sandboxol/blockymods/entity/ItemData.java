package com.sandboxol.blockymods.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sandboxol.blockymods.BR;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class ItemData extends BaseObservable {

    private String title;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
}
