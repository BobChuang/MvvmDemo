package com.sandboxol.messager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.sandboxol.messager.base.Messager0;
import com.sandboxol.messager.base.Messager1;
import com.sandboxol.messager.base.MsgAction0;
import com.sandboxol.messager.base.MsgAction1;
import com.sandboxol.messager.base.SanboxMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 对广播进行二次封装
 */
public class MessagerClient {
    private final static String TAG = MessagerClient.class.getName();
    private final static String TAG_DATA = "data";//消息标签
    private final static String MESSAGE_CLASS = "message.class";//消息类型
    private final static String PACKAGE_NAME ="package.name";//当前程序包名，用于避免和分包冲突
    private List<MsgAction0> mList0 = new ArrayList<>();//用于保存每次注册的事件，用于服务重连时恢复
    private List<MsgAction1> mList1 = new ArrayList<>();
    private MessageBroadcastReceiver messageBroadcastReceiver;
    private Context mContext;

    private static class MessagerClientImpl {
        private final static MessagerClient instance = new MessagerClient();
    }

    public static MessagerClient getIns() {
        return MessagerClient.MessagerClientImpl.instance;
    }

    public void init(Context context) {
        mContext = context;
        messageBroadcastReceiver = new MessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(context.getPackageName());
        context.registerReceiver(messageBroadcastReceiver, intentFilter);

    }

    public void unInit(Context context) {
        if (messageBroadcastReceiver != null) {
            context.unregisterReceiver(messageBroadcastReceiver);
            messageBroadcastReceiver = null;
        }
        mContext = null;
    }

    /**
     * 注册消息回调
     *
     * @param activityHash
     * @param token
     * @param messager0
     */
    public void registerMsg0(int activityHash, String token, Messager0 messager0) {
        if (messageBroadcastReceiver == null) {
            return;
        }
        synchronized (MessagerClient.class) {
            mList0.add(new MsgAction0(activityHash, token, messager0));
        }

    }

    /**
     * 注册消息回调
     *
     * @param activityHash
     * @param token
     * @param messager1
     */
    public void registerMsg1(int activityHash, String token, Messager1 messager1) {
        if (messageBroadcastReceiver == null) {
            return;
        }
        synchronized (MessagerClient.class) {
            mList1.add(new MsgAction1(activityHash, token, messager1));
        }
    }

    /**
     * 发送消息
     *
     * @param token
     */
    public void sendMsg0(String token) {
        if (messageBroadcastReceiver == null || mContext == null) {
            return;
        }
        Intent intent = new Intent(mContext.getPackageName());
        intent.putExtra(MESSAGE_CLASS,token);
        mContext.sendBroadcast(intent);
    }

    public void sendMsg1(String token, @NonNull SanboxMessage msg) {
        if (messageBroadcastReceiver == null || mContext == null) {
            return;
        }
        Intent intent = new Intent(mContext.getPackageName());
        intent.putExtra(MESSAGE_CLASS,token);
        intent.putExtra(TAG_DATA,msg);
        mContext.sendBroadcast(intent);
    }

    /**
     * 反注册消息回调
     *
     * @param activityHash
     */
    public void unRegisterMsg(int activityHash) {
        synchronized (MessagerClient.class) {
            List<MsgAction0> delList = new ArrayList<>();
            for (int i = 0; i < mList0.size(); i++) {
                if (mList0.get(i).getActivityHash() == activityHash) {

                    delList.add(mList0.get(i));
                }
            }
            mList0.removeAll(delList);
            List<MsgAction1> delList1 = new ArrayList<>();
            for (int i = 0; i < mList1.size(); i++) {
                if (mList1.get(i).getActivityHash() == activityHash) {

                    delList1.add(mList1.get(i));
                }
            }
            mList1.removeAll(delList1);
        }
    }

    public void unRegisterAll() {
        synchronized (MessagerClient.class) {
            mList0.clear();
            mList1.clear();
        }
    }

    class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null || !action.equalsIgnoreCase(context.getPackageName())) {
                return;
            }
            String token = intent.getStringExtra(MESSAGE_CLASS);
            SanboxMessage sanboxMessage = intent.getParcelableExtra(TAG_DATA);
            if (token != null&&token.length()>0) {
                synchronized (MessagerClient.class) {
                    if (sanboxMessage != null) {
                        //message1处理
                        for (int i = 0; i < mList1.size(); i++) {
                            MsgAction1 action1 = mList1.get(i);
                            if (action1.getToken() != null && action1.getToken().equalsIgnoreCase(token)) {
                                action1.getMessager1().onCall(sanboxMessage);
                            }
                        }
                    } else {
                        //message0处理
                        for (int i = 0; i < mList0.size(); i++) {
                            MsgAction0 action0 = mList0.get(i);
                            if (action0.getToken() != null && action0.getToken().equalsIgnoreCase(token)) {
                                action0.getMessager0().onCall();
                            }
                        }
                    }
                }
            }
        }
    }
}
