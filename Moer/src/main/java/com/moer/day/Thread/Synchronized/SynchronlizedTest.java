package com.moer.day.Thread.Synchronized;

public class SynchronlizedTest {
    public synchronized void dosth(){
        System.out.println("hello world");
    }

    public void dosthOther(){
        synchronized (SynchronlizedTest.class){
            System.out.println("hello world others");
        }
    }
}
