package com.moer.day.lambda;

import java.util.function.Consumer;

public class consumerDemo {
    public static void donation(Integer money,Consumer<Integer>consumer){
        consumer.accept(money);
    }

    public static void main(String[] args) {
        donation(1000,money-> System.out.println("捐钱"+money+"元"));
    }
}
