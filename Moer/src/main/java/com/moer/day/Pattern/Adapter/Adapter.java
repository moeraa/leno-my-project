package com.moer.day.Pattern.Adapter;

/**
 * 描述: [类是适配器模式]
 * <p>文件名称: Adapter.java </p>
 * @author <a href="mail to: chenxh@lenovocloud.com" rel="nofollow">chenxh</a>
 * @CreateDate: 2018/10/11 </p>
 * @version v1.0
 * <p>公司信息: 联想云 后台研发部</p>
 */


public class Adapter extends Source implements TargetTable {
    @Override
    public void method2() {
        System.out.println("this is the targetable method！");
    }

}
