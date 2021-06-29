package com.bob.common.listener;

import android.view.View;

/**
 * Created by Bob on 2018/7/19
 *
 * @author bob
 */
public class ClickProxy implements View.OnClickListener {

    private View.OnClickListener origin;
    private long lastClick = 0;
    /**
     * 时间（单位：ms）
     */
    private long times = 1000;
    private IAgain mIAgain;

    public ClickProxy(View.OnClickListener origin) {
        this.origin = origin;
    }

    public ClickProxy(View.OnClickListener origin, long times) {
        this.origin = origin;
        this.times = times;
    }

    public ClickProxy(View.OnClickListener origin, long times, IAgain again) {
        this.origin = origin;
        this.mIAgain = again;
        this.times = times;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastClick >= times) {
            origin.onClick(v);
            lastClick = System.currentTimeMillis();
        } else {
            if (mIAgain != null) {
                mIAgain.onAgain();
            }
        }
    }

    public interface IAgain {
        void onAgain();//重复点击
    }
}