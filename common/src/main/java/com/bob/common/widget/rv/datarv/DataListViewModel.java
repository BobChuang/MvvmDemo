package com.bob.common.widget.rv.datarv;

import android.content.Context;

import com.bob.common.R;
import com.bob.common.base.model.IListModel;
import com.bob.common.base.viewmodel.ListViewModel;
import com.bob.common.base.web.OnResponseListener;
import com.bob.common.utils.HttpUtils;

import java.util.List;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class DataListViewModel<T> extends ListViewModel<T> {

    private boolean isReplace;

    public DataListViewModel(Context context, IListModel model) {
        super(context, model);
        onRefresh();
    }

    public DataListViewModel(Context context, IListModel model, boolean isReplace) {
        super(context, model);
        this.isReplace = isReplace;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        loadData();
    }

    public void loadData() {
        if (model != null) {
            showEmptyView(context.getResources().getString(R.string.data_loading));
            ((DataListModel) model).onLoad(new OnResponseListener<List<T>>() {
                @Override
                public void onSuccess(List<T> data) {
                    if(data!=null){
                        if (isReplace) {
                            replaceData(data);
                        } else {
                            refreshData(data);
                        }
                    }
                    DataListViewModel.this.onSuccess();
                }

                @Override
                public void onError(int code, String msg) {
                    DataListViewModel.this.onError(null);
                }

                @Override
                public void onServerError(int error) {
                    DataListViewModel.this.onError(HttpUtils.getHttpErrorMsg(context, error));
                }
            });
        }
    }

}
