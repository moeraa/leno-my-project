package com.moer.day.annotation;

import java.lang.reflect.Field;

/**
 * @Auther: moer
 * @Date: 2020/1/10 16:33
 * @Description:
 */
public class MyFieldTest {

  @MyField(description = "用户名",length = 12)
  private String username;

  public void testMyField(){
    Class c = MyFieldTest.class;
    for (Field f : c.getDeclaredFields()) {
      if (f.isAnnotationPresent(MyField.class)){
        MyField annotation = f.getAnnotation(MyField.class);
        System.out.println("字段：["+f.getName()+"]，描述 "+annotation.description());
      }
    }
  }


  public static void main(String[] args) {
    MyFieldTest myFieldTest = new MyFieldTest();
    myFieldTest.testMyField();
  }


}
