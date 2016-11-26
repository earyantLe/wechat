package com.ctsig.sys.userRole.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctsig.sys.userRole.domain.UserRole;

import com.ctsig.sys.userRole.service.UserRoleService;

@Controller
@RequestMapping("/oper/userRole")
public class UserRoleController {
  @Resource
  private UserRoleService userRoleService;
  
  @RequestMapping("/addUserRoleInfo.do")
  @ResponseBody
  public String addUserRoleInfo(UserRole ur,HttpServletRequest request,HttpServletResponse response){
	  String flag="";
	  try {
		  UserRole urole=userRoleService.selectByUserId(ur.getUserid());
		  if(urole==null){
			  userRoleService.insertSelective(ur);
		  }else{
			  userRoleService.updateByUserId(ur);
		  }
		  
		flag="true";
	} catch (Exception e) {
		flag="false";
		e.printStackTrace();
	}
	  return flag;
  }
}
