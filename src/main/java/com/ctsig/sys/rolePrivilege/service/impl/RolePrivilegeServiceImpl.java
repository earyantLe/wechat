package com.ctsig.sys.rolePrivilege.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctsig.sys.rolePrivilege.controller.vo.RolePrivilegeVo;
import com.ctsig.sys.rolePrivilege.dao.RolePrivilegeMapper;
import com.ctsig.sys.rolePrivilege.domain.RolePrivilege;
import com.ctsig.sys.rolePrivilege.service.RolePrivilegeService;

@Service("rolePrivilegeService")
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
	@Resource
	private RolePrivilegeMapper rolePrivilegeMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return rolePrivilegeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RolePrivilege record) {
		return rolePrivilegeMapper.insert(record);
	}

	@Override
	public int insertSelective(RolePrivilege record) {
		return rolePrivilegeMapper.insertSelective(record);
	}

	@Override
	public RolePrivilege selectByPrimaryKey(Integer id) {
		return rolePrivilegeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<RolePrivilege> selectByRoleId(Integer id) {
		return rolePrivilegeMapper.selectByRoleId(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RolePrivilege record) {
		return rolePrivilegeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(RolePrivilege record) {
		return rolePrivilegeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<RolePrivilegeVo> selectRolePrivilegeVoByRoleId(RolePrivilegeVo vo) {
		return rolePrivilegeMapper.selectRolePrivilegeVoByRoleId(vo);
	}

}
