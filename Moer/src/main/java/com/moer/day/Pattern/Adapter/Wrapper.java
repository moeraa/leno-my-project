package com.moer.day.Pattern.Adapter;
/**
 * 描述: [对象适配]
 * <p>文件名称: Wrapper.java </p>
 * @author <a href="mail to: chenxh@lenovocloud.com" rel="nofollow">chenxh</a>
 * @CreateDate: 2018/10/11 </p>
 * @version v1.0
 * <p>公司信息: 联想云 后台研发部</p>
 */


public class Wrapper implements TargetTable {
    private Source source;

    public Wrapper(Source source) {
        super();
        this.source = source;
    }

    @Override
    public void method1() {
     source.method1();
    }

    @Override
    public void method2() {
        System.out.println("this is the targetable method ");
    }
}
