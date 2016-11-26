package com.ctsig.sys.userRole.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctsig.sys.userRole.domain.UserRole;

import com.ctsig.sys.userRole.controller.vo.UserRoleVo;
import com.ctsig.sys.userRole.dao.UserRoleMapper;
import com.ctsig.sys.userRole.service.UserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	@Resource
	private UserRoleMapper userRoleMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserRole record) {
		return userRoleMapper.insert(record);
	}

	@Override
	public int insertSelective(UserRole record) {
		return userRoleMapper.insertSelective(record);
	}

	@Override
	public UserRole selectByPrimaryKey(Integer id) {
		return userRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserRole record) {
		return userRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserRole record) {
		return userRoleMapper.updateByPrimaryKey(record);
	}

	@Override
	public UserRole selectByUserId(Integer id) {
		return userRoleMapper.selectByUserId(id);
	}

	@Override
	public int updateByUserId(UserRole userId) {
		return userRoleMapper.updateByUserId(userId);
	}

	@Override
	public List<UserRoleVo> selectByRoleByUserId(UserRoleVo vo) {
		return userRoleMapper.selectByRoleByUserId(vo);
	}

}
