package com.earyant.sys.role.dao;
/**
 * 角色数据访问层接口
 */

import com.earyant.sys.role.domain.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectByCondition(Role role);
}