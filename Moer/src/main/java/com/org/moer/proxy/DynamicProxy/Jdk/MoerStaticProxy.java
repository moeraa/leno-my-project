package com.org.moer.proxy.DynamicProxy.Jdk;

import com.org.moer.proxyDao.Girl.Girl;

/**
 * @Auther: moer
 * @Date: 2019/9/24 20:52
 * @Description:
 */
public class MoerStaticProxy implements Girl {
    public MoerStaticProxy(Girl girl) {
        this.girl = girl;
    }

    private Girl girl; //包含真实的对象

    @Override
    public void date() {
        System.out.println("static before ....");
        girl.date();
        System.out.println("static before ....");
    }


    @Override
    public void watchMovie() {
        System.out.println("static before ....");
        girl.watchMovie();
        System.out.println("static before ....");

    }


}
