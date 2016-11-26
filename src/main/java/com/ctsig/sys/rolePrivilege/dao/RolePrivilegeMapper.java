package com.ctsig.sys.rolePrivilege.dao;

import java.util.List;

import com.ctsig.sys.rolePrivilege.controller.vo.RolePrivilegeVo;
import com.ctsig.sys.rolePrivilege.domain.RolePrivilege;


public interface RolePrivilegeMapper {
    int deleteByPrimaryKey(Integer id);
    void deleteByRoleId(Integer roleId);

    int insert(RolePrivilege record);

    int insertSelective(RolePrivilege record);

    RolePrivilege selectByPrimaryKey(Integer id);
    List<RolePrivilege> selectByRoleId(Integer id);
    List<RolePrivilegeVo> selectRolePrivilegeVoByRoleId(RolePrivilegeVo vo);

    int updateByPrimaryKeySelective(RolePrivilege record);

    int updateByPrimaryKey(RolePrivilege record);
}