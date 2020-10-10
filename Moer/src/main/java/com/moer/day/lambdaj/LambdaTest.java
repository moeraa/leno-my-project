package com.moer.day.lambdaj;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.closure.Closure;

import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.*;
import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.Matchers.greaterThan;

public class LambdaTest {
    public static void main(String[] args) {
        List<Product> productList = ProductFactory.genRandomProduct(10);
        printl(1);
        //1.简单例子
        //对每个product都设置price为100
        //forEach(ps).setPrice(100); //对每个product都设置setPrice为100
        //按重量从小到大进行排序
        productList = Lambda.<Product>sort(productList,on(Product.class).getWeight());
        print(productList);
        printl(2);

        //2.joinForm 连接某一个属性，中间默认用逗号加空格(", ")可以自定义分隔符
        String names = joinFrom(productList).getName();
        // 连接每一个product对象的name，间隔符为":"
        String names1 = joinFrom(productList,": ").getName();
        System.out.println(names);
        System.out.println(names1);
        printl(3);

        //3.select
        //从集合或只能怪选出重量大于500的
//        Matcher matcher = new PatternMatcher("<500D")
//        List<Product> productList1 = select(productList,having(on(Product.class).getWeight(), "<500d");
//        print(productList1);
        printl(4);

        //4.selectMin找到最小的, also selectMax
        //找到重量最轻的产品
        Product pro_weight_min = selectMin(productList,on(Product.class).getWeight());
        System.out.println(pro_weight_min);
        printl(5);
        //5.max and maxFrom, also min and minFrom
        //找到最重的产品的重量（两种方法）
        //5.1
        Double weight1 = max(productList,on(Product.class).getWeight());
        //5.2
        Double weight2 = maxFrom(productList).getWeight();
        System.out.println(weight1 + "," + weight2);

        printl(6);

        //6.extract 抽取所有对象的某一列返回值
        //得到每个产品的id
        //6.1 使用on
        List<Integer> ids = extract(productList,on(Product.class).getId());
        System.out.println(ids);
        List<Integer> ids2 = extractProperty(productList,"id");
        System.out.println(ids2);
        printl(7);
        // 7.index 返回一个map其中value 是每个对象，key是对象的某一个属性
        //返回一个key为id value是对象的map
        Map<Integer,?> map = index(productList,on(Product.class).getId());
        print(map);
        printl(8);
        //8.with 使用更为流畅的方法
        //找到重量大于400的产品的id列表，并排序
        //8.1原始方法
        List<Double> prices1 = sort(
                extract(
                        select(productList, having(on(Product.class).getWeight(), greaterThan(400.0))),
                        on(Product.class).getPrice()
                ),
                on(Double.class).doubleValue()
        );

        //8.2使用with的方法
        List<Double> prices2 = with(productList)
                .retain(having(on(Product.class).getWeight(), greaterThan(400.0)))
                .extract(on(Product.class).getPrice())
                .sort(on(Double.class).doubleValue());

        System.out.println(prices1);
        System.out.println(prices2);

        printl(9);
        //9.闭包 ，of(T t) 返回T类型
        Closure println = closure();
        of(System.out).println(var(String.class));
        println.apply("hello");
        println.each("hello","oschina");


    }
    static void print(Map<?,?>map){
        for (Map.Entry<?,?> entry:map.entrySet()
        ) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
    static void print(List <Product>list){
        for (Product entry:list) {
            System.out.println(entry.toString());
        }
    }
    static void printl(int itemNo){
        System.out.println("----------------"+itemNo+"------------------");
    }
}
