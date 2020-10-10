package com.org.moer.Thread;

import java.util.concurrent.CountDownLatch;

public class Hr implements Runnable {

    private CountDownLatch downLatch;

    public Hr(CountDownLatch downLatch) {
        this.downLatch = downLatch;
    }

    public void run() {
        System.out.println("人事正在等所有的工人干完活......");
        try {
            this.downLatch.await();
        } catch (InterruptedException e) {
        }
        System.out.println("所有人集合完毕！");
        doPlay();
    }

    private  void doPlay()  {
        System.out.println("自由活动");
        this.notifyAll();
    }
}

