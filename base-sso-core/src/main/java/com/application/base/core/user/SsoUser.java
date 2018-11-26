package com.application.base.core.user;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: 孤狼
 * @desc: 登录标识.
 */
public class SsoUser implements Serializable {
    
    private static final long serialVersionUID = 42L;
    // field
    private String userId;
    private String userName;
    private Map<String, String> pluginInfo;

    private String version;
    private int expireMinite;
    private long expireFreshTime;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Map<String, String> getPluginInfo() {
        return pluginInfo;
    }
    
    public void setPluginInfo(Map<String, String> pluginInfo) {
        this.pluginInfo = pluginInfo;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public int getExpireMinite() {
        return expireMinite;
    }
    
    public void setExpireMinite(int expireMinite) {
        this.expireMinite = expireMinite;
    }
    
    public long getExpireFreshTime() {
        return expireFreshTime;
    }
    
    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }
}
