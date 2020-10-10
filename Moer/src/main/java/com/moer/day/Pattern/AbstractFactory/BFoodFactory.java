package com.moer.day.Pattern.AbstractFactory;

public class BFoodFactory implements Provider {
    @Override
    public Food produce() {
        return new BFood();
    }
}
