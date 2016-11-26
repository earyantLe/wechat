package com.earyant.sys.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.earyant.common.utils.MD5S;
import com.earyant.sys.user.domain.SysUser;
import com.earyant.sys.user.service.UserService;


@Controller
public class UserController {
	/**
	 * Logger for this class
	 */
//	private static final Logger logger = Logger.getLogger(UserController.class);
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	  
	@Resource
	  private UserService userService;
	  
	  /**
	   * 跳转到用户列表
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequiresRoles("admin")
	  @RequestMapping("/oper/user/tolistUserInfo.do")
	  public String tolistUserInfo(HttpServletRequest request,HttpServletResponse response){
		  return "sys/listUserInfo";
	  }
	  
	  @RequestMapping("/oper/user/listUserInfo.do")
	  @ResponseBody
	  public Map<String, Object> listUserInfo(SysUser user,HttpServletRequest request,HttpServletResponse response){
		/* ps.setPageSize(rows);
		 ps.setCurPage(page);*/
		 List<SysUser> userList =userService.selectByUserCondition(user);
		 Map<String, Object> data = new HashMap<String, Object>();
		 data.put("total", userList.size());
		 data.put("rows", userList);
		 return data;
	  }
    
	  @RequestMapping("/oper/user/toaddUserInfoPage.do")
	  public String toaddUserInfoPage(HttpServletRequest request,HttpServletResponse response){
		  return "sys/addUserInfo";
	  }
	  
	  /**
	   * 用户添加
	   * @param user
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/oper/user/addUserInfo.do")
	  @ResponseBody
	  public String addUserInfo(SysUser user,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  try {
			  user.setLoginpwd(MD5S.MD5(user.getLoginpwd()));
			  userService.insertSelective(user);
			flag="true";
		} catch (Exception e) {
			flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  /**
	   * 跳转到用户修改页面
	   * @param id
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/oper/user/toupdateuserInfoPage.do")
	  public String toupdateUserInfoPage(int id,HttpServletRequest request,HttpServletResponse response){
		  SysUser user=userService.selectByPrimaryKey(id);
		  request.setAttribute("user", user);
		  return "sys/updateUserInfo";
	  }
	  
	  /**
	   * 修改用户
	   * @param user
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/oper/user/updateUserInfo.do")
	  @ResponseBody
	  public String updateUserInfo(String yuanmima,SysUser user,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  try {
			  if(yuanmima.equals(user.getLoginpwd())){
				  user.setLoginpwd(null);
			  }else{
				  user.setLoginpwd(MD5S.MD5(user.getLoginpwd()));
			  }
			  userService.updateByPrimaryKeySelective(user);
			flag="true";
		} catch (Exception e) {
			flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  /**
	   * 删除用户
	   * @param ids
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/oper/user/deleteUserInfo.do")
	  @ResponseBody
	  public String deleteUserInfo(String ids,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  try {
			String[] idsarray=ids.split(",");
			  for(String id:idsarray){
				  userService.deleteByPrimaryKey(Integer.parseInt(id));
			  }
			  flag="true";
		} catch (NumberFormatException e) {
			 flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  /**
	   * 用戶登录
	   * @param ctrl
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/oper/user/validationUser.do")
	  public void loginmerchInfo(SysUser user,String checkcode,HttpServletRequest request,HttpServletResponse response){
		  HttpSession session=request.getSession();
		  String keyCode=(String) (session.getAttribute("key")==null?"":session.getAttribute("key"));
		  String path=request.getContextPath();
			  try {
				if(StringUtils.isBlank(checkcode)){
					 // request.getRequestDispatcher(path+"/login/login.jsp").forward(request, response);
					response.sendRedirect(path+"/login.jsp");
					  return;
				  }
				  if(StringUtils.isNotBlank(checkcode)&&!keyCode.equals(checkcode)){
					  request.setAttribute("flag",1); //1 表示验证码输入错误
					  request.getRequestDispatcher("/login.jsp").forward(request, response);
					  //response.sendRedirect(path+"/login/login.jsp");
					  return;
				  }
				  //基于shiro的登录
				  Subject subject=SecurityUtils.getSubject();
				  AuthenticationToken token=new UsernamePasswordToken(user.getLoginname(), MD5S.MD5(user.getLoginpwd()) ,true);
//				  System.out.println(MD5S.MD5("123456"));
				  try {
					subject.login(token);
					SysUser cc = (SysUser) subject.getPrincipal();
					request.getSession().setAttribute("ctrlUser", cc);
					response.sendRedirect(path+"/oper/user/userIndex.do");
					//request.getRequestDispatcher("/admin/merchInfo/merchIndex.do").forward(request, response);
				} catch (AuthenticationException e) {
					 
					  String simplename = e.getClass().getSimpleName();  
					  if("UnknownAccountException".equals(simplename)){  
					    request.setAttribute("flag", 2);//2 表示用户名输入错误
					   }  
					  if("IncorrectCredentialsException".equals(simplename)){  
						  request.setAttribute("flag", 3);//3表示密码输入错误
						 }  
					  logger.error("用户登录shiro验证返回异常类型"+simplename,e);
					  //response.sendRedirect(path+"/login/login.jsp?flag="+flag);
					  request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
	  }
	  
	  @RequestMapping("/oper/user/logout.do")
	  public void logout(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		  Subject sub=SecurityUtils.getSubject();
		  sub.logout();
		  request.getRequestDispatcher("/login.jsp").forward(request, response);
	  }
	  
	  /**
	   * 登录成功
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/oper/user/userIndex.do")
	  public String merchIndex(HttpServletRequest request,HttpServletResponse response){
		  return "index";
	  }
	  /**
		 * 跳转到系统公告页面
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("/oper/user/toHome.do")
		public String tohomePage(HttpServletRequest request,
				HttpServletResponse response) {
			return "home";
		}
		/**
		 * 跳转到系统公告页面
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("/oper/user/topermissionInfo.do")
		public String topermissionInfo(HttpServletRequest request,
				HttpServletResponse response) {
			return "sys/permissionInfo";
		}
		
}
