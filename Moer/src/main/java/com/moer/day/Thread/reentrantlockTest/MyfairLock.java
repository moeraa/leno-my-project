package com.moer.day.Thread.reentrantlockTest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: moer
 * @Date: 2020/1/9 10:03
 * @Description:
 */
public class MyfairLock extends Thread {

  private ReentrantLock lock = new ReentrantLock(false);

  public static void main(String[] args) {
    MyfairLock myfairLock = new MyfairLock();
    Runnable runnable = () -> {
      System.out.println(Thread.currentThread().getName() + "启动");
      myfairLock.fairLock();
    };
    Thread[] threads = new Thread[10];
    for (int i = 0; i < 10; i++) {
      threads[i] = new Thread(runnable);
    }
    for (int i = 0; i <10 ; i++) {
      threads[i].start();
    }
  }


  public void fairLock() {
    try {
      lock.lock();
      System.out.println(Thread.currentThread().getName() + "正在持有锁");
    } finally {
      System.out.println(Thread.currentThread().getName() + "释放了锁");
      lock.unlock();
    }
  }


}
