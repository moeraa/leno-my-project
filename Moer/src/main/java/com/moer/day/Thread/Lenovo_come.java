package com.moer.day.Thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lenovo_come {


    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        CountDownLatch latch = new CountDownLatch(3);

        Play w1 = new Play(latch, "1号");
        Play w2 = new Play(latch, "2号");
        Play w3 = new Play(latch, "3号");

        Hr boss = new Hr(latch);

        executor.execute(w3);
        executor.execute(w2);
        executor.execute(w1);
        executor.execute(boss);
        executor.shutdown();

    }

}
