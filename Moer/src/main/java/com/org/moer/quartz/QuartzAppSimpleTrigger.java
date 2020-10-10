package com.org.moer.quartz;

import org.quartz.*;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.text.ParseException;
import java.util.Date;

/**
 * @author chenxh
 * @date 2020/9/10  5:40 下午
 * @func
 */
public class QuartzAppSimpleTrigger {
    public static void main(String[] args) throws ParseException, SchedulerException {
        // 定义jobdetail 将 RunMeTask 添加到 JobDataMap中
        RunMeTask task = new RunMeTask();
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName("runMeJob");
        jobDetail.setJobClass(RunMeJob.class);

        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("runMeTask", task);
        //设置触发器
        /*触发器有两种"
         * 一种是: simpleTrigger 允许设置开始时间、结束时间、重复间隔等
         * 第二种是：CronTrigger 使用unix cron expression 表达式指定Job运行的时机
         * */
        SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
        simpleTrigger.setName("Run me simple trigger Testing");
        simpleTrigger.setEndTime(new Date(System.currentTimeMillis()+1000));
        simpleTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        simpleTrigger.setRepeatInterval(30000);



        CronTriggerImpl cronTrigger=new CronTriggerImpl();
        cronTrigger.setName("Run me Cron trigger Testing");
        cronTrigger.setCronExpression("0/30 * * * * ?");

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//        Scheduler scheduler1 = new DirectSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,cronTrigger);


    }
}
