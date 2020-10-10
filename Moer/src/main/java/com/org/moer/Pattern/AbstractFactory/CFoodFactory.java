package com.org.moer.Pattern.AbstractFactory;

public class CFoodFactory implements Provider {
    @Override
    public Food produce() {
        return new CFood();
    }
}
