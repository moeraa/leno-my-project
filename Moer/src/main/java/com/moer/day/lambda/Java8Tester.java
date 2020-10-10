package com.moer.day.lambda;

import java.util.Optional;

/**
 * @Auther: moer
 * @Date: 2019/3/21 16:32
 * @Description:
 */
public class Java8Tester {
    public static void main(String[] args) {
        Java8Tester java8Tester = new Java8Tester();
        Integer value1 = null;
        Integer value2 = new Integer(10);
        //Optional.ofNullable -允许传递为null的参数
         Optional<Integer> a = Optional.ofNullable(value1);
        Optional<Integer> b = Optional.of(value2);
        System.out.println(java8Tester.sum(a,b));
    }
    public Integer sum (Optional<Integer> a,Optional<Integer>b){
        //Optional.isPreset -判断是都存在
        System.out.println("the first param:"+a.isPresent());
        System.out.println("the second param:"+b.isPresent());
        //Optional.orElse 如果只存在 返回他 否则返回默认值
        Integer value1 = a.orElse(new Integer(0));
        // Optional.get 获取值，值需要存在
        Integer value2 = b.get();

        return value1+value2;
    }
}
