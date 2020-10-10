package com.moer.day.Pattern;

public class SingletonPattern {
    private static final SingletonPattern singleton = new SingletonPattern();

    private SingletonPattern() {
    }
    public static SingletonPattern getSingleton(){
        return singleton;
    }
    public static void doSometing(){
        System.out.println("do someting");
    }
}
