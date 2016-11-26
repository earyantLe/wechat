package com.earyant.sys.user.service;



import java.util.List;

import com.earyant.sys.user.domain.SysUser;


public interface UserService {
	int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> selectByUserCondition(SysUser user);
    SysUser selectUserBynameANDpwd(SysUser user);


}
