package com.moer.day.Pattern.Adapter;

public class Test {
    public static void main(String[] args) {
        TargetTable targetTable = new Adapter();
        targetTable.method1();
        targetTable.method2();
        System.out.println("========对象适配==========");
        Source s = new Source();
        TargetTable targetTable1 = new Wrapper(s);
        targetTable1.method2();
        targetTable1.method1();
    }
}
