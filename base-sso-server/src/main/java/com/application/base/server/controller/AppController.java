package com.application.base.server.controller;

import com.application.base.core.entity.ResultDataVO;
import com.application.base.core.login.SsoTokenLoginHelper;
import com.application.base.core.store.SsoLoginStore;
import com.application.base.core.store.SsoSessionIdHelper;
import com.application.base.core.user.SsoUser;
import com.application.base.server.core.model.UserInfo;
import com.application.base.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @author 孤狼
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;

    /**
     * Login
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResultDataVO<String> login(String username, String password) {
        // valid login
        ResultDataVO<UserInfo> result = userService.findUser(username, password);
        if (!result.getCode().equals("200")) {
            return new ResultDataVO<String>(result.getCode(), result.getMsg());
        }

        // 1、make xxl-sso user
        SsoUser user = new SsoUser();
        user.setUserId(String.valueOf(result.getData().getUserId()));
        user.setUserName(result.getData().getUserName());
        user.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
        user.setExpireFreshTime(System.currentTimeMillis());


        // 2、generate sessionId + storeKey
        String sessionId = SsoSessionIdHelper.makeSessionId(user);

        // 3、login, store storeKey
        SsoTokenLoginHelper.login(sessionId, user);

        // 4、return sessionId
        return new ResultDataVO<String>(sessionId);
    }


    /**
     * Logout
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ResultDataVO<String> logout(String sessionId) {
        // logout, remove storeKey
        SsoTokenLoginHelper.logout(sessionId);
        return new ResultDataVO("logout");
    }

    /**
     * logincheck
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logincheck")
    @ResponseBody
    public ResultDataVO<SsoUser> logincheck(String sessionId) {
        // logout
        SsoUser user = SsoTokenLoginHelper.loginCheck(sessionId);
        if (user == null) {
            return new ResultDataVO<SsoUser>("500", "sso not login.");
        }
        return new ResultDataVO<SsoUser>(user);
    }

}