package com.earyant.sys.user.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.earyant.sys.user.dao.SysUserMapper;
import com.earyant.sys.user.domain.SysUser;
import com.earyant.sys.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private SysUserMapper userMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysUser record) {
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(SysUser record) {
		return userMapper.insertSelective(record);
	}

	@Override
	public SysUser selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUser record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysUser record) {
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SysUser> selectByUserCondition(SysUser user) {
		return userMapper.selectByUserCondition(user);
	}

	@Override
	public SysUser selectUserBynameANDpwd(SysUser user) {
		return userMapper.selectUserBynameANDpwd(user);
	}

}
