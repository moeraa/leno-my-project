package com.moer.day.proxy;

import com.moer.day.proxyDao.UserDao;
import com.moer.day.proxyDao.UserDaoImpl;
import com.moer.day.proxy.method2.LogUserProxy;

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
