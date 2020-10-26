package com.moer.day.leetCode;

/**
 * @author chenxh
 * @date 2020/10/13  9:49 上午
 * @func 判断回文字段
 * 题目描述
 * 回文，英文palindrome，指一个顺着读和反过来读都一样的字符串，比如madam、我爱我，这样的短句在智力性、趣味性和艺术性上都颇有特色，中国历史上还有很多有趣的回文诗。
 * <p>
 * 那么，我们的第一个问题就是：判断一个字串是否是回文？
 * <p>
 * <p>
 * 最长回文子串
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * <p>
 * 示例 1：
 * <p>
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 * <p>
 * 输入: "cbbd"
 * 输出: "bb"
 */
public class Palindrome {

    public static void main(String[] args) {
        Palindrome palindrome = new Palindrome();
        String str = "6666abagggg";
        System.out.println(palindrome.isPalidrome(str));
        System.out.println(palindrome.isPalidromeV2(str));
    }

    public boolean isPalidrome(String str) {
        if ("".equals(str) || str.length() == 0) {
            return false;
        }
        char[] chars = str.toCharArray();
        int j = chars.length - 1;
        int i = 0;
        while (i < j) {
            if (chars[i] == chars[j]) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }
    public boolean isPalidromeV2(String str) {
        if ("".equals(str) || str.length() == 0) {
            return false;
        }
        char[] chars = str.toCharArray();
        int mid = chars.length / 2;
        System.out.println(mid + "   " + chars[mid]);
        for (int i = 0; i < mid; i++) {
            if (chars[mid + i] != chars[mid - i]) {
                return false;
            }
        }
        return true;
    }

    public String isLongPalidrome(String str) {

        if ("".equals(str) || str.length() == 0) {
            return str;
        }
        //按照缝隙来判断，遍历所有可能存在回文子串的见习
        // a  b  a  d  c
        //可能存在的间隙  1


        return "";
    }
    public String longestPalindrome(String s) {
        if(s.isEmpty()){
            return s;
        }
        String res=s.substring(0,1);
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String k=s.substring(i,j);
                String rk=new StringBuffer(k).reverse().toString();
                if(k.equals(rk)&&k.length()>res.length()){
                    res=k;
                }
            }

        }
        return res;
    }

}
