package com.earyant.sys.rolePrivilege.dao;

import com.earyant.sys.rolePrivilege.controller.vo.RolePrivilegeVo;
import com.earyant.sys.rolePrivilege.domain.RolePrivilege;

import java.util.List;


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