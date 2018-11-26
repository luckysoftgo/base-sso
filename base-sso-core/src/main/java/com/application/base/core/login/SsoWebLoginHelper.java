package com.application.base.core.login;

import com.application.base.core.config.SsoConfig;
import com.application.base.core.store.SsoLoginStore;
import com.application.base.core.store.SsoSessionIdHelper;
import com.application.base.core.user.SsoUser;
import com.application.base.core.util.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 孤狼
 * @desc: 登录token
 */
public class SsoWebLoginHelper {

    /**
     * client login
     *
     * @param response
     * @param sessionId
     * @param ifRemember    true: cookie not expire, false: expire when browser close （server cookie）
     * @param user
     */
    public static void login(HttpServletResponse response,
                             String sessionId,
                             SsoUser user,
                             boolean ifRemember) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("parseStoreKey Fail, sessionId:" + sessionId);
        }
        SsoLoginStore.put(storeKey, user);
        CookieUtil.set(response, SsoConfig.SSO_SESSIONID, sessionId, ifRemember);
    }

    /**
     * client logout
     *
     * @param request
     * @param response
     */
    public static void logout(HttpServletRequest request,
                              HttpServletResponse response) {
        String cookieSessionId = CookieUtil.getValue(request, SsoConfig.SSO_SESSIONID);
        if (cookieSessionId==null) {
            return;
        }

        String storeKey = SsoSessionIdHelper.parseStoreKey(cookieSessionId);
        if (storeKey != null) {
            SsoLoginStore.remove(storeKey);
        }
        CookieUtil.remove(request, response, SsoConfig.SSO_SESSIONID);
    }



    /**
     * login check
     *
     * @param request
     * @param response
     * @return
     */
    public static SsoUser loginCheck(HttpServletRequest request, HttpServletResponse response){
        String cookieSessionId = CookieUtil.getValue(request, SsoConfig.SSO_SESSIONID);
        // cookie user
        SsoUser user = SsoTokenLoginHelper.loginCheck(cookieSessionId);
        if (user != null) {
            return user;
        }
        // redirect user
        // remove old cookie
        SsoWebLoginHelper.removeSessionIdByCookie(request, response);
        // set new cookie
        String paramSessionId = request.getParameter(SsoConfig.SSO_SESSIONID);
        user = SsoTokenLoginHelper.loginCheck(paramSessionId);
        if (user != null) {
            // expire when browser close （client cookie）
            CookieUtil.set(response, SsoConfig.SSO_SESSIONID, paramSessionId, false);
            return user;
        }
        return null;
    }

    /**
     * client logout, cookie only
     *
     * @param request
     * @param response
     */
    public static void removeSessionIdByCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, SsoConfig.SSO_SESSIONID);
    }

    /**
     * get sessionid by cookie
     *
     * @param request
     * @return
     */
    public static String getSessionIdByCookie(HttpServletRequest request) {
        String cookieSessionId = CookieUtil.getValue(request, SsoConfig.SSO_SESSIONID);
        return cookieSessionId;
    }
}
