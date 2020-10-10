package com.moer.day.Pattern.AbstractFactory;

public class CFoodFactory implements Provider {
    @Override
    public Food produce() {
        return new CFood();
    }
}
