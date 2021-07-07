package com.bob.common.binding.adapter;

import androidx.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.bob.common.R;
import com.bob.common.command.ReplyCommand;
import com.bob.common.utils.SizeUtil;

import java.util.concurrent.TimeUnit;

import rx.subjects.PublishSubject;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class RecyclerViewBindingAdapters {

    @BindingAdapter(value = {"onScrollChangeCommand", "onScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final RecyclerView recyclerView,
                                             final ReplyCommand<ScrollDataWrapper> onScrollChangeCommand,
                                             final ReplyCommand<Integer> onScrollStateChangedCommand) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int state;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onScrollChangeCommand != null) {
                    onScrollChangeCommand.execute(new ScrollDataWrapper(dx, dy, state));
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state = newState;
                if (onScrollStateChangedCommand != null) {
                    onScrollStateChangedCommand.execute(state);
                }
            }
        });

    }

    @BindingAdapter(value = {"dividerColor", "dividerHeight", "dividerMarginLeft", "dividerMarginRight"}, requireAll = false)
    public static void setItemDecoration(final RecyclerView recyclerView, int dividerColor, int dividerHeight, int dividerMarginLeft, int dividerMarginRight) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (dividerColor == 0)
            paint.setColor(recyclerView.getContext().getResources().getColor(R.color.mainBgColor));
        else
            paint.setColor(recyclerView.getContext().getResources().getColor(dividerColor));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount - 1; i++) {
                    View view = parent.getChildAt(i);
                    float childTop = view.getBottom() - SizeUtil.dp2px(recyclerView.getContext(), dividerHeight);
                    float childBottom = view.getBottom();
                    float childLeft = view.getLeft() + SizeUtil.dp2px(recyclerView.getContext(), dividerMarginLeft);
                    float childRight = view.getRight() - SizeUtil.dp2px(recyclerView.getContext(), dividerMarginRight);
                    c.drawRect(childLeft, childTop, childRight, childBottom, paint);
                }
            }
        });
    }


    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(final RecyclerView recyclerView, final ReplyCommand<Integer> onLoadMoreCommand) {

        RecyclerView.OnScrollListener listener = new OnScrollListener(onLoadMoreCommand);
        recyclerView.clearOnScrollListeners();
        recyclerView.addOnScrollListener(listener);

    }

    @BindingAdapter("adapter")
    public static void setAdapter(final RecyclerView recyclerView, final RecyclerView.Adapter adapter) {
        if (adapter != null)
            recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(final RecyclerView recyclerView, int linearLayoutManager) {
        if (linearLayoutManager == LinearLayoutManager.HORIZONTAL || linearLayoutManager == LinearLayoutManager.VERTICAL)
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), linearLayoutManager, false));
    }

    public static class OnScrollListener extends RecyclerView.OnScrollListener {

        private PublishSubject<Integer> methodInvoke = PublishSubject.create();

        private ReplyCommand<Integer> onLoadMoreCommand;


        public OnScrollListener(ReplyCommand<Integer> onLoadMoreCommand) {
            this.onLoadMoreCommand = onLoadMoreCommand;
            methodInvoke.throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(onLoadMoreCommand::execute);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    if (onLoadMoreCommand != null) {
                        methodInvoke.onNext(recyclerView.getAdapter().getItemCount());
                    }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

    }

    public static class ScrollDataWrapper {
        public float scrollX;
        public float scrollY;
        public int state;

        public ScrollDataWrapper(float scrollX, float scrollY, int state) {
            this.scrollX = scrollX;
            this.scrollY = scrollY;
            this.state = state;
        }
    }

}
