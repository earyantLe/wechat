package com.ctsig.sys.gank.dao;


import com.ctsig.sys.gank.domain.Day;

public interface DayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Day record);

    int insertSelective(Day record);

    Day selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Day record);

    int updateByPrimaryKey(Day record);
    Day selectBydate(String date);

    Day select();
}