package com.earyant.web.dao;

import com.earyant.web.pojo.TokenMapping;

public interface TokenMappingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TokenMapping record);

    int insertSelective(TokenMapping record);

    TokenMapping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TokenMapping record);

    int updateByPrimaryKey(String record);
}