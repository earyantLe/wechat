package com.earyant.common.security;

import com.earyant.CommonConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class CommonFilter extends OncePerRequestFilter {

	public Log log = LogFactory.getLog(getClass());
	
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext wac = null;
		wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}
	
	public void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		/*Cookie ssologin = CommonUtils.getCookieByName(request, "ssologin");
		//获取访问者ip
		String ip = CommonUtils.getIpAddr(request);
		//密钥
		if( ssologin != null){
			CustAllInfo obj = (CustAllInfo) session.getAttribute(AppConstants.LOGIN_USER_PERSONAL);
			@SuppressWarnings("deprecation")
			String ssoStr = URLDecoder.decode(ssologin.getValue());
			if(obj==null){
				String[] arr = null;
				try {
					//获取cookie里解密后的用户名，密码，ip
					arr = CommonUtils.ees3DecodeECB(Commonconstant.SSO_KEY, ssoStr).split(",");
				} catch (Exception e) {
					e.printStackTrace();
				}
				CustLoginctrl clc=new CustLoginctrl();
				clc.setLoginId(arr[0]);
				clc.setLoginPwd(arr[1]);
				//注入service
				 init();
				clc=custService.validationLogin(clc);
				if(clc.getLoginState().equals("success") && ip.equals(arr[2])){
					CustAllInfo cai=new CustAllInfo();
					cai.setCustFlag(clc.getCustFlag());
					cai.setLoginId(clc.getLoginId());
					cai.setLoginName(clc.getLoginName());
					cai.setCustId(clc.getCustId());
					session.setAttribute(AppConstants.LOGIN_USER_PERSONAL, cai);
					session.setAttribute("LoginId",clc.getLoginId());
					session.setAttribute("CustId",clc.getCustId() );
					session.setAttribute("CustFlag",clc.getCustFlag());
				}
			}
		}*/		
		/** 个性化过滤器 *******/
		String requestUri = request.getRequestURI();
		requestUri = requestUri.substring(CommonConstants.APP_NAME.length() + 2);
		request.setCharacterEncoding("utf-8");
		//存在这些词，不拦截
	/*	if (requestUri.indexOf("common") >= 0 || requestUri.indexOf("jsp") >= 0
				|| requestUri.indexOf("Login") >= 0
				|| requestUri.indexOf("oper") >= 0
						|| requestUri.indexOf("services") >= 0

				|| requestUri.length() <=1) {
			chain.doFilter(request, response);
		}*/
		if(requestUri.indexOf("common")<0 
        		&& requestUri.indexOf("jsp")<0
        		&& requestUri.indexOf("Login")<0
        		&& requestUri.indexOf("oper")<0
        		&& requestUri.indexOf("index")<0
        		&& requestUri.indexOf("services")<0
        		&& requestUri.length()>0){
        	//response.sendRedirect(request.getContextPath()+"/login.action");
        }else{
        	chain.doFilter(request, response);
        }
	}
}
