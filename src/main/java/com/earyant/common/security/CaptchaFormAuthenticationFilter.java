package com.earyant.common.security;
 
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
 
/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> 
 * Version 1.1.0
 * @since 2012-8-7 上午9:20:26
 */
 
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
 
    private String captchaParam = "val";
    
 
    public String getCaptchaParam() {
        return captchaParam;
    }
 
    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }
 
    @Override
    protected AuthenticationToken createToken(ServletRequest request,
            ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        char[] pa=  new char[2];
        String host = getHost(request);
        return new UsernamePasswordToken(username,  pa , rememberMe,
                host, captcha);
       // return null;
    }
 
    /**
     * 覆盖默认实现，用sendRedirect直接跳出框架，以免造成js框架重复加载js出错。
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception  
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
        //issueSuccessRedirect(request, response);
        //we handled the success redirect directly, prevent the chain from continuing:
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) 
                || request.getParameter("ajax") == null) {// 不是ajax请求
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
        } else {
            httpServletRequest.getRequestDispatcher("/login/timeout/success").forward(httpServletRequest, httpServletResponse);
        }
         
        return false;
    }
}