package com.org.moer.ppt.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author : hepw
 * @date : 2020/4/7 14:58
 * @func : 时间相关的公共方法，
 * 旧版指数的每一个公共方法
 * 请确认后再迁移到这里
 */
public class DateUtils {

    /**
     * 获取日期列表
     *
     * @param fromTime
     * @param toTime
     * @return
     */
    public static List<String> getDateListWithLocalDate(String fromTime, String toTime) {
        if (StringUtils.isEmpty(fromTime)
                || StringUtils.isEmpty(toTime)
                || fromTime.length() < 6
                || toTime.length() < 6) {
            return Arrays.asList();
        }
        if (fromTime.length() <= 7) {
            fromTime += "-01";
        }

        if (toTime.length() <= 7) {
            toTime += "-01";
        }
        LocalDate start = LocalDate.parse(fromTime);
        LocalDate end = LocalDate.parse(toTime);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> dateList = new ArrayList<>();
        int num = 0;
        while (start.compareTo(end) <= 0) {
            dateList.add(start.format(f));
            start = start.plusDays(1);
            num++;
            if (num >= 1000) {
                break;
            }
        }
        return dateList;
    }


    /**
     * func: 获取两个时间之间的月份列表 (没有时间格式化的错误判断需要提前判断)
     * time: 2019-02-26
     * user: hepw
     * edit: hepw 2019-02-28 传入的时间格式必须是以“-”来分割年月日，必须传入 “年-月”或者“年-月-日”格式；例如2019-01 or 2019-02-09
     *
     * @param fromTime 开始时间
     * @param toTime   结束时间
     * @return
     * @throws Exception
     */
    public static List<String> getMonthListWithLocalDate(String fromTime, String toTime) {
        if (StringUtils.isEmpty(fromTime) || StringUtils.isEmpty(toTime) || fromTime.length() < 6 || toTime.length() < 6) {
            return Arrays.asList();
        }

        if (fromTime.length() <= 7) {
            fromTime += "-01";
        }

        if (toTime.length() <= 7) {
            toTime += "-01";
        }
        LocalDate start = LocalDate.parse(fromTime);
        LocalDate end = LocalDate.parse(toTime);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM");
        List<String> dateList = new ArrayList<>();
        //dateList.add(fromTime);
        int num = 0;
        while (start.compareTo(end) <= 0) {
            dateList.add(start.format(f));
            start = start.plusMonths(1);
            num++;
            if (num >= 24) {
                break;
            }
        }
        return dateList;
    }


    /**
     * func: 获取两个时间之间的月份列表 (没有时间格式化的错误判断需要提前判断)
     * time: 2019-02-26
     * user: hepw
     * edit: hepw 2019-02-28 传入的时间格式必须是以“-”来分割年月日，必须传入 “年-月”或者“年-月-日”格式；例如2019-01 or 2019-02-09
     *
     * @param fromTime 开始时间
     * @param toTime   结束时间
     * @return
     * @throws Exception
     */
    public static List<String> getMonthListWithLocalDateByUser(String fromTime, String toTime, int userNum) {
        if (StringUtils.isEmpty(fromTime) || StringUtils.isEmpty(toTime) || fromTime.length() < 6 || toTime.length() < 6) {
            return Arrays.asList();
        }

        if (fromTime.length() <= 7) {
            fromTime += "-01";
        }

        if (toTime.length() <= 7) {
            toTime += "-01";
        }
        LocalDate start = LocalDate.parse(fromTime);
        LocalDate end = LocalDate.parse(toTime);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM");
        List<String> dateList = new ArrayList<>();
        //dateList.add(fromTime);
        int num = 0;
        while (start.compareTo(end) <= 0) {
            dateList.add(start.format(f));
            start = start.plusMonths(1);
            num++;
            if (num >= userNum) {
                break;
            }
        }
        return dateList;
    }

    /**
     * 将日期的字符串转换为月份的字符串  2019-03-04 --> 2019-03
     *
     * @param time 时间
     * @return
     */
    public static String getMonthStr(String time) {
        if (StringUtils.isEmpty(time)) {
            return StringUtils.EMPTY;
        }
        String[] times = time.split("-");
        if (time.length() > 2) {
            return String.format("%s-%s", times[0], times[1]);
        } else {
            return time;
        }
    }


    /**
     * 将日期的字符串转换为月份的字符串  2019-03-04 --> 2019-03
     *
     * @param time 时间
     * @return
     */
    public static String getYearthStr(String time) {
        if (StringUtils.isEmpty(time)) {
            return StringUtils.EMPTY;
        }
        String[] times = time.split("-");
        return String.format("%s", times[0]);

    }

