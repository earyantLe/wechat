package com.earyant.sys.rolePrivilege.service;

import java.util.List;

import com.earyant.sys.rolePrivilege.controller.vo.RolePrivilegeVo;
import com.earyant.sys.rolePrivilege.domain.RolePrivilege;


public interface RolePrivilegeService {
	 int deleteByPrimaryKey(Integer id);

	    int insert(RolePrivilege record);

	    int insertSelective(RolePrivilege record);

	    RolePrivilege selectByPrimaryKey(Integer id);
	    List<RolePrivilege> selectByRoleId(Integer id);

	    int updateByPrimaryKeySelective(RolePrivilege record);

	    int updateByPrimaryKey(RolePrivilege record);
	    
	    List<RolePrivilegeVo> selectRolePrivilegeVoByRoleId(RolePrivilegeVo vo);
}
