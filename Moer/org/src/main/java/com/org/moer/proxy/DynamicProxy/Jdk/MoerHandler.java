package com.org.moer.proxy.DynamicProxy.Jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther: moer
 * @Date: 2019/9/24 19:56
 * @Description: 动态代理
 */
public class MoerHandler implements InvocationHandler {

  //目标对象
  private Object girl;

  public MoerHandler(Object girl) {
    this.girl = girl;
  }

  /*
   * 获取动态代理的对象
   * */
  public Object getProxyIntance() {
    return Proxy.newProxyInstance(girl.getClass().getClassLoader(), girl.getClass().getInterfaces(), (proxy, method, args) -> {
      System.out.println("before log ..... parent");
      Object invoke = method.invoke(girl, args);
      System.out.println("after log ...... parent");
      return invoke;
    });

  }

  //反射机制
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("before log ..... parent");
    Object invoke = method.invoke(girl, args);
    System.out.println("after log ...... parent");
    return invoke;
  }


}
