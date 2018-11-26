package com.application.base.core.login;

import com.application.base.core.config.SsoConfig;
import com.application.base.core.store.SsoLoginStore;
import com.application.base.core.store.SsoSessionIdHelper;
import com.application.base.core.user.SsoUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 孤狼
 * @desc: 登录token
 */
public class SsoTokenLoginHelper {

    /**
     * client login
     *
     * @param sessionId
     * @param user
     */
    public static void login(String sessionId,SsoUser user) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("parseStoreKey Fail, sessionId:" + sessionId);
        }
        SsoLoginStore.put(storeKey,user);
    }

    /**
     * client logout
     *
     * @param sessionId
     */
    public static void logout(String sessionId) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return;
        }

        SsoLoginStore.remove(storeKey);
    }
    /**
     * client logout
     *
     * @param request
     */
    public static void logout(HttpServletRequest request) {
        String headerSessionId = request.getHeader(SsoConfig.SSO_SESSIONID);
        logout(headerSessionId);
    }

    /**
     * login check
     *
     * @param sessionId
     * @return
     */
    public static SsoUser loginCheck(String  sessionId){

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return null;
        }

        SsoUser user = SsoLoginStore.get(storeKey);
        if (user != null) {
            String version = SsoSessionIdHelper.parseVersion(sessionId);
            if (user.getVersion().equals(version)) {
                // After the expiration time has passed half, Auto refresh
                if ((System.currentTimeMillis() - user.getExpireFreshTime()) > user.getExpireMinite()/2) {
                    user.setExpireFreshTime(System.currentTimeMillis());
                    SsoLoginStore.put(storeKey, user);
                }
                return user;
            }
        }
        return null;
    }

    /**
     * login check
     *
     * @param request
     * @return
     */
    public static SsoUser loginCheck(HttpServletRequest request){
        String headerSessionId = request.getHeader(SsoConfig.SSO_SESSIONID);
        return loginCheck(headerSessionId);
    }
}
