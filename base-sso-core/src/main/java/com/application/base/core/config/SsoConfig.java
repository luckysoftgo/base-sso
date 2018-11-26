package com.application.base.core.config;

import com.application.base.core.entity.ResultDataVO;

/**
 * @Author: 孤狼
 * @desc: sso config 设置
 */
public class SsoConfig {
	
	/**
	 * sso sessionid, between browser and sso-server (web + token client)
	 */
	public static final String SSO_SESSIONID = "base_sso_sessionId";
	
	/**
	 * redirect url (web client)
	 */
	public static final String REDIRECT_URL = "redirect_url";
	
	/**
	 * sso user, request attribute (web client)
	 */
	public static final String SSO_USER = "base_sso_user";
	
	/**
	 * sso server address (web + token client)
	 */
	public static final String SSO_SERVER = "sso_server";
	
	/**
	 * login url, server relative path (web client)
	 */
	public static final String SSO_LOGIN = "/login";
	/**
	 * logout url, server relative path (web client)
	 */
	public static final String SSO_LOGOUT = "/logout";
	
	/**
	 * logout path, client relatice path
	 */
	public static final String SSO_LOGOUT_PATH = "SSO_LOGOUT_PATH";
	
	/**
	 * excluded paths, client relatice path, include path can be set by "filter-mapping"
	 */
	public static final String SSO_EXCLUDED_PATHS = "SSO_EXCLUDED_PATHS";
	
	
	/**
	 * login fail result
	 */
	public static final ResultDataVO<String> SSO_LOGIN_FAIL_RESULT = new ResultDataVO("501", "sso not login.");
}
