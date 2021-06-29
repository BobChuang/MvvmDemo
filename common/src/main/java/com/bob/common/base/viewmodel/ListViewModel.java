package com.bob.common.base.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.bob.common.R;
import com.bob.common.base.model.IListModel;
import com.bob.common.command.ReplyCommand;
import com.bob.common.messenger.Messenger;
import com.bob.common.widget.rv.ListViewStyle;
import com.bob.common.widget.rv.msg.InsertMsg;
import com.bob.common.widget.rv.msg.RefreshMsg;
import com.bob.common.widget.rv.msg.RemoveMsg;
import com.bob.common.widget.rv.msg.ReplaceMsg;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Jimmy on 2017/9/28 0028.
 */
public abstract class ListViewModel<T> extends ViewModel implements IListViewModel<T> {

    public ObservableList<ListItemViewModel<T>> itemViewModel = new ObservableArrayList<>();
    public ReplyCommand onRefreshCommand = new ReplyCommand(this::onRefresh);
    public ListViewStyle viewStyle = new ListViewStyle();

    protected Context context;
    protected IListModel model;

    private List<T> data;

    public ItemBinding<ListItemViewModel<ListItemViewModel<T>>> itemBinding = ItemBinding.of(this::bindView);

    public ListViewModel(Context context, IListModel model) {
        super();
        this.context = context;
        this.model = model;
        this.data = new ArrayList<>();
        this.model.setViewModel(this);
        registerMessenger();
    }

    @Override
    public ObservableList<ListItemViewModel<T>> getItemViewModel() {
        return itemViewModel;
    }

    public void setRefreshing(boolean refreshing) {
        viewStyle.setRefreshing(refreshing);
    }

    @Override
    public void addItemViewModel(ListItemViewModel<T> item) {
        itemViewModel.add(item);
    }

    @Override
    public void addItemViewModel(ListItemViewModel<T> item, int index, InsertMsg.INSERT_MODE mode) {
        if (mode == InsertMsg.INSERT_MODE.END) {
            itemViewModel.add(item);
        } else if (mode == InsertMsg.INSERT_MODE.FIRST) {
            itemViewModel.add(0, item);
        } else {
            itemViewModel.add(index, item);
        }
    }

    @Override
    public void addItem(T item) {
        addItem(item, 0, InsertMsg.INSERT_MODE.END);
        data.add(item);
    }

    @Override
    public void addItem(T item, int index, InsertMsg.INSERT_MODE mode) {
        if (mode == InsertMsg.INSERT_MODE.END) {
            itemViewModel.add(model.getItemViewModel(item));
            data.add(item);
        } else if (mode == InsertMsg.INSERT_MODE.FIRST) {
            itemViewModel.add(0, model.getItemViewModel(item));
            data.add(0, item);
        } else {
            itemViewModel.add(index, model.getItemViewModel(item));
            data.add(index, item);
        }
    }

    @Override
    public void addItems(List<T> items) {
        addItems(items, 0, InsertMsg.INSERT_MODE.END);
    }

    @Override
    public void addItems(List<T> items, int index, InsertMsg.INSERT_MODE mode) {
        List<ListItemViewModel<T>> itemViewModels = new ArrayList<>();
        Observable.from(items).subscribe(item -> itemViewModels.add(model.getItemViewModel(item)), Throwable::printStackTrace);
        if (mode == InsertMsg.INSERT_MODE.END) {
            itemViewModel.addAll(itemViewModels);
        } else if (mode == InsertMsg.INSERT_MODE.FIRST) {
            itemViewModel.addAll(0, itemViewModels);
        } else {
            itemViewModel.addAll(index, itemViewModels);
        }
        data.addAll(items);
    }

    @Override
    public void clearItems() {
        itemViewModel.clear();
        data.clear();
    }

