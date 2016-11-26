package com.ctsig.sys.privilege.service.impl;
/**
 * 权限操作业务逻辑层
 * 该层使用了spring cache注解@Cacheable 查询结果放入缓存 @CacheEvict 清除缓存
 */

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ctsig.sys.privilege.dao.PrivilegeMapper;
import com.ctsig.sys.privilege.domain.Privilege;
import com.ctsig.sys.privilege.service.PrivilegeService;
@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {
	@Resource
	private PrivilegeMapper privilegeMapper;

	/**
	 * 注释来标记要清空缓存的方法，当这个方法被调用后，即会清空缓存
	 */
	@Override
	@CacheEvict(value="privilege")
	public int deleteByPrimaryKey(String id) {
		return privilegeMapper.deleteByPrimaryKey(id);
	}

	@Override
	@CacheEvict(value="privilege")
	public int insert(Privilege record) {
		return privilegeMapper.insert(record);
	}

	@Override
	@CacheEvict(value="privilege")
	public int insertSelective(Privilege record) {
		return privilegeMapper.insertSelective(record);
	}

	/**
	 * 这个注释的意思是，当调用这个方法的时候，会从一个名叫 privilege 的缓存中查询，
	 * 如果没有，则执行实际的方法（即查询数据库），并将执行的结果存入缓存中，否则返回缓存中的对象
	 */
	@Override
	@Cacheable(value="privilege")
	public Privilege selectByPrimaryKey(String id) {
		System.out.println("shiroing--------------------------------------");;
		return privilegeMapper.selectByPrimaryKey(id);
	}

	@Override
	@CacheEvict(value="privilege")
	public int updateByPrimaryKeySelective(Privilege record) {
		return privilegeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@CacheEvict(value="privilege")
	public int updateByPrimaryKey(Privilege record) {
		return privilegeMapper.updateByPrimaryKey(record);
	}

	@Override
	@CacheEvict(value="privilege")
	public int deleteByIdANDByPid(String id) {
		privilegeMapper.deleteByPrimaryKey(id);
		return privilegeMapper.deleteByPid(id);
	}

	@Override
	@Cacheable(value="privilege")
	public List<Privilege> selectByPid(String id) {
		return privilegeMapper.selectByPid(id);
	}

	@Override
	@Cacheable(value="privilege")
	public List<Privilege> selectAllPrivilege() {
		return privilegeMapper.selectAllPrivilege();
	}

}
