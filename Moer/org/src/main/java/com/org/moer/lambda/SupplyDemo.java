package com.org.moer.lambda;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SupplyDemo {

    public static List<Integer> supply(Integer num, Supplier<Integer> supplier){
        List<Integer> resultList =new ArrayList<>();
        for (int i = 0; i <num ; i++) {
            resultList.add(supplier.get());
        }
        return resultList;
    }
    static {}
    static {}


    public static void main(String[] args) {
//        List<Integer> list = supply(10,()->(int)Math.random()*100);
        List<String> list = Arrays.asList("abc","","bc","abdc","jk;");
        list.forEach(System.out::println);
        list.stream().count();
        list.stream();
        list.parallelStream();
        List<String> strings = list.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
        System.out.println("=========================");
        strings.forEach(System.out::println);
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
        System.out.println("============sorted=============");
        random.ints().limit(10).sorted().forEach(System.out::println);
        List<Integer> numbers = Arrays.asList(3,4,5,6,7,3,2,2);
        List<Integer> squaresList = numbers.stream().map(
            integer -> integer * integer
        ).distinct().collect(Collectors.toList());
        squaresList.forEach(System.out::println);
        System.out.println("=========================");
        System.out.println( list.stream().filter(s -> s.isEmpty()).count());
        IntSummaryStatistics statistics = numbers.stream().mapToInt((x)->x).summaryStatistics();
        System.out.println("=========================");
        System.out.println(statistics.toString());

    }


}