    @Override
    public void removeItem(T item) {
        Observable.from(itemViewModel)
                .filter(viewModel -> item == viewModel.item)
                .doOnNext(vm -> data.remove(vm.item))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemViewModel::remove, Throwable::printStackTrace);
    }

    @Override
    public void removeItems(Object items) {
        if (items instanceof List) {
            List<T> removes = (List<T>) items;
            if (removes.size() > 10) {
                List<T> dataResults = new ArrayList<>();
                List<ListItemViewModel<T>> vmResults = new ArrayList<>();
                Observable.from(itemViewModel)
                        .subscribe(vm -> {
                            if (removes.contains(vm.item)) {
                                dataResults.add(vm.item);
                            } else {
                                vmResults.add(vm);
                            }
                        }, Throwable::printStackTrace);
                data.removeAll(dataResults);
                itemViewModel.clear();
                itemViewModel.addAll(vmResults);
            } else {
                Observable.from(itemViewModel)
                        .filter(viewModel -> removes.contains(viewModel.item))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(vm -> data.remove(vm.item))
                        .subscribe(itemViewModel::remove, Throwable::printStackTrace);
            }
        }
    }

    @Override
    public void removeIndex(int index) {
        if (itemViewModel.size() > index) {
            itemViewModel.remove(index);
        }
        if (data.size() > index) {
            data.remove(index);
        }
    }

    @Override
    public void remove(Object data, int index, RemoveMsg.REMOVE_MODE mode) {
        switch (mode) {
            case CLEAR:
                clearItems();
                break;
            case ITEM:
                removeItem((T) data);
                break;
            case LIST:
                removeItems(data);
                break;
            case INDEX:
                removeIndex(index);
                break;
        }
    }

    @Override
    public void add(Object data, int index, InsertMsg.INSERT_MODE mode) {
        if (data instanceof List) {
            addItems((List<T>) data, index, mode);
        } else {
            addItem((T) data, index, mode);
        }
    }

    @Override
    public void replaceAll(List<T> data) {
        clearItems();
        addItems(data);
    }

    @Override
    public void hideEmptyView() {
        if (!itemViewModel.isEmpty()) {
            viewStyle.setEmptyText(model != null ? model.getErrorHint() : context.getResources().getString(R.string.no_data));
            viewStyle.setShowEmptyView(false);
        } else {
            showEmptyView(null);
        }
    }

    @Override
    public void showEmptyView(String error) {
        if (itemViewModel.isEmpty()) {
            viewStyle.setShowEmptyView(true);
            if (!TextUtils.isEmpty(error)) {
                viewStyle.setEmptyText(error);
            } else {
                viewStyle.setEmptyText(model != null ? model.getErrorHint() : context.getResources().getString(R.string.no_data));
            }
        }
    }

    @Override
    public void onSuccess() {
        setRefreshing(false);
        hideEmptyView();
    }

    @Override
    public void onError(String error) {
        setRefreshing(false);
        showEmptyView(error);
    }

    @Override
    public void bindView(ItemBinding itemView, int position, ListItemViewModel item) {
        if (model != null) {
            model.onBind(itemView, position, item);
        }
    }

    @Override
    public int getViewTypeCount() {
        return model != null ? model.getViewTypeCount() : 1;
    }

    @Override
    public void registerMessenger() {
        if (model != null) {
            if (model.getRemoveToken() != null) {
                Messenger.getDefault().register(this, model.getRemoveToken(), RemoveMsg.class, msg -> remove(msg.getData(), msg.getIndex(), msg.getMode()));
            }
            if (model.getInsertToken() != null) {
                Messenger.getDefault().register(this, model.getInsertToken(), InsertMsg.class, msg -> add(msg.getData(), msg.getIndex(), msg.getMode()));
            }
            if (model.getReplaceToken() != null) {
                Messenger.getDefault().register(this, model.getReplaceToken(), ReplaceMsg.class, msg -> replaceAll((List<T>) msg.getData()));
            }
            if (model.getRefreshToken() != null) {
                Messenger.getDefault().register(this, model.getRefreshToken(), RefreshMsg.class, msg -> onRefresh());
            }
        }
    }

    @Override
    public void onDestroy() {
        if (model != null) {
            if (model.getRemoveToken() != null) {
                Messenger.getDefault().unregister(this, RemoveMsg.class, model.getRemoveToken());
            }
            if (model.getInsertToken() != null) {
                Messenger.getDefault().unregister(this, InsertMsg.class, model.getInsertToken());
            }
            if (model.getReplaceToken() != null) {
                Messenger.getDefault().unregister(this, ReplaceMsg.class, model.getReplaceToken());
            }
            if (model.getRefreshToken() != null) {
                Messenger.getDefault().unregister(this, RefreshMsg.class, model.getRefreshToken());
            }
            Observable.from(itemViewModel).subscribe(ViewModel::onDestroy, Throwable::printStackTrace);
        }
    }

    @Override
    public List<T> getData() {
        return data != null ? data : new ArrayList<>();
    }

    public void refreshData(List<T> list) {
        if (list == null || list.size() == 0) {
            getData().clear();
            itemViewModel.clear();
            return;
        }
        getData().clear();
        getData().addAll(list);
        int itemViewSize = itemViewModel.size();
        int listSize = list.size();
        if (listSize >= itemViewSize) {
            for (int i = 0; i < itemViewSize; i++) {
                itemViewModel.set(i, model.getItemViewModel(list.get(i)));
            }
            List<ListItemViewModel<T>> itemViewModels = new ArrayList<>();
            for (int i = itemViewSize; i < listSize; i++) {
                itemViewModels.add(model.getItemViewModel(list.get(i)));
            }
            itemViewModel.addAll(itemViewModels);
        } else {
            for (int i = 0; i < listSize; i++) {
                itemViewModel.set(i, model.getItemViewModel(list.get(i)));
            }

            for (int i = itemViewSize - 1; i > listSize - 1; i--) {
                itemViewModel.remove(i);
            }
        }
        notifyChange();
    }

    /**
     * 复用item
     *
     * @param list
     */
    public void replaceData(List<T> list) {
        if (list == null || list.size() == 0) {
            getData().clear();
            itemViewModel.clear();
            return;
        }
        getData().clear();
        getData().addAll(list);
        int itemViewSize = itemViewModel.size();
        int listSize = list.size();
        if (listSize >= itemViewSize) {
            for (int i = 0; i < itemViewSize; i++) {
                itemViewModel.get(i).setItem(list.get(i));
            }
            List<ListItemViewModel<T>> itemViewModels = new ArrayList<>();
            for (int i = itemViewSize; i < listSize; i++) {
                itemViewModels.add(model.getItemViewModel(list.get(i)));
            }
            if (itemViewModels.size() > 0) {
                itemViewModel.addAll(itemViewModels);
            }
        } else {
            for (int i = 0; i < listSize; i++) {
                itemViewModel.get(i).setItem(list.get(i));
            }

            for (int i = itemViewSize - 1; i > listSize - 1; i--) {
                itemViewModel.remove(itemViewModel.size() - 1);
            }
        }
        notifyChange();
    }
}
