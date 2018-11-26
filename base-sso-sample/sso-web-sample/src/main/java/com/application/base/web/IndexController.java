package com.application.base.web;

import com.application.base.core.config.SsoConfig;
import com.application.base.core.entity.ResultDataVO;
import com.application.base.core.user.SsoUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 孤狼
 * @desc:
 */
@RestController
public class IndexController {
	
	@RequestMapping("/")
	public String index(Model model, HttpServletRequest request) {
		SsoUser user = (SsoUser) request.getAttribute(SsoConfig.SSO_USER);
		model.addAttribute("xxlUser", user);
		return "index";
	}
	
	@RequestMapping("/json")
	@ResponseBody
	public ResultDataVO<SsoUser> json(Model model, HttpServletRequest request) {
		SsoUser user = (SsoUser) request.getAttribute(SsoConfig.SSO_USER);
		return new ResultDataVO(user);
	}
	
}
