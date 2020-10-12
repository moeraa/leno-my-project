package com.moer.day.leetCode;

import java.util.Arrays;
import java.util.HashSet;

public class Soluction {
    /*
    给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
    不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。

    给定 nums = [0,0,1,1,1,2,2,3,3,4],
    函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
    你不需要考虑数组中超出新长度后面的元素。
    解决办法：
    如果i位置的数字等于number位置的数字，说明该数字重复出现，不予处理；如果i位置的数字不等于number位置的数字，说明该数字没有重复，需要放到l的下一位置，并使number加1。
     * */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int number = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != nums[number]) {
                number++;
                nums[number] = nums[i];
            }
        }
        number = number + 1;//返回数组的个数
        System.out.println(Arrays.toString(nums));
        return number;
    }

    /*
    给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
    设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
    注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
    1.可以交易多笔
    2.相邻价格中，只要后一次比前一次高，就买入
    */
    public int maxProfit(int[] prices) {
        if (prices.length <= 0) {
            return 0;
        }
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - prices[i - 1] > 0) {
                max += (prices[i] - prices[i - 1]);
            }
        }
        return max;

    }

    /**
     * 经典方法，三次倒置数组中对应位置的元素;
     * 简单说一下原理：数组元素右移k个位置的结果是，原来在
     * 后面的k个元素跑到了数组前面，原来在前面的length-k
     * 个元素，跑到了数组的后面，并且前后两部分元素各自的顺序和
     * 移动前一致，而倒置整个数组元素就是让后面k个元素跑到前面去，
     * 让前面length-k个元素跑到后面去，但是倒置之后前后两部分
     * 元素的顺序跟移动之前不一样了，倒置了，所以要把两部分的元素
     * 倒置回来
     * @param array
     * @param k
     */
    public static int[] moveArrayElement3(int[] array, int k) {
        // 倒置所有元素
        reverse(array);
        // 倒置前k个元素
        reverse(array, 0, k - 1);
        // 倒置后length - k个元素
        reverse(array, k, array.length - 1);
        return array;
    }

    /**
     * 倒置数组中begin和end之间的元素，包括begin和end
     * @param array
     * @param begin
     * @param end
     */
    private static void reverse(int[] array, int begin, int end) {
        int length = end - begin + 1;
        int half = length / 2;
        for(int i = 0; i < half; i++) {
            int temp = array[begin];
            array[begin] = array[end];
            array[end] = temp;
            begin++;
            end--;
        }

    }

    /**
     * 倒置数组中begin和end之间的元素，包括begin和end
     * @param array
     */
    private static void reverse(int[] array) {
        reverse(array, 0, array.length - 1);
    }

    /**
     * 每次把数组中所有元素移动一个位置，移动k轮
     * @param array
     * @param k
     */
    public static int[] moveArrayElement(int[] array, int k) {
        int length = array.length;
        // 右移newk + n * length个位置，和右移newk个位置效果是一样的
        int newk = k % length;
        int temp = 0;
        for(int i = 0; i < newk; i++) {
            temp = array[length - 1];
            for(int j = length - 2; j >= 0; j--) {
                array[j+1]=array[j];
            }
            array[0]=temp;
        }
        return array;
    }
    /**
     * 开辟一个新数组，把旧数组中的元素直接放在新数组中正确的位置
     * @param array
     * @param k
     * @return
     */
    public static int[] moveArrayElement1(int[] array, int k) {
        int length = array.length;
        // 右移newk + n * length个位置，和右移newk个位置效果是一样的
        int newk = k % length;
        int[] newArray = new int[length];
        // 重复length次把元素从旧位置移到新位置
        for(int i = 0; i < length; i++) {
            // 求出元素新的位置
            int newPosition = (i + newk) % length;
            newArray[newPosition] = array[i];
        }
        return newArray;
    }
    public boolean containsDuplicate(int[] nums) {
        if (nums.length <= 1){
            return false;
        }
        for (int i = 0; i <nums.length ; i++) {
            for (int j = i; j < nums.length; j++) {
                if (nums[i] == nums[j]){
                    return true;
                }

            }
            
        }
        
        return false;
    }

    public boolean containsDuplicate1(int[] nums) {


        HashSet<Integer> set = new HashSet<Integer>();
        for(int i =0;i<nums.length;i++){
            if(set.contains(nums[i])){
                return true;
            }else{
                set.add(nums[i]);
            }

        }

        return false;
    }
   // 然后看到了别人写的代码中用到了Arrays工具类中提供的排序算法(sort),底层好像也是快排，具体我没有太看。下面是代码，这样就可以干掉97%左右的人了。

    public static boolean containsDuplicate2(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1 ; i < nums.length ; i++) {
            if (nums[i - 1] == nums[i]) {
                return true;
            }
        }
        return false;
    }
    //求一个无序数组的中位数
    public int  quickStart(int [] nums,int left,int right){

        int point = nums[right];
        while (left < right){
            while (left<right && nums[left]<=point){
                left++;
            }
            nums[right] =nums[left];
            while (left<right && nums[right]>=point){
                right--;
            }
            nums[left] = nums[right];
        }
        nums[left] = point;
        return  left;
    }
    public int quickSort(int[] nums,int left,int right){
        int mid  = (right-1)/2;
        int div = quickStart(nums,left,right);//求出中间的位置
        while (div != mid)
        {
            if (div>mid){

                div =quickStart(nums,0,div-1); //左边
            }

            if (div<mid){
                div = quickStart(nums,div+1,right); //右边
            }
        }

        return  nums[mid];
    }
    public boolean containsDuplicateMy(int [] nums){
        boolean result = false;
        for ( int i = 0;i<nums.length;i++){
            for (int j = i+1;j<nums.length;i++){
                if (nums[i] == nums[j]){
                    return true;
                }
            }
        }
        return result;
    }
    public boolean containsDuplicateMy1(int [] nums){
        boolean result = false;
        HashSet<Integer> numSet = new HashSet(nums.length);
        numSet.add(nums[1]);
        for (int i =1 ;i<nums.length;i++){
            if (numSet.contains(nums[i])){
                result = true;
            }else {
                numSet.add(nums[i]);
                result = false;
            }
        }
        return result;
    }




    public static void main(String[] args) {
        Soluction soluction = new Soluction();
        int[] nums = {33, 44, 77,8,44, 55};
        System.out.println(soluction.removeDuplicates(nums));
//        System.out.println(soluction.removeDuplicates(nums));
//        System.out.println(Arrays.toString(soluction.moveArrayElement1(nums,2)));
//        System.out.println(soluction.containsDuplicate1(nums));
//        System.out.println((soluction.quickSort(nums,0,nums.length-1)));
//        System.out.println(soluction.containsDuplicateMy1(nums));

    }

}
