/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.ctsig.common.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

/**
 * 表单验证（包含验证码）过滤类
 * @author ThinkGem
 * @version 2013-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		String captcha = getCaptcha(request);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
	}

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