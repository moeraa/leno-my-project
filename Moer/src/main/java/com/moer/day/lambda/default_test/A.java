package com.moer.day.lambda.default_test;

public interface A {
    default void foo(){
        System.out.println("call default foo ");
    }
}
