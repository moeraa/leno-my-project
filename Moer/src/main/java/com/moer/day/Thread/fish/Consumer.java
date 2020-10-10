package com.moer.day.Thread.fish;

import java.util.List;

public class Consumer implements Runnable {

    private List<Object> goods;
    private String name;
    public Consumer(List<Object> goods,String name){
        this.name = name;
        this.goods = goods;
    }
    public void consume() throws InterruptedException {
        synchronized (goods){//synchronized同步锁goods
            while(goods.size()==0){
                System.out.println(Thread.currentThread().getName()+" "+name+" "+" goods is empty");
                goods.wait();//线程进入等待状态，释放锁goods;
            }
            //商品没有空或者处于等待状态的某消费线程唤醒后，执行以下代码
            goods.remove(0);
            System.out.println(Thread.currentThread().getName()+"  "+name+" consume,now goods'count is "+goods.size());
            goods.notifyAll();//唤醒在等待的线程
        }
    }

    @Override
    public void run() {
        try {
            consume();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
