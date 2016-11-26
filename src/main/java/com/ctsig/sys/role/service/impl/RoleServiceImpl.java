package com.ctsig.sys.role.service.impl;
/**
 * 角色操作业务逻辑层
 */
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctsig.sys.role.dao.RoleMapper;
import com.ctsig.sys.role.domain.Role;
import com.ctsig.sys.role.service.RoleService;
import com.ctsig.sys.rolePrivilege.dao.RolePrivilegeMapper;
import com.ctsig.sys.rolePrivilege.domain.RolePrivilege;
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private RolePrivilegeMapper rolePrivilegeMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Role record) {
		return roleMapper.insert(record);
	}

	@Override
	public int insertSelective(Role record) {
		return roleMapper.insertSelective(record);
	}

	@Override
	public Role selectByPrimaryKey(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		return roleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		return roleMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Role> selectByCondition(Role role) {
		return roleMapper.selectByCondition(role);
	}

	@Override
	public int insertRoleANDprivilege(Role record,String[] pId, String[] priId) {
		roleMapper.insert(record);
		Integer rid=record.getId();
		for(int i=0;i<pId.length;i++){
			RolePrivilege rp=new RolePrivilege();
			rp.setPrivilegeid(priId[i]);
			rp.setRoleid(rid);
			rp.setPid(pId[i]);
			rolePrivilegeMapper.insertSelective(rp);
		}
		return pId.length;
	}

	@Override
	public void updateRoleWithPrivilege(Role record, String[] pId,String[] priId) {
		roleMapper.updateByPrimaryKeySelective(record);
		//先根据roleIId 删除rolePrivilege中间表中的数据，在根据roleId,添加选中的权限
		rolePrivilegeMapper.deleteByRoleId(record.getId());
		for(int i=0;i<pId.length;i++){
			RolePrivilege rp=new RolePrivilege();
			rp.setPrivilegeid(priId[i]);
			rp.setRoleid(record.getId());
			rp.setPid(pId[i]);
			rolePrivilegeMapper.insertSelective(rp);
		}
	}

	@Override
	public void deleterRoleANDRolePrivilege(String id) {
		roleMapper.deleteByPrimaryKey(id);
		rolePrivilegeMapper.deleteByRoleId(Integer.parseInt(id));
	}

}
