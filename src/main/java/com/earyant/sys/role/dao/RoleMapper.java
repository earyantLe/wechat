package com.earyant.sys.role.dao;
/**
 * 角色数据访问层接口
 */
import java.util.List;

import com.earyant.sys.role.domain.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    List<Role> selectByCondition(Role role);
}