package com.bob.common.utils;

import com.bob.common.config.CommonMessageToken;
import com.bob.common.messenger.Messenger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by gaozhidong on 2019/4/22
 * 这个类的主要服务于第三方会话列表：
 * 1.保存第三会话列表的群头像组
 * 2.官方群的判断
 */
public class GroupUtilsProxy {

    private static GroupUtilsProxy instance;
    private List<String> urls;
    private Map<Long, List<String>> urlLib;
    private long tribeId;
    private Map<Long, Boolean> isOfficial;
    private long userId;
    private Map<Long, Long> ownerIds;
    private Map<Long, List<Long>> adminIds;

    private GroupUtilsProxy() {
        urlLib = new HashMap<>();
        isOfficial = new HashMap<>();
        ownerIds = new HashMap<>();
        adminIds = new HashMap<>();
    }

    public static GroupUtilsProxy getInstance() {
        if (instance == null) {
            synchronized (GroupUtilsProxy.class) {
                if (instance == null) {
                    instance = new GroupUtilsProxy();
                }
            }
        }
        return instance;
    }

    /**
     * 存储群主id
     */
    public void storeOwnerId(long groupId, long ownerId){
        if (ownerIds.keySet().contains(groupId)){
            ownerIds.remove(groupId);
        }
        ownerIds.put(groupId, ownerId);
    }

    /**
     * 存储管理员id
     */
    public void storeAdminId(long groupId, List<Long> ads){
        if (adminIds.keySet().contains(groupId)){
            adminIds.remove(groupId);
        }
        adminIds.put(groupId, ads);
        Messenger.getDefault().sendNoMsg(CommonMessageToken.TOKEN_REFRESH_MESSAGE_LIST);

    }

    public void storeUrls(long groupId, List<String> urls) {
        if (urlLib.keySet().contains(groupId)) {
            urlLib.remove(groupId);
        }
        urlLib.put(groupId, urls);
    }

    public boolean judgeOwner(long groupId, long userId){
        if (ownerIds.keySet().contains(groupId)){
            if (ownerIds.get(groupId) == userId)
                return true;
        }
        return false;
    }

    public boolean judgeAdmin(long groupId, long userId){
        if (adminIds.keySet().contains(groupId)){
            if (adminIds.get(groupId).contains(userId))
                return true;
        }
        return false;
    }

    public List<String> getUrls() {
        return urls;
    }

    public List<String> getUrls(long groupId) {
        return urlLib.get(groupId);
    }

    public void setTribeId(long tribeId) {
        this.tribeId = tribeId;
    }

    public boolean isGroup(long groupId) {
        return (groupId != tribeId);
    }


    public void setOfficial(long groupId, boolean official) {
        isOfficial.put(groupId, official);
    }

    public boolean isOfficial(long groupId) {
        if (isOfficial.keySet().contains(groupId)) {
            return isOfficial.get(groupId);

        } else {
            return false;
        }
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
