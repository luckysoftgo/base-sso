package com.application.base.config;

import com.application.base.core.config.SsoConfig;
import com.application.base.core.filter.SsoTokenFilter;
import com.application.base.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 孤狼
 * @desc:
 */
@Configuration
public class WebSsoConfig implements DisposableBean {
	
	@Value("${sso.server}")
	private String soServer;
	
	@Value("${sso.logout.path}")
	private String soLogoutPath;
	
	@Value("${sso.redis.address}")
	private String soRedisAddress;
	
	@Value("${sso.redis.password}")
	private String soRedisPassword;
	
	@Value("${sso.excluded.paths}")
	private String soExcludedPaths;
	
	
	@Bean
	public FilterRegistrationBean xxlSsoFilterRegistration() {
		
		// sso, redis init
		JedisUtil.init(soRedisAddress, null, soRedisPassword);
		
		// sso, filter init
		FilterRegistrationBean registration = new FilterRegistrationBean();
		
		registration.setName("soWebFilter");
		registration.setOrder(1);
		registration.addUrlPatterns("/*");
		registration.setFilter(new SsoTokenFilter());
		registration.addInitParameter(SsoConfig.SSO_SERVER, soServer);
		registration.addInitParameter(SsoConfig.SSO_LOGOUT_PATH, soLogoutPath);
		registration.addInitParameter(SsoConfig.SSO_EXCLUDED_PATHS, soExcludedPaths);
		
		return registration;
	}
	
	@Override
	public void destroy() throws Exception {
		// sso, redis close
		JedisUtil.close();
	}
}
