package com.moer.day.leetCode;


/**
 * @author chenxh
 * @date 2020/10/10  1:51 下午
 * @func 三步法解决数组翻转问题
 * <p>
 * 题目描述
 * 字符串旋转:
 * 给定两字符串A和B，如果能将A从中间某个位置分割为左右两部分字符串（都不为空串），并将左边的字符串移动到右边字符串后面组成新的字符串可以变为字符串B时返回true。
 * 例如：如果A=‘youzan’，B=‘zanyou’，A按‘you’‘zan’切割换位后得到‘zanyou’和B相同返回true。
 * <p>
 * 解法：三步翻转法
 * <p>
 * 1. X=you  Y = zan
 * 2. 翻转X：uoy 翻转Y ：naz 结果 result =  uoynaz
 * 翻转Z ：zanyou
 * <p>
 * <p>
 * 给定一个字符串，逐个翻转字符串中的每个单词。
 * <p>
 * 说明：
 * <p>
 * 无空格字符构成一个 单词 。
 * 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入："the sky is blue"
 * 输出："blue is sky the"
 * 示例 2：
 * <p>
 * 输入："  hello world!  "
 * 输出："world! hello"
 * 解释：输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-words-in-a-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ReverseString {

    public static void main(String[] args) {
        String str = "  ab good   example";
        ReverseString reverseString = new ReverseString();
//        System.out.println(reverseString.reverseWords(str));
        System.out.println(reverseString.reverseWordsGood(str));
    }

    public String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        s = s.trim();
        String[] strings = s.split(" ");
        StringBuilder resverseStr = new StringBuilder();
        char[] charArray1 = null;
        if (strings.length > 0) {
            for (String s1 : strings) {
                //如果是空格 将不做转换
                if (!isBlank(s1)) {
                    char[] charArray = s1.toCharArray();
                    reverse(charArray);
                    resverseStr.append(" ");
                    resverseStr.append(charArray);
                }
            }
            charArray1 = resverseStr.toString().toCharArray();
            reverse(charArray1);
        }
        return new String(charArray1);
    }

    /**
     * 判断字符串是否是空格
     *
     * @param cs
     * @return boolean
     * @date 2020/10/10 chenxh
     */
    public boolean isBlank(String cs) {
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void reverse(char[] charArray) {
        //eht yks si eulb
        int end = charArray.length - 1;
        int half = charArray.length / 2;
        for (int start = 0; start < half; start++) {
            char temp = charArray[start];
            charArray[start] = charArray[end];
            charArray[end] = temp;
            end--;
        }
    }

    //最优解  三部转换
    public String reverseWordsGood(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        s = s.trim();
        char[] ch = s.toCharArray();
        int j = 0;
        //挪动字符串，记录最后结尾的长度
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] != ' ' || (j > 0 && ch[j - 1] != ' ')) {
                ch[j++] = ch[i];
            }
        }
        System.out.println(new String(ch));
        int end = j - 1;
        int pre = 0;
        for (int i = 0; i <= end; i++) {
            if (ch[i] == ' ') {
                reverse(ch, pre, i - 1);
                pre = i + 1;
                System.out.println(new String(ch));
            }
        }
        System.out.println(new String(ch));//ba doog examplele
        reverse(ch, pre, end);
        System.out.println(new String(ch));//ba doog elpmaxele
        reverse(ch, 0, end);
        System.out.println(new String(ch)); //example good able
        return new String(ch, 0, end + 1);
    }

    private void reverse(char[] ch, int i, int j) {
        while (i < j) {
            char tmp = ch[i];
            ch[i] = ch[j];
            ch[j] = tmp;
            i++;
            j--;
        }
    }
}
