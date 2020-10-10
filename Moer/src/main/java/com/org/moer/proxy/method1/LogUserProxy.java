package com.org.moer.proxy.method1;

import com.org.moer.proxyDao.UserDaoImpl;
import sun.nio.cs.US_ASCII;

/**
 * @Auther: moer
 * @Date: 2019/9/24 08:47
 * @Description: 继承 实现代理---静态代理
 * 缺点： 类爆炸；单继承
 */
public class LogUserProxy extends UserDaoImpl {

  @Override
  public void query(String name) {
    System.out.println("log .proxy 1.....");
    super.query(name);
  }


}
