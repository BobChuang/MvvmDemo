package com.sandboxol.common.widget.rv.pagerv;

import android.content.Context;

import com.sandboxol.common.R;
import com.sandboxol.common.base.viewmodel.ListViewModel;
import com.sandboxol.common.base.web.OnResponseListener;
import com.sandboxol.common.command.ReplyCommand;
import com.sandboxol.common.config.PageConfig;
import com.sandboxol.common.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Observable;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class PageListViewModel<T> extends ListViewModel<T> {

    private int page = PageConfig.DEFAULT_PAGE;
    private int size = PageConfig.DEFAULT_SIZE;
    private int defaultPage = 0;
    private boolean isMore;
    private boolean isReplace;
    public ReplyCommand<Integer> onLoadMoreCommand = new ReplyCommand<>(count -> onLoadMore());
    /**
     * //保存每页的数据
     */
    private Map<Integer, List<T>> pageDatas =new HashMap<>();


    public PageListViewModel(Context context, PageListModel model, int defaultPage, int pageSize) {
        super(context, model);
        this.defaultPage = defaultPage;
        this.size = pageSize;
        onRefresh();
    }

    public PageListViewModel(Context context, PageListModel model, int defaultPage, int pageSize, boolean isReplace) {
        super(context, model);
        this.defaultPage = defaultPage;
        this.size = pageSize;
        this.isReplace = isReplace;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        isMore = true;
        page = defaultPage;
        setRefreshing(true);
        loadData(page, true);
    }

    private void onLoadMore() {
        if (isMore) {
            page++;
            loadData(page, false);
        }
    }

    /**
     *
     * @param page -1 表示只从数据库重新刷新数据
     * @param isRefresh
     */
    protected void loadData(int page, boolean isRefresh) {
        if (model != null) {
            showEmptyView(context.getResources().getString(R.string.data_loading));
            ((PageListModel) model).onLoad(page, size, new OnResponseListener<PageData<T>>() {
                @Override
                public void onSuccess(PageData<T> data) {
                    if(data!=null){
                        if (isRefresh) {
                            onAddOrReplace(data,true);
                        } else {
                            Observable.just(data)
                                    .filter(d -> d.getData() != null)
                                    .doOnNext(d -> {
                                        //只要有一次超过最大值就认为是加载完了
                                        if (isMore) {
                                            isMore = d.getPageNo() < (d.getTotalPage() - 1);
                                        }
                                    })
                                    .subscribe(d -> onAddOrReplace(d,false), Throwable::printStackTrace);
                        }
                    }
                    PageListViewModel.this.onSuccess();
                }

                @Override
                public void onError(int code, String msg) {
                    PageListViewModel.this.onError(null);
                }

                @Override
                public void onServerError(int error) {
                    PageListViewModel.this.onError(HttpUtils.getHttpErrorMsg(context, error));
                }
            });
        }
    }

    /**
     *更新指定页的数据
     */
    private void onAddOrReplace(PageData<T> data,boolean isReplace){
        if(isReplace){
            pageDatas.clear();
        }
        pageDatas.put(data.getPageNo(),data.getData());
        page =data.getPageNo();
        List<T> all =new ArrayList<>();
        for (int i =0;i<pageDatas.size();i++){
            List<T> item =pageDatas.get(i);
            if(item!=null) {
                all.addAll(item);
            }
        }
        replaceData(all);
    }
}
