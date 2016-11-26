/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.ctsig.common.security;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.ctsig.sys.rolePrivilege.controller.vo.RolePrivilegeVo;
import com.ctsig.sys.rolePrivilege.service.RolePrivilegeService;
import com.ctsig.sys.user.domain.SysUser;
import com.ctsig.sys.user.service.UserService;
import com.ctsig.sys.userRole.controller.vo.UserRoleVo;
import com.ctsig.sys.userRole.service.UserRoleService;



/**
 * 系统安全认证实现类
 * @author ThinkGem
 * @version 2013-5-29
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SystemAuthorizingRealm.class);

	@Resource
	private UserService userService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private RolePrivilegeService rolePrivilegeService;
	
    /**
     * 授权
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		logger.info("授权------------------");
		//获得当前登录用户。根据当前登录用户，查询对应角色。
		Subject subject =SecurityUtils.getSubject();
		SysUser user=(SysUser) subject.getPrincipal();
		SimpleAuthorizationInfo authorization =new SimpleAuthorizationInfo();
		//1.user，user_role，roleId 三表关联表 根据userId 查找roleCode(roleCode,是applicationContext-shiro.xml中配置的roles[]里面的内容),
		UserRoleVo vo=new UserRoleVo();
		vo.setUserid(user.getId());
		List<UserRoleVo> urList=userRoleService.selectByRoleByUserId(vo);
		if(!urList.isEmpty()){
			for(int i=0;i<urList.size();i++){
				authorization.addRole(urList.get(i).getRolecode());
				RolePrivilegeVo voo=new RolePrivilegeVo();
				voo.setRoleid(urList.get(i).getRoleid());
				List<RolePrivilegeVo> rpv=rolePrivilegeService.selectRolePrivilegeVoByRoleId(voo);
				voo=null;
				if(!rpv.isEmpty()){
					for(int j=0;j<rpv.size();j++){
						authorization.addStringPermission(rpv.get(j).getPrivatecode());
					}
				}
			}
		}
		return authorization;
		
	}
    
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.先使用用户名去数据库查询，如果用户名不存在，return null
		//2.将密码返回给securityManager，自动和token的密码比较，并且在subject中记录登录状态
		UsernamePasswordToken usernamepwdToken=(UsernamePasswordToken)token; 
		SysUser ctrl=new SysUser();
		ctrl.setLoginname(usernamepwdToken.getUsername());
		SysUser cl =userService.selectUserBynameANDpwd(ctrl);
		if(cl==null){
			return null;
		}
		//用数据库的用户名和密码去和输入的用户名密码去匹配
		AuthenticationInfo authenticationInfo= new SimpleAuthenticationInfo(cl,cl.getLoginpwd(), super.getName());
		return authenticationInfo;
	}

}
