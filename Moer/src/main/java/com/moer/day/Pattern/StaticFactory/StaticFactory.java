package com.moer.day.Pattern.StaticFactory;

public class StaticFactory {
    StaticFactory(){}
    /**
     * @brief 属于静态工厂 直接使用静态方法来返回实例
     * @Detail <p>好处是 不需要实例化工厂类</p>
     * @param
     * @return  Food
     * @author <a href="mail to: chenxh@lenovocloud.com" rel="nofollow">chenxh</a>
     * @CreateDate: 2018/10/10 14:34</p>
     *
     */

    public static Food getAFood(){
        return new AFood();
    }
    public static Food getBFood(){
        return new BFood();
    }
    public static Food getCFood(){
        return new CFood();
    }
/**
 * @brief 普通工厂 方法 使用字符串去返回对应的实例，可能传入字符串有错
 * @Detail <p>普通工厂</p>
 * @param name
 * @return Food
 * @author <a href="mail to: chenxh@lenovocloud.com" rel="nofollow">chenxh</a>
 * @CreateDate: 2018/10/10 14:35</p>
 *
 */

    public Food get(String name){
        Food x = null;
        if ( name.equals("A")) {
            x = StaticFactory.getAFood();
        }else if ( name.equals("B")){
            x = StaticFactory.getBFood();
        }else  if (name.equals("C")){
            x = StaticFactory.getCFood();
        }else {
            System.out.println("请输入正确的食物名称 ");
        }
        return x;
    }

}
