package com.moer.day.parallelcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: moer
 * @Date: 2019/8/22 11:00
 * @Description:
 */
public class LearnStream {

  public static void main(String[] args) throws InterruptedException {
    learnStream();


    Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8};
    List<Integer> listOfIntegers = new ArrayList<>(Arrays.asList(intArray));
    List<Integer> parallelStorage = new ArrayList<>();//Collections.synchronizedList(new ArrayList<>());
    listOfIntegers.parallelStream()
        // Don't do this! It uses a stateful lambda expression.
        .map(e -> {
          parallelStorage.add(e);
          return e;
        }).forEachOrdered(e -> System.out.print(e + " "));
    System.out.println();
    parallelStorage.stream().forEachOrdered(e -> System.out.print(e + " "));
    System.out.println();
    System.out.println("Sleep 5 sec");

    TimeUnit.SECONDS.sleep(5);
    parallelStorage.stream().forEachOrdered(e -> System.out.print(e + " "));

  }

  private static void learnStream() {
    List<Long> lists = new ArrayList<>();
    lists.add(1L);
    lists.add(2L);
    lists.add(3L);
    lists.add(4L);
    lists.add(5L);
    lists.add(6L);
    Optional<Long> sum = lists.parallelStream().reduce((a, b) -> a + b);
    if (sum.isPresent()) {
      System.out.println("list sum = " + sum.get());
    }

    Long sum2 = lists.stream().reduce(0L, (a, b) -> a + b);
    System.out.println("list sum = " + sum2);

    Optional<Long> product = lists.stream().reduce((a, b) -> a * b);
    if (product.isPresent()) {
      System.out.println("list * = " + product.get());
    }

    Long product1 = lists.parallelStream().reduce(1L, (a, b) -> a * b);
    System.out.println("list * = " + product1);

    Long product2 = lists.parallelStream().reduce(1L, (a, b) -> a * (b * 2));
    System.out.println("list * = " + product2);

    Long product3 = lists.parallelStream().reduce(1L, (a, b) -> a * (b * 2), (aLong, aLong2) -> aLong * aLong2);
    System.out.println("list * = " + product3);

  }


}
