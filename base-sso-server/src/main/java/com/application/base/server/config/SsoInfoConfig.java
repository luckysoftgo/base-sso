package com.application.base.server.config;

import com.application.base.core.store.SsoLoginStore;
import com.application.base.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 孤狼.
 * @desc: 启动配置文件.
 */
@Configuration
public class SsoInfoConfig implements InitializingBean, DisposableBean {
	
	@Value("${sso.redis.address}")
	private String redisAddress;
	@Value("${sso.redis.password}")
	private String redisPassWord;
	
	@Value("${sso.redis.expire.minite}")
	private int redisExpireMinite;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		SsoLoginStore.setRedisExpireMinite(redisExpireMinite);
		//根据实际情况进行设置
		JedisUtil.init(redisAddress,null,redisPassWord);
	}
	
	@Override
	public void destroy() throws Exception {
		JedisUtil.close();
	}
}
