package com.moer.day.java.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxh
 * @since 2020/10/26  5:00 下午
 */
public class TestGenerics {
    public static void main(String[] args) {
        //<? extends E> 是Upper Bound（上限）通配符，用来限制元素的类型上限

        //表示集合中所有的元素类型上限为Fruit类型，即只能是Fruit或者Fruit的子类
        List<? extends Fruit> fruits;
        fruits = new ArrayList<Apple>();
        fruits = new ArrayList<Fruit>();
        //编译不通过
        // fruits = new ArrayList<Object>();
        /*//编译不通过
        fruits.add(new Apple());
        //编译不通过
        fruits.add(new Fruit());
        //
        1.写入：为何不能编译不通过？
        解释：因此<? extends Fruit> 只告诉编译器集合中元素的类型上限，但是具体是什么类型编译器是不知道的，
        fruits 可以指向ArrayList<Fruit>.也可以指向ArrayList<Apple>、ArrayList<Banana>，也就是说它的类型是不确定的，既然是不确定的，
        ，为了类型安全，编译器只能阻止添加元素，但fruits此时指向ArrayList<Banana>，显然类型就不兼容了。当然null除外，因为它可以表示任何类型

        2.读取：无论fruits指向的什么，编译器都可以确定获取的元素是Fruit类型，所有读取集合中元素都是允许的。

        3.补充， <?>是<? extends Object>的简写
        */
// ----------------------------------

        //<? super E>是Lower Bound （下限）的通配符，用来限制元素的类型下限
        //表示集合中元素下限为 Apple类型，即只能是Apple或者Apple的父类，
        List<? super Apple> fruits1;
        fruits1 = new ArrayList<Apple>();
        /*  fruits1 = new ArrayList<Fruit>();
        fruits1 = new ArrayList<Object>();*/
        /*
         * 1.写入：
         * 因为apples中装的是apple或者apple的某个父类，我们无法确定是哪个具体类型，
         * 但是可以确定是apple 和apple的子类是和这个"不确定的类型"兼容的，
         * 因为她肯定是这个"不确定类型的"子类，也就是说我们可以往集合中添加apple或者apple的子类对象
         * 2.读取：
         *  编译器允许从apples中获取元素的，但是无法确定获取的元素具体是什么类型，只能确定一定是object的子类，
         *  因此我们想获取得存储进去的对应类型元素，就只能强制类型转换。
         * */
        fruits1.add(new Apple());
        fruits1.add(new RedApple());
/*       //无法添加Fruit的任何父类对象，它无法添加Fruit的任何父类对象，举个例子，当你往apples中添加一个Fruit类型对象时，
        // 但此时apples指向ArrayList<Apple>，显然类型就不兼容了，Fruit不是Apple的子类
        fruits1.add(new Fruit());*/


    }
}
