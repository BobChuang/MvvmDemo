package com.bob.common.widget.rv.pagerv;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.ViewDataBinding;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.bob.common.BR;
import com.bob.common.R;
import com.bob.common.config.PageConfig;
import com.bob.common.widget.rv.IListLayout;

import me.tatarka.bindingcollectionadapter2.LayoutManagers;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class PageRecyclerView extends FrameLayout {

    private Context context;
    private ViewDataBinding binding;
    private PageListViewModel viewModel;
    private int page, size;

    public PageRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public PageRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PageRecyclerView);
        page = array.getInteger(R.styleable.PageRecyclerView_page, PageConfig.DEFAULT_PAGE);
        size = array.getInteger(R.styleable.PageRecyclerView_size, PageConfig.DEFAULT_SIZE);
        array.recycle();
    }

    public void setModel(PageListModel model) {
        if (viewModel == null) {
            viewModel = new PageListViewModel(context, model, page, size);
            binding.setVariable(BR.ViewModel, viewModel);
        }
    }

    public void setModel(PageListModel model, boolean isReplace) {
        if (viewModel == null) {
            viewModel = new PageListViewModel(context, model, page, size, isReplace);
            binding.setVariable(BR.ViewModel, viewModel);
        }
    }

    public void setListLayout(IListLayout listLayout) {
        binding = listLayout.bind(context, this, true);
    }

    public void setLayoutFactory(LayoutManagers.LayoutManagerFactory layoutFactory) {
        RecyclerView rvData = binding.getRoot().findViewById(R.id.rvData);
        if (rvData != null) {
            rvData.setLayoutManager(layoutFactory.create(rvData));
        }
    }
    @Override
    public void setNestedScrollingEnabled(boolean closeNestedScrollingEnabled) {
        //super.setNestedScrollingEnabled(!closeNestedScrollingEnabled);
        RecyclerView rvData = binding.getRoot().findViewById(R.id.rvData);
        if (rvData != null) {
            rvData.setNestedScrollingEnabled(!closeNestedScrollingEnabled);
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (viewModel != null) {
            viewModel.onDestroy();
            viewModel = null;
        }
    }

}
