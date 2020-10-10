package com.moer.day;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: moer
 * @Date: 2020/1/2 10:10
 * @Description:
 */public class WeakBasedYeae {

  public static void main(String[] args) {
    HashMap hashMap = new HashMap(16);
    ReentrantLock lock = new ReentrantLock();
    Calendar calendar = Calendar.getInstance();
    // 2019-12-31
    calendar.set(2019,Calendar.DECEMBER,31);
    Date time = calendar.getTime();
    System.out.println(time);
    // 小写 YYYY
    Date strDate1 = calendar.getTime();
     // 2020-01-01
    Calendar calendar1 = Calendar.getInstance();
    calendar1.set(2020,Calendar.JANUARY,1);
    // 大写 YYYY
    DateFormat formatLowerCaseyyy = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat formatUpperCaseYYY = new SimpleDateFormat("YYYY-MM-dd");
    Date strDate2 = calendar1.getTime();

    System.out.println("2019-12-31 to YYYY/MM/dd: "+formatUpperCaseYYY.format(strDate1));
    System.out.println("2020-01-01 to YYYY/MM/dd: "+formatUpperCaseYYY.format(strDate2));

    System.out.println("2019-12-31 to yyyy/MM/dd: "+formatLowerCaseyyy.format(strDate1));
    System.out.println("2020-01-01 to yyyy/MM/dd: "+formatLowerCaseyyy.format(strDate2));

  }


}
