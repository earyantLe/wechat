package com.earyant.sys.gank.dao;

import com.earyant.sys.gank.domain.GankContent;

import java.util.List;

public interface GankContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GankContent record);

    int insertSelective(GankContent record);

    GankContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GankContent record);

    int updateByPrimaryKeyWithBLOBs(GankContent record);

    int updateByPrimaryKey(GankContent record);
    List<GankContent> selectByNewDate(String date);
}