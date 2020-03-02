package com.org.moer.Thread.volatile_some;

/**
 * @Auther: moer 无 volatile
 * @Date: 2019/9/16 17:21
 * @Description: ps:主线程可能由于停顿时间太短，导致while循环根本没进去。重试几次，当i的值不为0即代表已经进入循环。
 */
public class Test3 {

  //无 volatile sleep 为 1s
   private static  boolean flag = true;
  private static int i = 0;
  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(()->{
      while (flag){
        i++;
      }
      System.out.printf("**********test1 跳出成功, i=%d **********\n", i);
    });
    thread.start();
    Thread.sleep(1);
    flag = false;
    System.out.printf("**********test1 main thread 结束, i=%d **********\n", i);
  }


}
