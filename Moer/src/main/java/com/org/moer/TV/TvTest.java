package com.org.moer.TV;

public class TvTest {
    public static void main(String[] args) {
        XiaoMiTV xiaoMiTV = new XiaoMiTV("1","cc",1L,"白");
        XiaoMiTV xiaoMiTV2 = new XiaoMiTV("2","mm",1L,"白");
        System.out.println(xiaoMiTV.equals(xiaoMiTV2));
    }
}
