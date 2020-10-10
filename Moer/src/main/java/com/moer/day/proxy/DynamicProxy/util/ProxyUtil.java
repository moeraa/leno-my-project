package com.moer.day.proxy.DynamicProxy.util;

import javax.print.DocFlavor;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Auther: moer
 * @Date: 2019/9/24 09:03
 * @Description:
 */
public class ProxyUtil {
  /*
  * step1.得到一个代理对象的java文件
  * step2.编译文件为class文件
  * step3.classloader 加载磁盘上文件
  * step4.反射
  * step3.返回代理对象
  * */

  public static Object newProxyInstance(Object target) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    String content = "";

    //step1.得到一个代理对象的java文件
    Class<?> targetInfo = target.getClass().getInterfaces()[0];
    String packageContent = "package proxyDao ";

    //step2.编译文件为class文件
    File file = new File("d:\\rolename");
    if (!file.exists()) {
      file.createNewFile ();
    }
    FileWriter fileWriter = new FileWriter(file);
    fileWriter.append(packageContent);
    JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

   // step3.classloader 加载磁盘上文件
    URL[] urls = new URL[]{new URL("d://")};
    URLClassLoader urlClassLoader = new URLClassLoader(urls);
    Class<?> aa = urlClassLoader.loadClass("aa");

    Constructor<?> constructor = aa.getConstructor(targetInfo);
    Object proxy = constructor.newInstance(target);
    return proxy;
  }
}
