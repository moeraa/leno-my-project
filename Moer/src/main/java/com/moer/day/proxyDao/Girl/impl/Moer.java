package com.moer.day.proxyDao.Girl.impl;

import com.moer.day.proxyDao.Girl.Girl;

/**
 * @Auther: moer
 * @Date: 2019/9/24 20:25
 * @Description:
 */
public class Moer implements Girl {

    @Override
    public void date() {
        System.out.println("moer say :the happy day !!");
    }


    @Override
    public void watchMovie() {
        System.out.println("moer say : this is bad movie !!");
    }


}
