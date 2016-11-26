package com.earyant.sys.privilege.controller;


import java.util.ArrayList;
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

import com.earyant.sys.privilege.controller.vo.PrivilegeVo;
import com.earyant.sys.privilege.domain.Privilege;
import com.earyant.sys.privilege.service.PrivilegeService;
import com.earyant.sys.rolePrivilege.domain.RolePrivilege;
import com.earyant.sys.rolePrivilege.service.RolePrivilegeService;


@Controller
@RequestMapping("/oper/privilege")
public class PrivilegeController {
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	private RolePrivilegeService rolePrivilegeService;
	  /**
	   * 跳转到权限树页面
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/toprivilegeInfo.do")
	  public String toprivilegeInfo(HttpServletRequest request,HttpServletResponse response){
		  return "sys/listPrivilegeInfo";
	  }
	  
	  /**
	   * 跳转到权限添加页面
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/toAddprivilegeInfo.do")
	  public String toAddprivilegeInfo(String id,HttpServletRequest request,HttpServletResponse response){
		  request.setAttribute("Pid", id);
		  return "sys/addPrivilegeInfo";
	  }
	  
	  /**
	   * 跳转到权限修改页面
	   * @param id
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/toupdateprivilegeInfoPage.do")
	  public String toupdateprivilegeInfoPage(String id,HttpServletRequest request,HttpServletResponse response){
		  Privilege pri=privilegeService.selectByPrimaryKey(id);
		  request.setAttribute("pri", pri);
		  return "sys/updatePrivilegeInfo";
	  }
	  /**
	   * 修改权限
	   * @param pri
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/updatePrivilegeInfo.do")
	  @ResponseBody
	  public String updatePrivilegeInfo(Privilege pri,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  try {
			  privilegeService.updateByPrimaryKeySelective(pri);
			flag="true";
		} catch (Exception e) {
			flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  
	  @RequestMapping("/deleteChildPrivilegeInfo.do")
	  @ResponseBody
	  public String deleteChildPrivilegeInfo(String ids,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  try {
			String[] idsarray=ids.split(",");
			  for(String id:idsarray){
				  privilegeService.deleteByPrimaryKey(id);
			  }
			  flag="true";
		} catch (NumberFormatException e) {
			 flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  /**
	   * 添加权限
	   * @param pri
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/addPrivilegeInfo.do")
	  @ResponseBody
	  public String addPrivilegeInfo(Privilege pri,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  try {
			  privilegeService.insertSelective(pri);
			flag="true";
		} catch (Exception e) {
			flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  /**
	   * 修改权限
	   * @param pri
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/updateprivilegeInfo.do")
	  @ResponseBody
	  public String updateprivilegeInfo(Privilege pri,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  try {
			  privilegeService.updateByPrimaryKeySelective(pri);
			flag="true";
		} catch (Exception e) {
			flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  /**
	   * 删除权限
	   * @param ids
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/deletePrivilegeInfo.do")
	  @ResponseBody
	  public String deletePrivilegeInfo(String id,HttpServletRequest request,HttpServletResponse response){
		  String flag="";
		  try{
			  privilegeService.deleteByIdANDByPid(id);
		  flag="true";
		} catch (NumberFormatException e) {
			 flag="false";
			e.printStackTrace();
		}
		  return flag;
	  }
	  
	  @RequestMapping("/getPrivilegeInfo.do")
	  @ResponseBody
	  public String getPrivilegeInfo(HttpServletRequest request,HttpServletResponse response){
		  List<Privilege> plist=new ArrayList<Privilege>();
		  Privilege p=new Privilege();
		  p.setId("1");
		  p.setpId("-1");
		  p.setPrivilegename("菜单导航");
		  plist.add(p);
		  List<Privilege> ppl=privilegeService.selectByPid("1");
		  plist.addAll(ppl);
		  JSONArray array=JSONArray.fromObject(plist);
		  return array.toString().replaceAll("privilegename", "name");
	  }
	  
	  @RequestMapping("/listPrivilegeInfo.do")
	  @ResponseBody
	  public Map<String, Object> listPrivilegeInfo(String id,HttpServletRequest request,HttpServletResponse response){
		 List<Privilege> pList =privilegeService.selectByPid(id);
		 Map<String, Object> data = new HashMap<String, Object>();
		 data.put("total", pList.size());
		 data.put("rows", pList);
		 return data;
	  }
	  
	  /**
	   * 角色授权树
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/getAllPrivilegeInfo.do")
	  @ResponseBody
	  public String getAllPrivilegeInfo(HttpServletRequest request,HttpServletResponse response){
		  List<Privilege> ppl=privilegeService.selectAllPrivilege();
		  List<PrivilegeVo> voList=new LinkedList<PrivilegeVo>();
		  if(!ppl.isEmpty()){
			  for(Privilege pl:ppl){
				  PrivilegeVo vo=new PrivilegeVo();
				  vo.setId(pl.getId());
				  vo.setIsmenu(pl.getIsmenu());
				  vo.setOpen(true);
				  vo.setPage(pl.getPage());
				  vo.setpId(pl.getpId());
				  vo.setPrivilegename(pl.getPrivilegename());
				  voList.add(vo);
				 
			  }
		  }
		  JSONArray array=JSONArray.fromObject(voList);
		  return array.toString().replaceAll("privilegename", "name");
	  }
	  
	  /**
	   * 回显角色选择树
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/getAllPrivilegeInfoANDchecked.do")
	  @ResponseBody
	  public String getAllPrivilegeInfoANDchecked(Integer roleId,HttpServletRequest request,HttpServletResponse response){
		  List<Privilege> ppl=privilegeService.selectAllPrivilege();
		  List<PrivilegeVo> voList=new LinkedList<PrivilegeVo>();
		  List<RolePrivilege> rpList=rolePrivilegeService.selectByRoleId(roleId);
		  if(!ppl.isEmpty()){
			  for(Privilege pl:ppl){
				  PrivilegeVo vo=new PrivilegeVo();
				  vo.setId(pl.getId());
				  vo.setIsmenu(pl.getIsmenu());
				  vo.setPage(pl.getPage());
				  vo.setpId(pl.getpId());
				  vo.setPrivilegename(pl.getPrivilegename());
				  for(RolePrivilege rpp:rpList){
					  if(pl.getId().equals(rpp.getPrivilegeid())||pl.getId().equals(rpp.getPid())){
						  vo.setChecked(true);
						  vo.setOpen(true);
					  }
					 
				  }
				  voList.add(vo);
				 
			  }
		  }
		  JSONArray array=JSONArray.fromObject(voList);
		  return array.toString().replaceAll("privilegename", "name");
	  }
}
