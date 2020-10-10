package com.moer.daydayup.mybatisdemo.dao;

import com.moer.daydayup.mybatisdemo.domian.City;

public interface CityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
    City findCityByConditon(Integer id);
}