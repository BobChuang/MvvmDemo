package com.sandboxol.common.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.sandboxol.common.R;
import com.sandboxol.common.config.CommonMessageToken;
import com.sandboxol.common.messenger.Messenger;
import com.sandboxol.messager.MessagerClient;


/**
 * Created by Bob on 2018/3/26
 */
public class LoadingDialog extends FullScreenDialog {

    private AnimationDrawable loadingAnim;
    private Context context;


    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        this.context = context;
        setContentView(R.layout.common_dialog_app_loading);
        ImageView ivLoading = findViewById(R.id.ivLoading);
        loadingAnim = (AnimationDrawable) ivLoading.getBackground();

        if (!loadingAnim.isRunning())
            loadingAnim.start();
        MessagerClient.getIns().registerMsg0(this.hashCode(), CommonMessageToken.COMMON_CLOSE_LOADING_DIALOG, () -> {
            if (isShowing()) {
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing())
                        dismiss();
                } else
                    dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        try {
            Messenger.getDefault().unregister(this);
            MessagerClient.getIns().unRegisterMsg(this.hashCode());
            if (loadingAnim.isRunning()) {
                loadingAnim.stop();
            }
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isShowing()) {
            dismiss();
        }
    }
}
