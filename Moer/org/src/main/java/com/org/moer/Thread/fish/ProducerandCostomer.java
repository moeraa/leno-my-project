package com.org.moer.Thread.fish;

import java.util.ArrayList;
import java.util.List;

public class ProducerandCostomer {

    public static void  main(String[] args){
        List<Object> goods = new ArrayList<Object>();
        Thread p1=new Thread(new Producer(goods,"Producer1"));
        p1.start();
        Thread c1 = new Thread(new Consumer(goods,"Consumer1"));
        c1.start();
        Thread c2 = new Thread(new Consumer(goods,"Consumer2"));
        c2.start();
//        Thread c3 = new Thread(new Consumer(goods,"Consumer3"));
//        c3.start();
        Thread p2 = new Thread(new Producer(goods,"Producer2"));
        p2.start();
        Thread p5 = new Thread(new Producer(goods,"Producer3"));
        p5.start();
    }

}
