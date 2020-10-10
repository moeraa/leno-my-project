package com.moer.daydayup.mybatisdemo;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author chenxh
 * @date 2020/9/14  4:16 下午
 * @func
 */
public class Sql {
    // 匿名内部类风格
    public String deleteCitySql() {
        return new SQL() {{
            DELETE_FROM("CITY");
            WHERE("ID = #{id}");
        }}.toString();
    }

    public String InsertCitySql(){
       return new SQL().INSERT_INTO("CITY")
                .VALUES("name","#{name}")
                .VALUES("state,country","#{state},#{country}").toString();
    }
    // 动态条件（注意参数需要使用 final 修饰，以便匿名内部类对它们进行访问）
    public String selectCityLike(final String id, final String name, final String country) {
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
