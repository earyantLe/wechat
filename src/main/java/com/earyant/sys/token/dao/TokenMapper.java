package com.earyant.sys.token.dao;

import com.earyant.sys.token.domain.Token;

import java.util.List;

public interface TokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Token record);

    int insertSelective(Token record);

    Token selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);

    List<Token> selectAll();
}