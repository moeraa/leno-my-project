package com.org.moer.lambda;
import java.util.function.*;
public class FunctionInterfaceDemo {
    @FunctionalInterface
    interface Predicate<T>{
        boolean test(T t);
    }
    public static boolean doPredicate(int age,Predicate<Integer>predicate){
        return predicate.test(age);
    }

    public static void main(String[] args) {
        boolean isAdult = doPredicate(29,x->x>=18) ;
        System.out.println(isAdult);
    }
}
