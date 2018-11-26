package com.application.base.core.filter;

import com.application.base.core.config.SsoConfig;
import com.application.base.core.login.SsoTokenLoginHelper;
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
 * @desc: token filter.
 */
public class SsoTokenFilter extends HttpServlet implements Filter {
    
    private static Logger logger = LoggerFactory.getLogger(SsoTokenFilter.class);

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private String ssoServer;
    private String logoutPath;
    private String excludedPaths;

    @Override
    public void init(FilterConfig filterSsoConfigig) throws ServletException {
        ssoServer = filterSsoConfigig.getInitParameter(SsoConfig.SSO_SERVER);
        logoutPath = filterSsoConfigig.getInitParameter(SsoConfig.SSO_LOGOUT_PATH);
        excludedPaths = filterSsoConfigig.getInitParameter(SsoConfig.SSO_EXCLUDED_PATHS);
        logger.info("SsoTokenFilter init.");
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
        // logout filter
        if (logoutPath!=null && logoutPath.trim().length()>0 && logoutPath.equals(servletPath)) {
            // logout
            SsoTokenLoginHelper.logout(req);
            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println("{\"code\":"+200+", \"msg\":\"\"}");
            return;
        }

        // login filter
        SsoUser user = SsoTokenLoginHelper.loginCheck(req);
        if (user == null) {
            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println("{\"code\":"+SsoConfig.SSO_LOGIN_FAIL_RESULT.getCode()+", \"msg\":\""+ SsoConfig.SSO_LOGIN_FAIL_RESULT.getMsg() +"\"}");
            return;
        }
        // ser sso user
        request.setAttribute(SsoConfig.SSO_USER, user);
        // already login, allow
        chain.doFilter(request, response);
        return;
    }
}
