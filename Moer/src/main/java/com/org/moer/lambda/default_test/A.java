package com.org.moer.lambda.default_test;

public interface A {
    default void foo(){
        System.out.println("call default foo ");
    }
}
