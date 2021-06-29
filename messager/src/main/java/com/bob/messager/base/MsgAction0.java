package com.bob.messager.base;

/**
 * 简单消息回调封装
 */
public class MsgAction0 {

    String token;
    int activityHash;
    Messager0 messager0;

    public MsgAction0(int activityHash, String token, Messager0 messager0) {
        this.activityHash = activityHash;
        this.token = token;
        this.messager0 = messager0;
    }

    public Messager0 getMessager0() {
        return messager0;
    }

    public int getActivityHash() {
        return activityHash;
    }

    public String getToken() {
        return token;
    }
}
