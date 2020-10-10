package com.moer.day.Thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Play implements Runnable {

    private CountDownLatch downLatch;
    private String name;

    public Play(CountDownLatch downLatch, String name){
        this.downLatch = downLatch;
        this.name = name;
    }

    public void run() {
        this.doWork();
        System.out.println(this.name + "集合完毕");
        this.downLatch.countDown();
        try {
            _wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            downLatch.await();
            System.out.println("集合完毕");
            doPlay();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        doPlay();

    }



    private void doWork(){
        System.out.println(this.name + "在集合的路上!");

    }
    private synchronized void _wait() throws InterruptedException {
        System.out.println(this.name + "finsh_wait!");
        this.wait();
    }

    private synchronized void doPlay()  {
        System.out.println(this.name+"活动");
        this.notifyAll();
    }

}
