package com.moer.day.leetCode;

/**
 * @author chenxh
 * @date 2020/10/12  10:06 上午
 * @func 字符串包含题目
 * 题目描述
 * 给定两个分别由字母组成的字符串A和字符串B，字符串B的长度比字符串A短。请问，如何最快地判断字符串B中所有字母是否都在字符串A里？
 * <p>
 * 为了简单起见，我们规定输入的字符串只包含大写英文字母，请实现函数bool StringContains(string &A, string &B)
 * <p>
 * 比如，如果是下面两个字符串：
 * <p>
 * String 1：ABCD
 * <p>
 * String 2：BAD
 * <p>
 * 答案是true，即String2里的字母在String1里也都有，或者说String2是String1的真子集。
 * <p>
 * 如果是下面两个字符串：
 * <p>
 * String 1：ABCD
 * <p>
 * String 2：BCE
 * <p>
 * 答案是false，因为字符串String2里的E字母不在字符串String1里。
 * <p>
 * 同时，如果string1：ABCD，string 2：AA，同样返回true。
 */
public class ContainString {
    public static void main(String[] args) {
        ContainString containString = new ContainString();
        System.out.println(containString.contain("ABCD", "AF"));
        System.out.println(containString.javaContain("ABCD", "A"));
    }

    public boolean javaContain(String parent, String sun) {
        if (parent.contains(sun)) {
            return true;
        }
        return false;
    }

    //解法：
    //总共有26个字母，开辟26的一个int数据，此方法可以计算出 出现过最多字符的的字母
    // 计数排序方法，将遍历字母 如果出现过，标志位1 这样遍历父串和子串就行
    public boolean contain(String parent, String sun) {
        if ("".equals(parent) || "".equals(sun) || parent.length() == 0 || parent.length() < sun.length()) {
            return false;
        }
        int[] countArray = new int[26];
        //(char -'A') == index
        for (char c : parent.toCharArray()) {
            int index = c - 'A';
            if (index < 26) {
                countArray[index]++;
            }
        }
        // 遍历子串 如果父串的数组中为零 讲返回false
        for (char c : sun.toCharArray()) {
            int index = c - 'A';
            if (index < 26) {
                if (countArray[index] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
