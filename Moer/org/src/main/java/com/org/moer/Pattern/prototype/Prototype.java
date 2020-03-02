package com.org.moer.Pattern.prototype;

public class Prototype implements Cloneable{
    /**
     * 描述: [
     * 浅复制：将一个对象复制后，基本数据类型的变量都会重新创建，而引用类型，
     * 指向的还是原对象所指向的。
     * 深复制：将一个对象复制后，不论是基本数据类型还有引用类型，都是重新创建的。简单来说，就是深复制进行了完全彻底的复制，而浅复制不彻底]
     * <p>文件名称: Prototype.java </p>
     * @author <a href="mail to: chenxh@lenovocloud.com" rel="nofollow">chenxh</a>
     * @CreateDate: 2018/10/10 </p>
     * @version v1.0
     * <p>公司信息: 联想云 后台研发部</p>
     */


    private  String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone(){

        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }

    public static void main(String[] args) {
        Prototype prototype = new Prototype();
        Prototype prototype1 = (Prototype) prototype.clone();
        System.out.println(prototype == prototype1);
    }
}
