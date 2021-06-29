package com.sandboxol.common.widget.rv.datarv;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.sandboxol.common.BR;
import com.sandboxol.common.R;
import com.sandboxol.common.command.ReplyCommand;
import com.sandboxol.common.widget.rv.IListLayout;

import java.util.concurrent.TimeUnit;

import me.tatarka.bindingcollectionadapter2.LayoutManagers;
import rx.subjects.PublishSubject;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class DataRecyclerView extends FrameLayout {

    private Context context;
    private ViewDataBinding binding;
    private DataListViewModel viewModel;

    public DataRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public DataRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setListLayout(IListLayout listLayout) {
        binding = listLayout.bind(context, this, true);
    }

    public void setModel(DataListModel model) {
        if (viewModel == null) {
            viewModel = new DataListViewModel(context, model);
            binding.setVariable(BR.ViewModel, viewModel);
        }
    }

    public void setModel(DataListModel model, boolean isReplace) {
        if (viewModel == null) {
            viewModel = new DataListViewModel(context, model, isReplace);
            binding.setVariable(BR.ViewModel, viewModel);
        }
    }

    public void setLayoutFactory(LayoutManagers.LayoutManagerFactory layoutFactory) {
        RecyclerView rvData = binding.getRoot().findViewById(R.id.rvData);
        if (rvData != null) {
            rvData.setLayoutManager(layoutFactory.create(rvData));
        }
    }

    public void scrollBy(int x, int y) {
        RecyclerView rvData = binding.getRoot().findViewById(R.id.rvData);
        if (rvData != null) {
            rvData.scrollBy(x, y);
        }
    }

    public void lastVisibleListener(ReplyCommand<Integer> replyCommand) {
        RecyclerView rvData = binding.getRoot().findViewById(R.id.rvData);
        if (rvData != null) {
            PublishSubject<Integer> subject = PublishSubject.create();
            subject.throttleFirst(300, TimeUnit.MILLISECONDS)
                    .subscribe(replyCommand::execute, Throwable::printStackTrace);
            LinearLayoutManager layoutManager = (LinearLayoutManager) rvData.getLayoutManager();
            rvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    subject.onNext(lastVisibleItemPosition);
                }
            });
        }
    }

    public void scrollToPosition(int position) {
        if (binding != null && binding.getRoot() != null) {
            RecyclerView rvData = binding.getRoot().findViewById(R.id.rvData);
            if (rvData != null) {
                try {
                    rvData.scrollToPosition(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setItemViewCacheSize(int size){
        RecyclerView rvData = binding.getRoot().findViewById(R.id.rvData);
        if (rvData != null) {
            rvData.setItemViewCacheSize(100);
        }
    }

    public void setNestedScrollingEnabled(boolean closeNestedScrollingEnabled) {
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
