package com.moer.day.Thread.Synchronized.tuniu;

/**
 * @Auther: moer
 * @Date: 2019/10/8 19:49
 * @Description:
 */
public class SynchronizedStaticTest {

  public static void main(String[] args) {
    final ThreadDemo demo = new ThreadDemo();
    new

        Thread() {

          @Override
          public void run() {
            for (int i = 0; i < 100; i++) {
              demo.method1();
            }
          }


        }.start();

    new

        Thread() {

          @Override
          public void run() {
            for (int i = 0; i < 100; i++) {
              demo.method2();
            }
          }


        }.start();
  }


}
