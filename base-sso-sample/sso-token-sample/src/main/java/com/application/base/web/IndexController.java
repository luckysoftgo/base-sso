package com.application.base.web;

import com.application.base.core.config.SsoConfig;
import com.application.base.core.entity.ResultDataVO;
import com.application.base.core.user.SsoUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 孤狼
 * @desc: index 进入
 */
@RestController
public class IndexController {
	
	@RequestMapping("/")
	@ResponseBody
	public ResultDataVO<SsoUser> index(HttpServletRequest request) {
		SsoUser user = (SsoUser) request.getAttribute(SsoConfig.SSO_USER);
		return new ResultDataVO<SsoUser>(user);
	}
}
