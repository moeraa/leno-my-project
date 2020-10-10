package com.moer.day.proxy.DynamicProxy.Jdk;

import com.moer.day.proxyDao.Girl.Girl;
import com.moer.day.proxyDao.Girl.impl.Moer;
import com.moer.day.proxyDao.Girl.impl.Xiao;
import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.Modifier;

/**
 * @Auther: moer
 * @Date: 2019/9/24 20:02
 * @Description:
 */
public class Boy {

    public static void main(String[] args) {

        Girl moer = new Moer();
        Girl xiao = new Xiao();


        //静态代理
        /*
        * 缺点：1.类爆炸 2.开闭原则
        *
        * */
        System.out.println("============静态代理======");
        MoerStaticProxy moerStaticProxy = new MoerStaticProxy(moer);
        moerStaticProxy.date();

        // 动态代理
        System.out.println("============动态代理111======");
        MoerHandler family = new MoerHandler(moer);
        Girl mother = (Girl) family.getProxyIntance();
        mother.date();
        System.out.println("============动态代理2222======");
        MoerHandler xiaoFamily = new MoerHandler(xiao);
        Girl xiaoMother = (Girl) xiaoFamily.getProxyIntance();
        xiaoMother.date();

        classFile(moer.getClass(),mother.getClass().getSimpleName());




    }


    public static void classFile(Class clazz,String proxyName){

        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                proxyName, new Class[]{clazz}, Modifier.FINAL);

        String path  = clazz.getResource(".").getPath();
        System.out.println(path);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path + proxyName + ".class");
            out.write(proxyClassFile);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
