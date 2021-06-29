package com.sandboxol.common.web.error;

import android.content.Context;

import com.sandboxol.common.R;
import com.sandboxol.common.utils.AppToastUtils;
import com.sandboxol.common.utils.HttpUtils;
import com.tendcloud.tenddata.TCAgent;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/14
 */

public class ServerOnError {

    public static void showOnServerError(Context context, int error) {
        AppToastUtils.showShortNegativeTipToast(context, HttpUtils.getHttpErrorMsg(context, error));
        String result = HttpUtils.getHttpErrorMsg(context, error);
        if (context.getResources().getString(R.string.connect_error_code, error).equals(result)) {
            TCAgent.onEvent(context, "Unknown Error Code", "onServerError_" + error);
        }
    }

    public static void showOnServerError(Context context, int error, String modules) {
        showOnServerError(context, error);
        //检测到未知错误码的时候上报给TD
        String result = HttpUtils.getHttpErrorMsg(context, error);
        if (context.getResources().getString(R.string.connect_error_code, error).equals(result)) {
            TCAgent.onEvent(context, "Unknown Error Code", "onServerError_" +modules + "_" + error);
        }
    }
}
