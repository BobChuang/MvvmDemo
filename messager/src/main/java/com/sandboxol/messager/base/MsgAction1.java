package com.sandboxol.messager.base;

/**
 * 复杂消息回调封装
 */
public class MsgAction1 {

    String token;
    int activityHash ;

    Messager1 messager1;
    public MsgAction1(int activityHash, String token, Messager1 messager1) {
        this.activityHash =activityHash;
        this.token =token;
        this.messager1 =messager1;
    }
    public Messager1 getMessager1() {
        return messager1;
    }
    public int getActivityHash() {
        return activityHash;
    }
    public String getToken() {
        return token;
    }


}
