package com.application.base.core.store;

import com.application.base.core.config.SsoConfig;
import com.application.base.core.user.SsoUser;
import com.application.base.core.util.JedisUtil;

/**
 * @Author: 孤狼
 * @desc: sso config 设置
 */
public class SsoLoginStore {
    
    // 1440 minite, 24 hour
    private static int redisExpireMinite = 1440;
    public static void setRedisExpireMinite(int redisExpireMinite) {
        if (redisExpireMinite < 30) {
            redisExpireMinite = 30;
        }
        SsoLoginStore.redisExpireMinite = redisExpireMinite;
    }
    public static int getRedisExpireMinite() {
        return redisExpireMinite;
    }

    /**
     * get
     *
     * @param storeKey
     * @return
     */
    public static SsoUser get(String storeKey) {
        String redisKey = redisKey(storeKey);
        Object objectValue = JedisUtil.getObjectValue(redisKey);
        if (objectValue != null) {
            SsoUser user = (SsoUser) objectValue;
            return user;
        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public static void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        JedisUtil.del(redisKey);
    }

    /**
     * put
     *
     * @param storeKey
     * @param user
     */
    public static void put(String storeKey, SsoUser user) {
        String redisKey = redisKey(storeKey);
        // minite to second
        JedisUtil.setObjectValue(redisKey, user, redisExpireMinite * 60);
    }

    private static String redisKey(String sessionId){
        return SsoConfig.SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
