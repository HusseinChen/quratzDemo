package com.hussein.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @Description: 测试JOB A
 * @author: Hussein
 * @E-mail: 43138199@qq.com
 * @date: 2019/2/9
 * @time: 19:15
 * 2019/2/9 19:15 Hussein create
 */
public class TestJobA implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("测试JobA开始" + new Date());
        System.out.println("测试JobA结束");
    }
}
