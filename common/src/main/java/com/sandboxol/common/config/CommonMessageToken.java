package com.sandboxol.common.config;

import com.sandboxol.common.BuildConfig;

/**
 * Created by Bob on 2017/12/12
 */
public interface CommonMessageToken {
    String TOKEN_REPEAT_LOGIN = "token.repeat.login";
    String TOKEN_DECORATION_INIT_FINISH = "token.decoration.init.finish";
    String TOKEN_ANDROID_DEVICE_ID_TYPE = "token.android.device.id.type";//统计安卓唯一ID的类型
    String TOKEN_REFRESH_USER_INFO = "token.refresh.user.info";//更新用户信息
    String TOKEN_BE_REPORT = "token.be.report";
    String TOKEN_REFRESH_TAB_NEW_ICON = "token.refresh.tab.new.icon";
    String TOKEN_REFRESH_TAB_NEW_ICON_NEXT = "token.refresh.tab.new.icon.next";

    String TOKEN_REFRESH_MESSAGE_LIST = "token.refresh.message.list";//刷新信息列表
    String TOKEN_HTTPS_COUNT = "token.https.count";
    String TOKEN_ACTIVITY_RECHARGE = "token.activity.recharge";

    String COMMON_CLOSE_LOADING_DIALOG = BuildConfig.APPLICATION_ID + ".common.close.loading.dialog";
}
