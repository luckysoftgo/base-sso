package com.application.base.core.filter;

import com.application.base.core.config.SsoConfig;
import com.application.base.core.login.SsoWebLoginHelper;
import com.application.base.core.path.impl.AntPathMatcher;
import com.application.base.core.user.SsoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 孤狼
 * @desc: web filter .
 */
public class SsoWebFilter extends HttpServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(SsoWebFilter.class);

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private String ssoServer;
    private String logoutPath;
    private String excludedPaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ssoServer = filterConfig.getInitParameter(SsoConfig.SSO_SERVER);
        logoutPath = filterConfig.getInitParameter(SsoConfig.SSO_LOGOUT_PATH);
        excludedPaths = filterConfig.getInitParameter(SsoConfig.SSO_EXCLUDED_PATHS);
        logger.info("SsoWebFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // make url
        String servletPath = req.getServletPath();
        // excluded path check
        if (excludedPaths!=null && excludedPaths.trim().length()>0) {
            for (String excludedPath:excludedPaths.split(",")) {
                String uriPattern = excludedPath.trim();
                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    // excluded path, allow
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        // logout path check
        if (logoutPath!=null && logoutPath.trim().length()>0 && logoutPath.equals(servletPath)) {
            // remove cookie
            SsoWebLoginHelper.removeSessionIdByCookie(req, res);
            // redirect logout
            String logoutPageUrl = ssoServer.concat(SsoConfig.SSO_LOGOUT);
            res.sendRedirect(logoutPageUrl);
            return;
        }
        // valid login user, cookie + redirect
        SsoUser user = SsoWebLoginHelper.loginCheck(req, res);
        // valid login fail
        if (user == null) {
            String header = req.getHeader("content-type");
            boolean isJson=  header!=null && header.contains("json");
            if (isJson) {
                // json msg
                res.setContentType("application/json;charset=utf-8");
                res.getWriter().println("{\"code\":"+SsoConfig.SSO_LOGIN_FAIL_RESULT.getCode()+", \"msg\":\""+ SsoConfig.SSO_LOGIN_FAIL_RESULT.getMsg() +"\"}");
                return;
            } else {
                // total link
                String link = req.getRequestURL().toString();
                // redirect logout
                String loginPageUrl = ssoServer.concat(SsoConfig.SSO_LOGIN) + "?" + SsoConfig.REDIRECT_URL + "=" + link;
                res.sendRedirect(loginPageUrl);
                return;
            }
        }
        // ser sso user
        request.setAttribute(SsoConfig.SSO_USER, user);
        // already login, allow
        chain.doFilter(request, response);
        return;
    }

}
