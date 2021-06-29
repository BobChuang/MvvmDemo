package com.bob.common.base.model;

import com.bob.common.base.viewmodel.IListViewModel;
import com.bob.common.base.viewmodel.ListItemViewModel;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by Jimmy on 2017/9/28 0028.
 */
public interface IListModel<T> extends IModel {

    void onBind(ItemBinding itemView, int position, ListItemViewModel<T> item);

    /**
     * Item类型的数量
     *
     * @return view type count
     */
    int getViewTypeCount();

    ListItemViewModel<T> getItemViewModel(T item);

    /**
     * 删除item/items的唯一标示，如果需要删除item/items，必须实现该方法并使用Messenger发送RemoveMsg类型的消息
     * RemoveMsg data use T or List<T>
     *
     * @return remove token
     */
    String getRemoveToken();

    /**
     * 插入item/items的唯一标示，如果需要插入item/items，必须实现该方法并使用Messenger发送InsertMsg类型的消息
     * InsertMsg data use T or List<T>
     *
     * @return insert token
     */
    String getInsertToken();

    /**
     * 替换items的唯一标示，如果需要插入items，必须实现该方法并使用Messenger发送ReplaceMsg类型的消息
     * ReplaceMsg data use List<T>
     *
     * @return replace token
     */
    String getReplaceToken();

    /**
     *
     * RefreshMsg data use List<T>
     *
     * @return refresh token
     */
    String getRefreshToken();

    /**
     * 错误提示，比如网络错误或者空列表
     *
     * @return empty list hint
     */
    String getErrorHint();

    /**
     * 设置ViewModel
     * @param viewModel
     */
    void setViewModel(IListViewModel<T> viewModel);

    /**
     * 获取RV的ViewModel
     * @return view model
     */
    IListViewModel<T> getViewModel();

    /**
     * 获取data数据
     * @return data
     */
    List<T> getData();

}
