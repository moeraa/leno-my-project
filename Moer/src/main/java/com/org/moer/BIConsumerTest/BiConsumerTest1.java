package com.org.moer.BIConsumerTest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author chenxh
 * @date 2020/7/31  10:12 上午
 * @func
 */
public class BiConsumerTest1 {
    public static void main(String[] args) {

        baseUseMethod();
        highLevelUseMethod();
        aLitterPractice();
        mapForEach();
    }

    /**
     * 1.基本用法
     *
     * @param
     * @return void
     * @date 2020/7/31 chenxh
     */
    public static void baseUseMethod() {

        BiConsumer<Integer, String> addTow
                = (x, y) -> System.out.println("BIconsumner base use (x+y)=" + (x + y));
        addTow.accept(1, "1");
    }

    /**
     * 1.高阶函数
     *
     * @param
     * @return void
     * @date 2020/7/31 chenxh
     */
    public static void highLevelUseMethod() {
        addTow(1, 2, (x, y) -> System.out.println("BIconsumner high level use (x+y)=" + (x + y)));
        addTow("node", "js", (x, y) -> System.out.println("BIconsumner high level use (x+y)=" + x + y));
    }

    /**
     * 2.小练习
     *
     * @param
     * @return void
     * @date 2020/7/31 chenxh
     */
    public static void aLitterPractice() {
        math(1, 2, (x, y) -> System.out.println(x + y));
        math(1, 2, (x, y) -> System.out.println(x - y));
        math(1, 2, (x, y) -> System.out.println(x * y));
        math(1, 2, (x, y) -> System.out.println(x / y));
    }

    static <T> void addTow(T a1, T a2, BiConsumer<T, T> c) {
        c.accept(a1, a2);
    }

    static <T> void math(T a1, T a2, BiConsumer<T, T> method) {
        method.accept(a1, a2);
    }
    /**
     *JDk中的Map.forEach也可以节后是一个BiConsumer 作为参数，传入的第一个参数是key，第二个参数是Value
     * @param
     * @return void
     * @date 2020/7/31 chenxh
     */
    public static void mapForEach() {
        Map map = new HashMap();
        map.put("1",1);
        map.forEach((x,y)->{
            System.out.println("key="+x+" value="+y);
        });

    }



}

