package com.moer.day.excel;

import cn.hutool.core.convert.Convert;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 字段转换，文件中字段与实体类属性转换
 *
 * @author zhangjd
 * @since 2020-07-31
 */
public class Converter {
    public static final String STRING_CHINESE_COMMA = "，";

    /**
     * 将【单元格源字符串值】转换成【类成员域值】
     *
     * @param field 成员域
     * @param str   【单元格源字符串值】
     * @return 【类成员域值】
     */
    public Object str2obj(Field field, String str) {
        Class<?> type = field.getType();
        if (String.class.equals(type)) {
            return str;
        } else if (int.class.equals(type) || Integer.class.equals(type)) {
            return Integer.parseInt(str);
        } else if (double.class.equals(type) || Double.class.equals(type)) {
            return Double.parseDouble(str);
        } else if (float.class.equals(type) || Float.class.equals(type)) {
            return Float.parseFloat(str);
        } else if (long.class.equals(type) || Long.class.equals(type)) {
            return Long.parseLong(str);
        } else if (boolean.class.equals(type) || Boolean.class.equals(type)) {
            return Boolean.parseBoolean(str);
        } else if (List.class.equals(type)) {
            if (field.getGenericType() instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                if (Long.class.equals(parameterizedType.getActualTypeArguments()[0])) {
                    return Arrays.stream(str.split(STRING_CHINESE_COMMA)).map(Convert::toLong).collect(Collectors.toList());
                }
            }
            return Arrays.stream(str.split(STRING_CHINESE_COMMA)).collect(Collectors.toList());
        }
        throw new RuntimeException(String.format("未能将值【%s】解析成【%s】类型的值 ", str, type.getSimpleName()));
    }


    /**
     * 转换异常的处理方法
     */
    public enum MismatchPolicy {
        /**
         * 继续抛出异常
         */
        Throw,
        /**
         * 简单类型使用默认初始值，引用类型使用null
         */
        Default
    }
}

