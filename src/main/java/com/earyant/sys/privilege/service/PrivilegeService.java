package com.earyant.sys.privilege.service;


import com.earyant.sys.privilege.domain.Privilege;

import java.util.List;

public interface PrivilegeService {
    int deleteByPrimaryKey(String id);

    int deleteByIdANDByPid(String id);

    int insert(Privilege record);

    int insertSelective(Privilege record);

    Privilege selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Privilege record);

    int updateByPrimaryKey(Privilege record);

    List<Privilege> selectByPid(String id);

    List<Privilege> selectAllPrivilege();
}
