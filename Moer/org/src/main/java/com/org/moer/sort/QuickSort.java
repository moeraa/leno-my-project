package com.org.moer.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * QuickSort * 1.对一个未排序序列，假设从该序列中的元素中取一个基准值pivotkey，将<pivotkey放左边 >pivotkey放右边
 * * 2.接着以该k为中间，左右两边的分割作为新的序列，重新进行1操作
 * * <p>
 * * 快排因为用到了递归操作，所以在简单排序中性能不如直接插入排序
 * * 而在大量数据排序时，递归产生的性能影响对于算法的整体性能优势可以忽略
 * * <p> * 时间复杂度分析：
 * * 最坏情况：待排序为正序或逆序，这样每次分割后的子序列一个之比上一次序列少一个元素，一个为空
 * * 如 1 2 3 4 5 pivotkey=1;分割后一个序列为 2 3 4 5 一个为空
 * * 最终O(n^2)
 * * 最好情况：每一次分割都能平分，很均匀 O(n*logn)
 * * 平均情况：O(n*logn) 数学归纳法
 * * 空间复杂度：主要有地柜而产生的对栈空间的影响
 * * 最好：O(logn)
 * * 最坏:O(n) * 平均：O(logn) * 稳定性 不稳定 比较和交换是跳跃进行的
 * * <p> * 如何合理基准值pivotkey
 * * 该值的取值对该算法有相当影响，若pivotkey取到了最大或最小，都会增加算法复杂度，影响性能
 * * 1.随机选取，在待排序列中随机选取，以降低取到最大或最小值的概率
 * * 2.三数取中，在待排序列的左端，中间，右端去三个值选取中位数，节省随机数产生的时间开销，以降低取到最大或最小值的概率
 * * 三数取中时，比较的同时应将三个元素按中间，小，大的顺序重新排好位置
 * * 3.九数取中，三次取样，每次取三个数，取它们的中位数，再取三个中位数的中位数
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] a = new int[10];
        boolean flag = true;
        //random array
        for (int i = 0; i < a.length; i++) {
            Random rd = new Random();
            a[i] = rd.nextInt(10);
        }
        System.out.println("Random Array :");
        System.out.println(Arrays.toString(a));
        System.out.println();
        System.out.println("Quick Sort :");
        //快速排序开始
        quickSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    /**
     * @param a
     * @param low
     * @param high
     */
    public static void quickSort(int[] a, int low, int high) {
        //该值定义了从哪个位置开始分割序列
        int pivot;
        //当high-low大于某一值时适合快速排序
        // if ((high - low) >MAX_LENGTH_INSERT_SORT) 该值取7或50
        if (low < high) {
            //partition方法对序列进行排序
            pivot = partition(a, low, high);
            //分割两个序列继续进行快排操作
            quickSort(a, low, pivot - 1);
            quickSort(a, pivot + 1, high);
        }
    }

    /**
     * @param a
     * @param low
     * @param high
     * @return
     */

    public static int partition(int[] a, int low, int high) {
        //取每个序列的第一个值作为基准值
        int pivotkey = a[low];
        while (low < high) {
            //从序列的右边开始往左遍历，直到找到小于基准值的元素
            while (high > low && a[high] >= pivotkey) {
                high--;
            }
            //将元素直接赋予给左边第一个，即pivotkey所在的位置
            a[low] = a[high];
            a[high] = pivotkey;
            //从序列的左边边开始往右遍历，直到找到大于基准值的元素
            while (high > low && a[low] <= pivotkey) {
                low++;
            }
            //此时的a[high]<pivotkey,已经被赋予到左边，所以可以将元素赋予给a[high]
            a[high] = a[low];
            a[low] = pivotkey;
        }
        //最后的low是基准值所在的位置
        a[low] = pivotkey;
        return low;
    }
}