    /**
     * 昨日
     *
     * @return
     */
    public static String getYesterday() {
        return LocalDate.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 上个月
     *
     * @return
     */
    public static String getLastMonth() {
        return LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * 获取两个日期的时间间隔
     *
     * @param timeType
     * @param fromTime
     * @param toTime
     * @return
     */
    public static int getTimeGap(String timeType, String fromTime, String toTime) {
        if (StringUtils.isEmpty(timeType) || StringUtils.isEmpty(fromTime) || StringUtils.isEmpty(toTime)) {
            return 0;
        }
        if (timeType.equals(Constants.DAY)) {
            LocalDate start = LocalDate.parse(fromTime);
            LocalDate end = LocalDate.parse(toTime);
            return Period.between(start, end).getDays();
        } else {
            LocalDate start = LocalDate.parse(DateUtils.getMonthStr(fromTime) + "-01");
            LocalDate end = LocalDate.parse(DateUtils.getMonthStr(toTime) + "-01");
            return Period.between(start, end).getMonths();
        }
    }

    /**
     * 本月的最后一天
     *
     * @param monthStr
     * @return
     */
    public static String getMonthLastDay(String monthStr) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat mf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = mf.parse(monthStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        //因为格式化时默认了DATE为本月第一天所以此处为0
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取上一个月
     *
     * @param date 当前日期，格式必须是yyyy-MM-dd
     * @return
     */
    public static String getPerMonth(String date, int scale) {
        try {
            if (date.length() <= 7) {
                date = date + "-01";
            }
            return LocalDate.parse(date).plusMonths(scale).format(DateTimeFormatter.ofPattern("yyyy-MM"));
            // LocalDate.now().plusMonths(-2).plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }


    public static String getMonthFirstDay(String monthStr) {
        return monthStr + "-01";
    }


    /**
     * 时间操作
     *
     * @param str
     * @param n
     * @return
     */
    public static String getNowDate(String str, int n) {
        try {
            SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = f2.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            //把日期往后增加一天.整数往后推,负数往前移动
            calendar.add(Calendar.DATE, n);
            //这个时间就是日期往后推一天的结果
            date = calendar.getTime();
            return f2.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }


    /**
     * 获取时间段
     *
     * @param fromTime
     * @param toTime
     * @param timeType
     * @return
     */
    public static List<String> getTimeList(String fromTime, String toTime, String timeType) {
        List<String> dateList;
        if (Constants.DAY.equals(timeType)) {
            dateList = DateUtils.getDateListWithLocalDate(fromTime, toTime);
        } else {
            dateList = DateUtils.getMonthListWithLocalDate(fromTime, toTime);
        }
        return dateList;
    }

    /**
     * func: 通过日期获取上一个月的月份 传入的时间格式必须是以“-”来分割年月日，必须传入“年-月-日”格式；例如2019-02-09
     * time: 2019-03-04
     * user: hepw
     *
     * @param time 日期
     * @return
     */
    public static String getPreMonthByDay(String time) {
        return getPreMonthByDay(time, "yyyy-MM");
    }

    /**
     * func: 通过日期获取上一个月的月份 传入的时间格式必须是以“-”来分割年月日，必须传入“年-月-日”格式；例如2019-02-09
     * time: 2019-03-04
     * user: hepw
     *
     * @param time 日期
     * @return
     */
    public static String getPreMonthByDay(String time, String fmt) {
        LocalDate localDate = LocalDate.parse(time);
        localDate = localDate.plusMonths(-1);
        DateTimeFormatter f = DateTimeFormatter.ofPattern(fmt);
        return localDate.format(f);
    }


    /**
     * func: 通过日期获取上一个月的月份 传入的时间格式必须是以“-”来分割年月日，必须传入“年-月-日”格式；例如2019-02-09
     * time: 2019-03-04
     * user: hepw
     *
     * @param time 日期
     * @return
     */
    public static String getPreMonthByDay(Date time, String fmt) {
        LocalDate localDate = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.plusMonths(-1);
        DateTimeFormatter f = DateTimeFormatter.ofPattern(fmt);
        return localDate.format(f);
    }

    /**
     * func: 通过日期获取上一个月的月份 传入的时间格式必须是以“-”来分割年月日，必须传入“年-月-日”格式；例如2019-02-09
     * time: 2019-04-03
     * user: hepw
     *
     * @param time 日期
     * @param num  前推月份
     * @return
     */
    public static String getPreMonthByDay(String time, int num) {
        LocalDate localDate = LocalDate.parse(time);
        localDate = localDate.plusMonths(-num);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM");
        return localDate.format(f);
    }


    /**
     * func: 根据参数获取前一个月的时间
     * 1. 时间类型是month，返回从本月的首日至末日的日期列表 example: [dataTime: 2018-01 --> 2018-01-01,2018-01-02 .... 2018-01-31]
     * 2. 时间类型是日的话，从time往前推30天的日期列表 example:[dateTime: 2017-03-01 -->  2017-01-31,2017-01-01 ... 2017-03-01]
     * time: 2019-03-04
     * user: hepw
     *
     * @param time     时间
     * @param timeType 时间类型
     * @param timeType 时间类型
     * @return
     */
    public static List<String> getPerDays(String time, String timeType) {
        List<String> times = new ArrayList<>();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (Constants.DAY.equals(timeType)) {
            LocalDate localDate = LocalDate.parse(time);
            for (int i = 29; i >= 0; i--) {
                times.add(localDate.plusDays(-i).format(f));
            }
        } else {
            LocalDate localDate = LocalDate.parse(time + "-01");
            LocalDate nextMonth = localDate.plusMonths(1);
            while (localDate.compareTo(nextMonth) < 0) {
                times.add(localDate.format(f));
                localDate = localDate.plusDays(1);
            }
        }
        return times;
    }

    /**
     * func: 根据参数获取前一个月的时间
     * 1. 时间类型是month，返回从本月前推12月的月份列表列表 example: [dataTime: 2018-01 --> 2018-01,2017-12 .... 2017-02]
     * 2. 时间类型是日的话，从time往前推30天的日期列表 example:[dateTime: 2017-03-01 -->  2017-01-31,2017-01-01 ... 2017-03-01]
     * time: 2019-09-04
     * user: hepw
     *
     * @param time     时间
     * @param timeType 时间类型
     * @param timeType 时间类型
     * @return
     */
    public static List<String> getPerDate(String time, String timeType) {
        List<String> times = new ArrayList<>();
        if (Constants.DAY.equals(timeType)) {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(time);
            for (int i = 29; i >= 0; i--) {
                times.add(localDate.plusDays(-i).format(f));
            }
        } else {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM");
            LocalDate localDate = LocalDate.parse(time + "-01");
            for (int i = 11; i >= 0; i--) {
                times.add(localDate.plusMonths(-i).format(f));
            }
        }
        return times;
    }

    /**
     * func: 通过日期获取前num天的日期 传入的时间格式必须是以“-”来分割年月日，必须传入“年-月-日”格式；例如2019-02-09
     * time: 2019-04-03
     * user: hepw
     *
     * @param time 日期
     * @param num  前推月份
     * @return
     */
    public static String getPreDay(String time, int num) {
        LocalDate localDate = LocalDate.parse(time);
        localDate = localDate.plusDays(-num);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(f);
    }


    /**
     * func: 获取当前日期
     * time: 2019-04-03
     * user: hepw
     *
     * @return
     */
    public static String getToDay() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.now().format(f);
    }

    /**
     * func: 获取当前日期
     * time: 2019-04-03
     * user: hepw
     *
     * @return
     */
    public static String getToDayTime() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return LocalDateTime.now().format(f);
    }

    /**
     * func: 获取当前日期
     * time: 2019-04-03
     * user: hepw
     *
     * @return
     */
    public static String getToDay(int days) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.now().plusDays(days).format(f);
    }

    /**
     * func: 获取当前日期
     * time: 2019-04-03
     * user: hepw
     *
     * @return
     */
    public static String getToDay(String pattern) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.now().format(f);
    }


    /**
     * func: 获取当前日期
     * time: 2019-04-03
     * user: hepw
     *
     * @return
     */
    public static String getToDay(String pattern, int day) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.now().plusDays(day).format(f);
    }

    public static String getDayStr(String date, int day) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date).plusDays(day).format(f);
    }


    /**
     * 时间比较 使用LocalDate
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(String date1, String date2) {
        return LocalDate.parse(date1).compareTo(LocalDate.parse(date2));
    }

    public static int compareMonth(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        LocalDate dt1 = LocalDate.parse(date1);
        LocalDate dt2 = LocalDate.parse(date2);
        return dt1.compareTo(dt2);
    }

    public static boolean compareMonthV2(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            return dt1.compareTo(dt2) <= 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取当前月份数字
     *
     * @param date
     * @return
     */
    public static Integer getMonthNum(String date) {
        return LocalDate.parse(getMonthStr(date) + "-01").getMonthValue();
    }

    /**
     * ppt 折线图的 时间调整
     *
     * @param dateStr 必须是日期
     */
    public static String getDiffDay(String dateStr) throws ParseException {
        if (dateStr.length() <= 7) {
            dateStr = dateStr + "-01";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("1900-01-00");
        long start = org.apache.commons.lang3.time.DateUtils.addDays(date, -1).getTime();
        date = sdf.parse(dateStr);
        long end = date.getTime();
        long diff = (end - start) / (24 * 60 * 60 * 1000);
        return String.valueOf(diff);
    }

    /**
     * @param dateStr
     * @return java.lang.String
     * @Description: <br>
     * 〈new SimpleDateFormat("yyyy年MM月dd日");〉
     * @date 2020/4/18 chenxh
     */
    public static String formatDate(String dateStr, String sourceFormatStr, String destFormatStr) throws ParseException {
//        if (dateStr.length() <= 7) {
//            dateStr = dateStr + "-01";
//        }
        // yyyy-MM
        SimpleDateFormat sdf = new SimpleDateFormat(sourceFormatStr);
        Date date = sdf.parse(dateStr);
        //yyyy年MM月
        SimpleDateFormat df = new SimpleDateFormat(destFormatStr);
        String format = df.format(date);
        return format;
    }

    public static String formatDateForMonth(String dateStr) throws ParseException {
        return formatDate(dateStr, "yyyy-MM", "yyyy年MM月");

    }

    /**
     * func: 通过日期获取上一个日期的年份 传入的时间格式必须是以“-”来分割年月日，必须传入“年-月-日”格式；例如2019-02-09
     * time: 2019-03-04
     * user: hepw
     *
     * @param time 日期
     * @return
     */
    public static String getPreYearByDay(String time, String fmt, int num) {
        LocalDate localDate = LocalDate.parse(time);
        localDate = localDate.plusYears(num);
        DateTimeFormatter f = DateTimeFormatter.ofPattern(fmt);
        return localDate.format(f);
    }


    /**
     * @param fromTime 开始时间
     * @param toTime   结束时间
     * @return
     * @throws Exception
     */
    public static List<String> getYearListWithLocalDate(String fromTime, String toTime) {
        if (StringUtils.isEmpty(fromTime) || StringUtils.isEmpty(toTime) || fromTime.length() < 6 || toTime.length() < 6) {
            return Arrays.asList();
        }

        if (fromTime.length() <= 7) {
            fromTime += "-01";
        }

        if (toTime.length() <= 7) {
            toTime += "-01";
        }
        LocalDate start = LocalDate.parse(fromTime);
        LocalDate end = LocalDate.parse(toTime);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy");
        List<String> dateList = new ArrayList<>();
        //dateList.add(fromTime);
        int num = 0;
        while (start.compareTo(end) <= 0) {
            dateList.add(start.format(f));
            start = start.plusYears(1);
            num++;
            if (num >= 12) {
                break;
            }
        }
        return dateList;
    }


    /**
     * func: 通过日期获取上一个日期的年份 传入的时间格式必须是以“-”来分割年月日，必须传入“年-月-日”格式；例如2019-02-09
     * time: 2019-03-04
     * user: hepw
     *
     * @return
     */
    public static List<String> getBetweenByYear(String fromTime, String toTime) {
        return getYearListWithLocalDate(fromTime, toTime);

    }


    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return sdf.format(date);

    }


    public static long getDateLongNum(String oneDate, String fmt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            Date date1 = sdf.parse(oneDate);
            long longDate1 = date1.getTime();
            return longDate1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String transferLongToDate(String dateFormat, Long dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(dateStr);
        return sdf.format(date);
    }

    /**
     * 获取精确到秒的时间字符串，用作文件命名
     *
     * @return
     */
    public static String getTimeStr() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(f);
    }


    /**
     * 获取上一个月第一天
     *
     * @return
     */
    public static String getPerMonthFirstDay() {
        try {
            return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1).plusMonths(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // LocalDate.now().plusMonths(-2).plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception ex) {
        }
        return "";
    }

    /**
     * 获取上一个月最后一天
     *
     * @return
     */
    public static String getPerMonthLastDay() {
        try {
            return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1).plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception ex) {
            //
        }
        return "";
    }

    /**
     * 判断第一个时间是否在后面两个的时间周期内
     *
     * @param dateStr
     * @param fromDateStr
     * @param endDateStr
     * @return
     */
    public static boolean checkDateContains(String dateStr, String fromDateStr, String endDateStr) {
        LocalDate fromDate = LocalDate.parse(fromDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        if (fromDate.compareTo(endDate) > 0) {
            return false;
        }
        LocalDate date = LocalDate.parse(dateStr);
        if (date.compareTo(fromDate) >= 0 && date.compareTo(endDate) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws ParseException {
        //System.out.println(formatDateForMonth("2020-09"));
        System.out.println(getPreYearByDay("2016-01-01", "yyyy-MM", -4));
        List ls = getBetweenByYear("2016-01", "2020-05");
        System.out.println("=========");
        System.out.println(getCurrentYear());
    }
}
