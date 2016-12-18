package com.earyant.sys.gank.dao;


import com.earyant.sys.gank.domain.Day;

import java.util.List;

public interface DayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Day record);

    int insertSelective(Day record);

    Day selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Day record);

    int updateByPrimaryKey(Day record);

    Day selectBydate(String date);

    List<Day> select();
}