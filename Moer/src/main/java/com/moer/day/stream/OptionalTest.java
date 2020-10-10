package com.moer.day.stream;

import java.util.Optional;

/**
 * @Auther: moer
 * @Date: 2019/8/1 14:08
 * @Description:
 */
public class OptionalTest {

  public static void main(String[] args) {
    Integer num = 1;
    Integer num1 = null;
    Integer num2 = new Integer(2);//会抛出npe
//    Optional  optional = Optional.of(num1);
    Optional optional1 = Optional.ofNullable(null);//不会抛出npe
    Optional<Integer> optional =  Optional.of(num);
//    System.out.println("isPresent = "+optional.isPresent());
     System.out.println("isPresent = "+optional1.isPresent());
     System.out.println("get() = "+optional.get());
     optional.ifPresent(n -> {
       System.out.println(    n.equals(5));
     });
     Integer aa = Optional.ofNullable(num1).orElse(num2);
    System.out.println("aa value = "+aa);
    Integer dd = Optional.ofNullable(num1).orElseGet(() -> num2);
    System.out.println("dd value ="+dd);
    Integer cc = Optional.ofNullable(num).map(integer -> 7).orElse(5);
    System.out.println("cc value = "+cc);

  }


}
