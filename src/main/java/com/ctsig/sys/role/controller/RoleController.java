package com.ctsig.sys.role.controller;
/**
 * 角色访问控制层
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctsig.sys.userRole.domain.UserRole;

import com.ctsig.sys.role.controller.vo.RoleVo;
import com.ctsig.sys.role.domain.Role;
import com.ctsig.sys.role.service.RoleService;
import com.ctsig.sys.userRole.service.UserRoleService;

@Controller
@RequestMapping("/oper/role")
public class RoleController {
	@Resource
	private RoleService roleService;
	@Resource
	private UserRoleService userRoleService;
	
	 @RequestMapping("/toListRoleInfo.do")
	  public String toListRoleInfo(HttpServletRequest request,HttpServletResponse response){
		  return "sys/listRoleInfo";
	  }
	 @RequestMapping("/toshowroleInfoPage.do")
	  public String toshowroleInfoPage(String id,HttpServletRequest request,HttpServletResponse response){
		 request.setAttribute("id", id);
		 return "sys/showRoleInfo";
	  }
	 
	 /**
	  * 角色列表
	  * @param role
	  * @param request
	  * @param response
	  * @return
	  */
	  @RequestMapping("/listroleInfo.do")
	  @ResponseBody
	  public Map<String, Object> listroleInfo(Role role,HttpServletRequest request,HttpServletResponse response){
		 List<Role> rList =roleService.selectByCondition(role);
		 Map<String, Object> data = new HashMap<String, Object>();
		 data.put("total", rList.size());
		 data.put("rows", rList);
		 return data;
	  }
	  
	  @RequestMapping("/getAllRoleInfo.do")
	  @ResponseBody
	  public String getAllRoleInfo(Integer uid,Role role,HttpServletRequest request,HttpServletResponse response){
		 List<Role> rList =roleService.selectByCondition(role);
		 List<RoleVo> voList=new LinkedList<RoleVo>();
		 UserRole ur=userRoleService.selectByUserId(uid);
		 if(!rList.isEmpty()){
			 for(Role r:rList){
				 RoleVo vo=new RoleVo();
				 vo.setId(r.getId());
				 vo.setName(r.getRolename());
				 vo.setRoleCode(r.getRolecode());
				 voList.add(vo);
				 if(ur!=null){
					 if(r.getId().equals(ur.getRoleid()+"")){
						 vo.setChecked(true);
					 }
				 }
				
				 vo=null;
			 }
		 }
		 JSONArray array=JSONArray.fromObject(voList);
		 return array.toString();
	  }
	  
	  /**
	   * 添加角色
	   * @param pri
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/addRoleInfo.do")
	  @ResponseBody
	  public String addRoleInfo(String roleIds,String priIds,Role record,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  String[] roleId=roleIds.split(",");
		  String[] priId=priIds.split(",");
		  try {
			  roleService.insertRoleANDprivilege(record, roleId, priId);
			flag="true";
		} catch (Exception e) {
			flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  /**
	   * 跳转到角色修改页面
	   * @param id
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/toupdateroleInfoPage.do")
	  public String toupdateroleInfoPage(Integer id,HttpServletRequest request,HttpServletResponse response){
		  Role role=roleService.selectByPrimaryKey(id);
		  request.setAttribute("role", role);
		  return "sys/updateRoleInfo";
	  }
	  
	  @RequestMapping("/updateRoleInfo.do")
	  @ResponseBody
	  public String updateRoleInfo(String roleIds,String priIds,Role record,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  String[] roleId=roleIds.split(",");
		  String[] priId=priIds.split(",");
		  try {
		    roleService.updateRoleWithPrivilege(record, roleId, priId);
			flag="true";
		} catch (Exception e) {
			flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  /**
	   * 删除角色同时删除角色权限中间表中的数据
	   * @param id
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/deleteRoleInfo.do")
	  @ResponseBody
	  public String deleteRoleInfo(String ids,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  try{
			  String[] idsarray=ids.split(",");
			  for(String id:idsarray){
				  roleService.deleterRoleANDRolePrivilege(id);
			  }
			  flag="true";
		} catch (NumberFormatException e) {
			 flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
}
