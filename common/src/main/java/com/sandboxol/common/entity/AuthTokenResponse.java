package com.sandboxol.common.entity;

import java.io.Serializable;

/**
 * Created by Bob on 2018/8/23
 */
public class AuthTokenResponse implements Serializable {

    private long userId;
    private String accessToken;
    private boolean hasBinding;//是否已经绑定了第三方账号
    private boolean hasPassword;//是否已经设置了密码

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isHasBinding() {
        return hasBinding;
    }

    public void setHasBinding(boolean hasBinding) {
        this.hasBinding = hasBinding;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }
}
