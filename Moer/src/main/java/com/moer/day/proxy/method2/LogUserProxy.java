package com.moer.day.proxy.method2;

import com.moer.day.proxyDao.UserDao;

/**
 * @Auther: moer
 * @Date: 2019/9/24 08:51
 * @Description: 聚合 实现接口 ---静态代理
 * 代理对象，和目标对象 同时实现此方法
 * 优点：可复用
 * 缺点：1.代理内容写死，
 * 2.类爆炸
 *
 */
public class LogUserProxy implements UserDao {

  private UserDao userDao;

  public LogUserProxy(UserDao userDao) {
    this.userDao = userDao;
  }


  @Override
  public void query(String name) {
    System.out.println("log .proxy 1.....");
    userDao.query(name);
  }


}
