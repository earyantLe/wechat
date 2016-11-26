package com.earyant.sys.user.dao;

import java.util.List;

import com.earyant.sys.user.domain.SysUser;

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