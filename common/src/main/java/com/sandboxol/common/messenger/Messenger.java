package com.sandboxol.common.messenger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class Messenger {

    private static Messenger defaultInstance;

    private HashMap<Type, List<WeakActionAndToken>> recipientsOfSubclassesAction;

    private HashMap<Type, List<WeakActionAndToken>> recipientsStrictAction;

    public static Messenger getDefault() {
        if (defaultInstance == null) {
            defaultInstance = new Messenger();
        }
        return defaultInstance;
    }


    public static void overrideDefault(Messenger newMessenger) {
        defaultInstance = newMessenger;
    }

    public static void reset() {
        defaultInstance = null;
    }

    /**
     * @param recipient the receiver,if register in activity the recipient always set "this",
     *                  and "Messenger.getDefault().unregister(this)" in onDestroy,if in ViewModel,
     *                  you can also register with Activity context and also in onDestroy to unregister.
     * @param action    do something on message received
     */
    public void register(Object recipient, Action0 action) {
        register(recipient, null, false, action);
    }

    /**
     * @param recipient                 the receiver,if register in activity the recipient always set "this",
     *                                  and "Messenger.getDefault().unregister(this)" in onDestroy,if in ViewModel,
     *                                  you can also register with Activity context and also in onDestroy to unregister.
     * @param receiveDerivedMessagesToo whether Derived class of recipient can receive the message
     * @param action                    do something on message received
     */
    public void register(Object recipient, boolean receiveDerivedMessagesToo, Action0 action) {
        register(recipient, null, receiveDerivedMessagesToo, action);
    }

    /**
     * @param recipient the receiver,if register in activity the recipient always set "this",
     *                  and "Messenger.getDefault().unregister(this)" in onDestroy,if in ViewModel,
     *                  you can also register with Activity context and also in onDestroy to unregister.
     * @param token     register with a unique token,when a messenger send a msg with same token,it will receive this msg
     * @param action    do something on message received
     */
    public void register(Object recipient, Object token, Action0 action) {
        register(recipient, token, false, action);
    }

    /**
     * @param recipient                 the receiver,if register in activity the recipient always set "this",
     *                                  and "Messenger.getDefault().unregister(this)" in onDestroy,if in ViewModel,
     *                                  you can also register with Activity context and also in onDestroy to unregister.
     * @param token                     register with a unique token,when a messenger send a msg with same token,it will receive this msg
     * @param receiveDerivedMessagesToo whether Derived class of recipient can receive the message
     * @param action                    do something on message received
     */
    public void register(Object recipient, Object token, boolean receiveDerivedMessagesToo, Action0 action) {

        Type messageType = NotMsgType.class;

        HashMap<Type, List<WeakActionAndToken>> recipients;

        if (receiveDerivedMessagesToo) {
            if (recipientsOfSubclassesAction == null) {
                recipientsOfSubclassesAction = new HashMap<>();
            }

            recipients = recipientsOfSubclassesAction;
        } else {
            if (recipientsStrictAction == null) {
                recipientsStrictAction = new HashMap<>();
            }

            recipients = recipientsStrictAction;
        }

        List<WeakActionAndToken> list;

        if (!recipients.containsKey(messageType)) {
            list = new ArrayList<>();
            recipients.put(messageType, list);
        } else {
            list = recipients.get(messageType);
        }

        WeakAction weakAction = new WeakAction(recipient, action);

        WeakActionAndToken item = new WeakActionAndToken(weakAction, token);
        list.add(item);
        cleanup();
    }

    public <T> void register(Object recipient, Class<T> tClass, Action1<T> action) {
        register(recipient, null, false, action, tClass);
    }

    /**
     * see {@link Messenger#register(Object, Class, Action1)}
     *
     * @param recipient                 receiver of message
     * @param receiveDerivedMessagesToo whether derived class of recipient can receive the message
     * @param tClass                    class of T
     * @param action                    this action has one params that type of tClass
     * @param <T>                       message data type
     */
    public <T> void register(Object recipient, boolean receiveDerivedMessagesToo, Class<T> tClass, Action1<T> action) {
        register(recipient, null, receiveDerivedMessagesToo, action, tClass);
    }

    /**
     * see {@link Messenger#register(Object, Object, Action0)}
     *
     * @param recipient receiver of message
     * @param token     register with a unique token,when a messenger send a msg with same token,it will receive this msg
     * @param tClass    class of T for Action1
     * @param action    this action has one params that type of tClass
     * @param <T>       message data type
     */
    public <T> void register(Object recipient, Object token, Class<T> tClass, Action1<T> action) {
        register(recipient, token, false, action, tClass);
    }

    /**
     * see {@link Messenger#register(Object, Object, Class, Action1)}
     *
     * @param recipient                 receiver of message
     * @param token                     register with a unique token,when a messenger send a msg with same token,it will receive this msg
     * @param receiveDerivedMessagesToo whether derived class of recipient can receive the message
     * @param action                    this action has one params that type of tClass
     * @param tClass                    class of T for Action1
     * @param <T>                       message data type
     */
    public <T> void register(Object recipient, Object token, boolean receiveDerivedMessagesToo, Action1<T> action, Class<T> tClass) {

        HashMap<Type, List<WeakActionAndToken>> recipients;

        if (receiveDerivedMessagesToo) {
            if (recipientsOfSubclassesAction == null) {
                recipientsOfSubclassesAction = new HashMap<>();
            }

            recipients = recipientsOfSubclassesAction;
        } else {
            if (recipientsStrictAction == null) {
                recipientsStrictAction = new HashMap<>();
            }

            recipients = recipientsStrictAction;
        }

        List<WeakActionAndToken> list;

        if (!recipients.containsKey(tClass)) {
            list = new ArrayList<>();
            recipients.put(tClass, list);
        } else {
            list = recipients.get(tClass);
        }

        WeakAction weakAction = new WeakAction<T>(recipient, action);

        WeakActionAndToken item = new WeakActionAndToken(weakAction, token);
        list.add(item);
        cleanup();
    }


    private void cleanup() {
        try {
            Observable.just(true).observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
                cleanupList(recipientsOfSubclassesAction);
                cleanupList(recipientsStrictAction);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param token send with a unique token,when a receiver has register with same token,it will receive this msg
     */
    public void sendNoMsg(Object token) {
        sendToTargetOrType(null, token);
    }

    /**
     * send to recipient directly with has not any message
     *
     * @param target Messenger.getDefault().register(this, ..) in a activity,if target set this activity
     *               it will receive the message
     */
    public void sendNoMsgToTarget(Object target) {
        sendToTargetOrType(target.getClass(), null);
    }

    /**
     * send message to target with token,when a receiver has register with same token,it will receive this msg
     *
     * @param token  send with a unique token,when a receiver has register with same token,it will receive this msg
     * @param target send to recipient directly with has not any message,
     *               Messenger.getDefault().register(this, ..) in a activity,if target set this activity
     *               it will receive the message
     */
    public void sendNoMsgToTargetWithToken(Object token, Object target) {
        sendToTargetOrType(target.getClass(), token);
    }

    /**
     * send the message type of T, all receiver can receive the message
     *
     * @param message any object can to be a message
     * @param <T>     message data type
     */
    public <T> void send(T message) {
        sendToTargetOrType(message, null, null);
    }

    /**
     * send the message type of T, all receiver can receive the message
     *
     * @param message any object can to be a message
     * @param token   send with a unique token,when a receiver has register with same token,it will receive this message
     * @param <T>     message data type
     */
    public <T> void send(T message, Object token) {
        sendToTargetOrType(message, null, token);
    }

    /**
     * send message to recipient directly
     *
     * @param message any object can to be a message
     * @param target  send to recipient directly with has not any message,
     *                Messenger.getDefault().register(this, ..) in a activity,if target set this activity
     *                it will receive the message
     * @param <T>     message data type
     * @param <R>     target
     */
    public <T, R> void sendToTarget(T message, R target) {
        sendToTargetOrType(message, target.getClass(), null);
    }

    /**
     * Unregister the receiver such as:
     * Messenger.getDefault().unregister(this)" in onDestroy in the Activity is required avoid to memory leak!
     *
     * @param recipient receiver of message
     */
    public void unregister(Object recipient) {
        unregisterFromLists(recipient, recipientsOfSubclassesAction);
        unregisterFromLists(recipient, recipientsStrictAction);
        cleanup();
    }


    public void unregister(Object recipient, Object token) {
        unregisterFromLists(recipient, token, null, recipientsStrictAction);
        unregisterFromLists(recipient, token, null, recipientsOfSubclassesAction);
        cleanup();
    }

    public void unregister(Object recipient, Class clazz, Object token) {
        unregisterFromLists(recipient, token, null, recipientsStrictAction, clazz);
        unregisterFromLists(recipient, token, null, recipientsOfSubclassesAction, clazz);
        cleanup();
    }

    private static <T> void sendToList(
            T message,
            Collection<WeakActionAndToken> list,
            Type messageTargetType,
            Object token) {
        try {
            Observable.just(true).observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
                if (list != null) {
                    ArrayList<WeakActionAndToken> listClone = new ArrayList<>();
                    listClone.addAll(list);

                    for (WeakActionAndToken item : listClone) {
                        WeakAction executeAction = item.getAction();
                        if (executeAction != null
                                && item.getAction().isLive()
                                && item.getAction().getTarget() != null
                                && (messageTargetType == null
                                || item.getAction().getTarget().getClass() == messageTargetType
                                || classImplements(item.getAction().getTarget().getClass(), messageTargetType))
                                && ((item.getToken() == null && token == null)
                                || item.getToken() != null && item.getToken().equals(token))) {
                            executeAction.execute(message);
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void unregisterFromLists(Object recipient, HashMap<Type, List<WeakActionAndToken>> lists) {
        try {
            Observable.just(true).observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
                if (recipient == null
                        || lists == null
                        || lists.size() == 0) {
                    return;
                }
                synchronized (lists) {
                    for (Type messageType : lists.keySet()) {
                        for (WeakActionAndToken item : lists.get(messageType)) {
                            WeakAction weakAction = item.getAction();

                            if (weakAction != null
                                    && recipient == weakAction.getTarget()) {
                                weakAction.markForDeletion();
                            }
                        }
                    }
                }
                cleanupList(lists);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static <T> void unregisterFromLists(
            Object recipient,
            Action1<T> action,
            HashMap<Type, List<WeakActionAndToken>> lists,
            Class<T> tClass) {
        Type messageType = tClass;

        if (recipient == null
                || lists == null
                || lists.size() == 0
                || !lists.containsKey(messageType)) {
            return;
        }

        synchronized (lists) {
            for (WeakActionAndToken item : lists.get(messageType)) {
                WeakAction<T> weakActionCasted = (WeakAction<T>) item.getAction();

                if (weakActionCasted != null
                        && recipient == weakActionCasted.getTarget()
                        && (action == null
                        || action == weakActionCasted.getAction1())) {
                    item.getAction().markForDeletion();
                }
            }
        }
    }

    private static void unregisterFromLists(
            Object recipient,
            Action0 action,
            HashMap<Type, List<WeakActionAndToken>> lists
    ) {
        Type messageType = NotMsgType.class;

        if (recipient == null
                || lists == null
                || lists.size() == 0
                || !lists.containsKey(messageType)) {
            return;
        }

        synchronized (lists) {
            for (WeakActionAndToken item : lists.get(messageType)) {
                WeakAction weakActionCasted = item.getAction();

                if (weakActionCasted != null
                        && recipient == weakActionCasted.getTarget()
                        && (action == null
                        || action == weakActionCasted.getAction())) {
                    item.getAction().markForDeletion();
                }
            }
        }
    }


    private static <T> void unregisterFromLists(
            Object recipient,
            Object token,
            Action1<T> action,
            HashMap<Type, List<WeakActionAndToken>> lists, Class<T> tClass) {
        try {
            Type messageType = tClass;
            Observable.just(true).observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
                if (recipient == null
                        || lists == null
                        || lists.size() == 0
                        || !lists.containsKey(messageType)) {
                    return;
                }

                synchronized (lists) {
                    for (WeakActionAndToken item : lists.get(messageType)) {
                        WeakAction<T> weakActionCasted = (WeakAction<T>) item.getAction();

                        if (weakActionCasted != null
                                && recipient == weakActionCasted.getTarget()
                                && (action == null
                                || action == weakActionCasted.getAction1())
                                && (token == null
                                || token.equals(item.getToken()))) {
                            item.getAction().markForDeletion();
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void unregisterFromLists(
            Object recipient,
            Object token,
            Action0 action,
            HashMap<Type, List<WeakActionAndToken>> lists) {
        try {
            Observable.just(true).observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
                Type messageType = NotMsgType.class;

                if (recipient == null
                        || lists == null
                        || lists.size() == 0
                        || !lists.containsKey(messageType)) {
                    return;
                }

                synchronized (lists) {
                    for (WeakActionAndToken item : lists.get(messageType)) {
                        WeakAction weakActionCasted = item.getAction();

                        if (weakActionCasted != null
                                && recipient == weakActionCasted.getTarget()
                                && (action == null
                                || action == weakActionCasted.getAction())
                                && (token == null
                                || token.equals(item.getToken()))) {
                            item.getAction().markForDeletion();
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static boolean classImplements(Type instanceType, Type interfaceType) {
        if (interfaceType == null
                || instanceType == null) {
            return false;
        }
        Class[] interfaces = ((Class) instanceType).getInterfaces();
        for (Class currentInterface : interfaces) {
            if (currentInterface == interfaceType) {
                return true;
            }
        }

        return false;
    }

    private static void cleanupList(HashMap<Type, List<WeakActionAndToken>> lists) {
        if (lists == null) {
            return;
        }
        try {
            Observable.just(true).observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
                Iterator<Map.Entry<Type, List<WeakActionAndToken>>> iterator = lists.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Type, List<WeakActionAndToken>> entry = iterator.next();
                    List<WeakActionAndToken> itemList = lists.get(entry.getKey());
                    if (itemList != null) {
                        Iterator<WeakActionAndToken> items = itemList.iterator();
                        while (items.hasNext()) {
                            WeakActionAndToken item = items.next();
                            if (item.getAction() == null
                                    || !item.getAction().isLive()) {
                                items.remove();
                            }
                        }
                        if (itemList.size() == 0) {
                            iterator.remove();
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendToTargetOrType(Type messageTargetType, Object token) {
        Class messageType = NotMsgType.class;
        if (recipientsOfSubclassesAction != null) {
            List<Type> listClone = new ArrayList<>();
            listClone.addAll(recipientsOfSubclassesAction.keySet());
            for (Type type : listClone) {
                List<WeakActionAndToken> list = null;

                if (messageType == type
                        || ((Class) type).isAssignableFrom(messageType)
                        || classImplements(messageType, type)) {
                    list = recipientsOfSubclassesAction.get(type);
                }

                sendToList(list, messageTargetType, token);
            }
        }

        if (recipientsStrictAction != null) {
            if (recipientsStrictAction.containsKey(messageType)) {
                List<WeakActionAndToken> list = recipientsStrictAction.get(messageType);
                sendToList(list, messageTargetType, token);
            }
        }

        cleanup();
    }

    private static void sendToList(
            Collection<WeakActionAndToken> list,
            Type messageTargetType,
            Object token) {
        if (list != null) {
            ArrayList<WeakActionAndToken> listClone = new ArrayList<>();
            listClone.addAll(list);
            try {
                for (WeakActionAndToken item : listClone) {
                    WeakAction executeAction = item.getAction();
                    if (executeAction != null
                            && item.getAction().isLive()
                            && item.getAction().getTarget() != null
                            && (messageTargetType == null
                            || item.getAction().getTarget().getClass() == messageTargetType
                            || classImplements(item.getAction().getTarget().getClass(), messageTargetType))
                            && ((item.getToken() == null && token == null)
                            || item.getToken() != null && item.getToken().equals(token))) {
                        executeAction.execute();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private <T> void sendToTargetOrType(T message, Type messageTargetType, Object token) {
        Class messageType = message.getClass();


        if (recipientsOfSubclassesAction != null) {
            List<Type> listClone = new ArrayList<>();
            listClone.addAll(recipientsOfSubclassesAction.keySet());
            for (Type type : listClone) {
                List<WeakActionAndToken> list = null;

                if (messageType == type
                        || ((Class) type).isAssignableFrom(messageType)
                        || classImplements(messageType, type)) {
                    list = recipientsOfSubclassesAction.get(type);
                }

                sendToList(message, list, messageTargetType, token);
            }
        }

        if (recipientsStrictAction != null) {
            if (recipientsStrictAction.containsKey(messageType)) {
                List<WeakActionAndToken> list = recipientsStrictAction.get(messageType);
                sendToList(message, list, messageTargetType, token);
            }
        }

        cleanup();
    }

    public boolean isRegisterMessenger(Object recipient) {
        return isRegister(recipient, recipientsStrictAction) || isRegister(recipient, recipientsOfSubclassesAction);
    }

    private static boolean isRegister(Object recipient, HashMap<Type, List<WeakActionAndToken>> hashMap) {
        try {
            if (recipient == null
                    || hashMap == null
                    || hashMap.size() == 0) {
                return false;
            }
            synchronized (hashMap) {
                for (List<WeakActionAndToken> list : hashMap.values()) {
                    for (WeakActionAndToken item : list) {
                        WeakAction weakAction = item.getAction();

                        if (weakAction != null
                                && recipient.getClass().getName().equals(weakAction.getTarget().getClass().getName())) {
                            return true;
                        }
                    }
                }
            }
            cleanupList(hashMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private class WeakActionAndToken {
        private WeakAction action;
        private Object token;

        public WeakActionAndToken(WeakAction action, Object token) {
            this.action = action;
            this.token = token;
        }

        public WeakAction getAction() {
            return action;
        }

        public void setAction(WeakAction action) {
            this.action = action;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }
    }

    public static class NotMsgType {

    }
}
