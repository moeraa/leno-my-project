package com.org.moer.proxy;

import com.org.moer.proxy.method2.LogUserProxy;
import proxyDao.UserDao;
import proxyDao.UserDaoImpl;

/**
 * @Auther: moer
 * @Date: 2019/9/24 08:56
 * @Description:
 */
public class Test {

  public static void main(String[] args) {
    UserDao userDao = new UserDaoImpl();
    LogUserProxy logUserProxy = new LogUserProxy(userDao);
    logUserProxy.query("moer");
  }


}
