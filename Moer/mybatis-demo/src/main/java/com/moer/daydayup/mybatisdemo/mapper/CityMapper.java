package com.moer.daydayup.mybatisdemo.mapper;

import com.moer.daydayup.mybatisdemo.domian.City;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @author chenxh
 * @date 2020/9/14  2:52 下午
 * @func
 */
@Mapper
public interface CityMapper {
    @Select("select * from city where id =#{id}")
    City findCitysByCondition(Integer id);

    @Insert(
            " insert into city (`name`, `state`, country)" +
                    "values (#{name,jdbcType=VARCHAR}, #{state,jdbcType=VARCHAR}, #{country,jdbcType=VARCHAR})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(City city);

    @SelectProvider(value = Sql.class, method = "selectCityLike")
    List<City> selectCityLike(Integer id, String name, String country);

    class Sql {
        // 匿名内部类风格
        public String deleteCitySql() {
            return new SQL() {{
                DELETE_FROM("CITY");
                WHERE("ID = #{id}");
            }}.toString();
        }

        public String insertCitySql1() {
            return new SQL().INSERT_INTO("CITY")
                    .VALUES("name", "#{name}")
                    .VALUES("state,country", "#{state},#{country}").toString();
        }

        // 动态条件（注意参数需要使用 final 修饰，以便匿名内部类对它们进行访问）
        public String selectCityLike(final Integer id, final String name, final String country) {
            return new SQL() {{
                SELECT("C.ID, C.NAME, C.COUNTRY, C.STATE");
                FROM("CITY C");
                if (id != null) {
                    WHERE("C.ID= #{id}");
                }
                if (name != null) {
                    WHERE("C.NAME like #{name}");
                }
                if (country != null) {
                    WHERE("C.COUNTRY like #{country}");
                }
                ORDER_BY("C.STATE");
            }}.toString();
        }

        public String insertCitySql() {
            return new SQL() {{
                INSERT_INTO("CITY");
                VALUES("ID, NAMW", "#{id}, #{name}");
                VALUES("COUNTRY", "#{country}");
            }}.toString();
        }

        public String updateCitySql() {
            return new SQL() {{
                UPDATE("CITY");
                SET("NAME = #{name}");
                WHERE("ID = #{id}");
            }}.toString();
        }
    }
}
