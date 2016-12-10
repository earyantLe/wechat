package com.earyant.sys.role.service;


import com.earyant.sys.role.domain.Role;

import java.util.List;

public interface RoleService {
    int deleteByPrimaryKey(String id);

    void deleterRoleANDRolePrivilege(String id);

    int insert(Role record);

    int insertSelective(Role record);

    int insertRoleANDprivilege(Role record, String[] roleId, String[] priId);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectByCondition(Role role);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    void updateRoleWithPrivilege(Role record, String[] roleId, String[] priId);
}
