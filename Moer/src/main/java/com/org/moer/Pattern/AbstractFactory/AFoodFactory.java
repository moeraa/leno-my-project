package com.org.moer.Pattern.AbstractFactory;

public class AFoodFactory implements Provider {
    @Override
    public Food produce() {
        return new AFood();
    }
}
