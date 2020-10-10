package com.org.moer.bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author : hepw
 * @date : 2020/9/24 18:52
 * @func :
 */
public class Search {

    public static void main2(String[] args) {
        //int[] data1 = {1, 2, 3, 4, 56, 73, 65, 88, 23, 5};
        //int[] data2 = {11, 21, 31, 41, 561, 731, 65, 881, 231, 23};
        int[] data1 = randomArray(0, MAXBIT, MAXBIT / 10);
        int[] data2 = randomArray(0, MAXBIT, MAXBIT / 10);
        int[] data3 = randomArray(0, MAXBIT, MAXBIT / 10);
        int[] data4 = randomArray(0, MAXBIT, MAXBIT / 10);
        int[] data5 = randomArray(0, MAXBIT, MAXBIT / 10);
        ByteBitMap bitMap1 = new ByteBitMap(MAXBIT);
        ByteBitMap bitMap2 = new ByteBitMap(MAXBIT);
        ByteBitMap bitMap3 = new ByteBitMap(MAXBIT);
        ByteBitMap bitMap4 = new ByteBitMap(MAXBIT);
        ByteBitMap bitMap5 = new ByteBitMap(MAXBIT);
        for (int i = 0; i < data1.length; i++) {
            bitMap1.add(data1[i]);
        }
        for (int i = 0; i < data2.length; i++) {
            bitMap2.add(data2[i]);
        }
        for (int i = 0; i < data3.length; i++) {
            bitMap3.add(data3[i]);
        }
        for (int i = 0; i < data4.length; i++) {
            bitMap4.add(data4[i]);
        }
        for (int i = 0; i < data5.length; i++) {
            bitMap5.add(data5[i]);
        }
        /*bitMap1.display();
        bitMap2.display();*/
        Long s = System.currentTimeMillis();
        bitMap1.union(bitMap2);
        bitMap1.union(bitMap3);
        bitMap1.union(bitMap4);
        bitMap1.union(bitMap5);
        System.out.println("ss1:" + (System.currentTimeMillis() - s));
        s = System.currentTimeMillis();
        bitMap1.getValues();
        System.out.println("ss2:" + (System.currentTimeMillis() - s));
    }

    /**
     * 数据最大值
     */
    private static final int MAXBIT = Integer.MAX_VALUE / 64;

    public static void main(String[] args) {
        int[] data1 = randomArray(0, MAXBIT, MAXBIT / 10);
        int[] data2 = randomArray(0, MAXBIT, MAXBIT / 10);
        int[] data3 = randomArray(0, MAXBIT, MAXBIT / 10);
        int[] data4 = randomArray(0, MAXBIT, MAXBIT / 10);
        int[] data5 = randomArray(0, MAXBIT, MAXBIT / 10);
        SearchByBitMap searchByBitMap = new SearchByBitMap(MAXBIT);
        searchByBitMap.add("data1", data1);
        searchByBitMap.add("data2", data2);
        searchByBitMap.add("data3", data3);
        searchByBitMap.add("data4", data4);
        searchByBitMap.add("data5", data5);
        Long s = System.currentTimeMillis();
        searchByBitMap.union(new ArrayList<>(Arrays.asList("data1", "data2", "data3", "data4", "data5")));
        System.out.println("ss:" + (System.currentTimeMillis() - s));
    }

    /**
     * 随机指定范围内N个不重复的数
     * 在初始化的无重复待选数组中随机产生一个数放入结果中，
     * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换
     * 然后从len-2里随机产生下一个随机数，如此类推
     *
     * @param max 指定范围最大值
     * @param min 指定范围最小值
     * @param n   随机数个数
     * @return int[] 随机数结果集
     */
    public static int[] randomArray(int min, int max, int n) {
        int len = max - min + 1;

        if (max < min || n > len) {
            return null;
        }

        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }

}
