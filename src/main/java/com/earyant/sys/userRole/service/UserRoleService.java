package com.earyant.sys.userRole.service;

import com.earyant.sys.userRole.controller.vo.UserRoleVo;
import com.earyant.sys.userRole.domain.UserRole;

import java.util.List;


public interface UserRoleService {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);


    UserRole selectByPrimaryKey(Integer id);

    UserRole selectByUserId(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    int updateByUserId(UserRole userId);

    List<UserRoleVo> selectByRoleByUserId(UserRoleVo vo);
}
