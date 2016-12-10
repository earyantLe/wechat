package com.earyant.sys.user.dao;

import com.earyant.sys.user.domain.SysUser;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> selectByUserCondition(SysUser user);

    SysUser selectUserBynameANDpwd(SysUser user);
}