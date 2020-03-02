package com.org.moer.Pattern;

public class ideaSingleton {
/**
 * 描述: [类型描述] 
 * <p>文件名称: ideaSingleton.java </p>
 * @CreateDate: 2018/10/10 </p> 
 * @version v1.0
 * <p>公司信息: 联想云 后台研发部</p>
 * 使用场景：
 * ● 要求生成唯一序列号的环境；
 * ● 在整个项目中需要一个共享访问点或共享数据，例如一个Web页面上的计数器，可以不用把每次刷新都记录到数据库中，使用单例模式保持计数器的值，并确保是线程安全的；
 * ● 创建一个对象需要消耗的资源过多，如要访问IO和数据库等资源；
 * ● 需要定义大量的静态常量和静态方法（如工具类）的环境，可以采用单例模式（当然，也可以直接声明为static的方式）。
 */



    private static ideaSingleton ourInstance = new ideaSingleton();

    public static ideaSingleton getInstance() {
        return ourInstance;
    }

    private ideaSingleton() {
    }
}
