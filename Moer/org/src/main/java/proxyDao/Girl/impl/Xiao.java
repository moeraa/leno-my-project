package proxyDao.Girl.impl;

import proxyDao.Girl.Girl;

/**
 * @Auther: moer
 * @Date: 2019/9/24 20:25
 * @Description:
 */
public class Xiao implements Girl {

    @Override
    public void date() {
        System.out.println("XIAO say :the happy day !!");
    }


    @Override
    public void watchMovie() {
        System.out.println("XIAO say : this is bad movie !!");
    }


}
