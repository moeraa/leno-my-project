package com.moer.day.Thread.Synchronized.tuniu;

/**
 * @Auther: moer
 * @Date: 2019/10/8 19:50
 * @Description:
 */
public class ThreadDemo {


  public synchronized static void method1(){
    System.out.println(Thread.currentThread().getName() + "进入了方法1");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(Thread.currentThread().getName() + "离开了方法1");

//    synchronized(this){
//      System.out.println(Thread.currentThread().getName() + "进入了方法1");
//      System.out.println(Thread.currentThread().getName() + "离开了方法1");
//    }
  }
  static {
    System.out.println(Thread.currentThread().getName() + "进入了方法3");
    System.out.println(Thread.currentThread().getName() + "离开了方法3");
  }
  public void method2(){

//    try {
//      Thread.sleep(1000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    synchronized(ThreadDemo.class){
      System.out.println(Thread.currentThread().getName() + "进入了方法2");
      System.out.println(Thread.currentThread().getName() + "离开了方法2");
    }
  }


}
