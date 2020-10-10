package com.moer.day.Pattern.AbstractFactory;

public class AFoodFactory implements Provider {
    @Override
    public Food produce() {
        return new AFood();
    }
}
