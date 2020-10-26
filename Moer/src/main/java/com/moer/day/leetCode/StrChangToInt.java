package com.moer.day.leetCode;

/**
 * @author chenxh
 * @date 2020/10/12  1:56 下午
 * @func 字符串转换成整数
 * 题目描述
 * 输入一个由数字组成的字符串，把它转换成整数并输出。例如：输入字符串"123"，输出整数123。
 * <p>
 * 给定函数原型int StrToInt(const char *str) ，实现字符串转换成整数的功能，不能使用库函数atoi。
 */
public class StrChangToInt {
    public static void main(String[] args) {
        StrChangToInt strChangToInt = new StrChangToInt();
        System.out.println((int) '0');
/*        System.out.println(strChangToInt.myAtoi("-2147483648") == -2147483648);
        System.out.println(strChangToInt.myAtoi("-+2") == 0);
        System.out.println(strChangToInt.myAtoi("3.0333") == 3);
        System.out.println(strChangToInt.myAtoi("-91283472332") == -2147483648);
        System.out.println(strChangToInt.myAtoi("21474836460") == 2147483647);*/
        System.out.println(strChangToInt.myAtoi("-2147483648") == -2147483648);
//        System.out.println(strChangToInt.myAtoi("-2147483647") == -2147483647);
/*        System.out.println(strChangToInt.myAtoi("42") == 42);
        System.out.println(strChangToInt.myAtoi("-42") == -42);
        System.out.println(strChangToInt.myAtoi("4193 with words"));*/

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println();
        System.out.println(-Integer.MIN_VALUE);
        System.out.println(Integer.MIN_VALUE % 10);
    }

    public int myAtoiGood(String string) {
        String str = string.trim();
        if ("".equals(str) || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int step = 10;
        int result = 0;
        char sign = chars[0];
        //qqqq3333   =0
        //3333sss=3333
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c >= '0' && c <= '9') {
                int charInt = c - 48;
                if (sign == '-') {
                    int tmp = -result;
                    //7   > 8
                    //8>=8
                    System.out.println(charInt);
                    System.out.println(Integer.MIN_VALUE % 10);
                    // resutl>(Integer.MAX_VALUE - charInt)/10
                    if (tmp < Integer.MIN_VALUE / 10 || (tmp == Integer.MIN_VALUE / 10 && (-charInt <= (Integer.MIN_VALUE % 10)))) {
                        return Integer.MIN_VALUE;
                    }
                }
                //处理溢出问题 8 <7
                if (sign != '-' && result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && (charInt > Integer.MAX_VALUE % 10))) {
                    return Integer.MAX_VALUE;
                }
                result = (result * step) + charInt;
                System.out.println(result);
            } else {
                //遇到非正常字符 判断第一个字符是否为 + 或者 -
                if (i == 0) {
                    if (c != '-' && c != '+') {
                        return 0;
                    }
                } else {
                    break;
                }
            }
        }
        //如果是负数 需要翻转
        if (sign == '-') {
            return -result;
        }
        return result;
    }

    public int myAtoi(String s) {
        char[] chars = s.toCharArray();
        int length = chars.length;

        int idx = 0;
        boolean negative = false;
        while (idx < length && chars[idx] == ' ') {
            idx++;
        }
        if (idx == length) {
            return 0;
        }
        if (chars[idx] == '-') {
            negative = true;
            idx++;
        } else if (chars[idx] == '+') {
            idx++;
        } else if (!Character.isDigit(chars[idx])) {
            return 0;
        }

        int ans = 0;
        while (idx < length && Character.isDigit(chars[idx])) {
            int digital = chars[idx] - '0';
            if (ans > (Integer.MAX_VALUE - digital) / 10) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            ans = ans * 10 + digital;

            idx++;
        }

        return negative ? -ans : ans;
    }
}
