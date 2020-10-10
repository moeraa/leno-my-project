package com.org.moer.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author chenxh
 * @date 2020/9/10  5:34 下午
 * @func 定义Job 。本例子通过JobDataMap 返回需要执行的任务。 运行相应的方法
 */
public class RunMeJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        RunMeTask runMeTask = (RunMeTask) jobDataMap.get("runMeTask");
        runMeTask.printMe();
    }
}
