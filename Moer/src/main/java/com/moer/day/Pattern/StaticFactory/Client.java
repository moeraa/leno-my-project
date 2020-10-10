package com.moer.day.Pattern.StaticFactory;

public class Client {


    public static void main(String[] args) {

        Food x =  StaticFactory.getAFood();
        x.printFoodName();

        StaticFactory staticFactory = new StaticFactory();
        x = staticFactory.get("B");
        x.printFoodName();
    }
}
