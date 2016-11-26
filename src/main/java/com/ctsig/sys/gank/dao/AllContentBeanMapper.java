package com.ctsig.sys.gank.dao;

import com.ctsig.sys.gank.domain.AllContentBean;
import com.ctsig.sys.gank.domain.AllContentBeanWithBLOBs;

import java.util.List;

public interface AllContentBeanMapper {
    int deleteByPrimaryKey(Integer tId);

    int insert(AllContentBeanWithBLOBs record);

    int insertSelective(AllContentBeanWithBLOBs record);

    AllContentBeanWithBLOBs selectByPrimaryKey(Integer tId);

    List<AllContentBeanWithBLOBs> selectByType(String type);

    int updateByPrimaryKeySelective(AllContentBeanWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(AllContentBeanWithBLOBs record);

    int updateByPrimaryKey(AllContentBean record);

    AllContentBeanWithBLOBs selectByGankId(String gankId);

    void updateByGankIdWithBLOBs(AllContentBeanWithBLOBs allContentBeanWithBLOBs1);

    List<AllContentBeanWithBLOBs> selectByDate(String date);
}