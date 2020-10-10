package com.moer.day.Thread.fish;

import java.util.List;

public class Producer implements Runnable{
    private List<Object> goods;
    private String name;
    public Producer(List<Object> goods, String name){
        this.name = name;
        this.goods = goods;
    }
    public void product() throws InterruptedException {
        synchronized (goods) {//synchronized同步锁goods
            while (goods.size() == 10) {
                System.out.println(Thread.currentThread().getName() + " " + name + " " + " goods is full");
                goods.wait();//线程进入等待状态，并且释放锁goods
            }
            //商品没有满或者处于等待状态的某生产线程唤醒后，执行以下代码
            goods.add(new Object());
            System.out.println(Thread.currentThread().getName() + " " + name + " product,now goods'count is " + goods.size());
            goods.notifyAll();//唤醒在等待的线程
        }
    }

    @Override
    public void run() {
        try {
            product();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
