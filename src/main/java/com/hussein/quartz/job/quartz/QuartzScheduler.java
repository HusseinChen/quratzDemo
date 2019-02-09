package com.hussein.quartz.job.quartz;

import com.hussein.quartz.job.TestJobA;
import com.hussein.quartz.job.TestJobB;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @Description: 任务调度处理
 * @author: Hussein
 * @E-mail: 43138199@qq.com
 * @date: 2019/2/9
 * @time: 19:20
 * 2019/2/9 19:20 Hussein create
 * @version:
 */
@Configuration
public class QuartzScheduler {

    @Autowired
    private Scheduler scheduler;

    /**
     * @Description: 将任务注入scheduler调度容器，启动调度容器中所有任务
     * @author : Hussein
     * @E-mail：43138199@qq.com
     * @Date: 2019/2/9 20:13
     * @Return: a
    */
    public void startJob() throws SchedulerException {
        startJob1(scheduler);
        startJob2(scheduler);
        scheduler.start();
    }

    /**
     * @Description: 获取指定任务的详情
     * @author : Hussein
     * @E-mail：43138199@qq.com
     * @Date: 2019/2/9 20:19
     * @Return: a
    */
    public String getJobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time: %s, state: %s", cronTrigger.getCronExpression(), scheduler.getTriggerState(triggerKey));
    }

    /**
     * @Description: 修改JOB触发时间
     * @author : Hussein
     * @E-mail：43138199@qq.com
     * @Date: 2019/2/9 20:29
     * @Return: true：修改成功，false：修改失败
    */
    public boolean modifyJob(String name, String group, String cron) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldCron = cronTrigger.getCronExpression();
        if (!oldCron.equalsIgnoreCase(cron)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, newTrigger);
        }
        return date != null;
    }

    /**
     * @Description: 暂停所有JOB
     * @author : Hussein
     * @E-mail：43138199@qq.com
     * @Date: 2019/2/9 21:26
     * @Return: a
    */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * @Description: 暂停指定JOB
     * @author : Hussein
     * @E-mail：43138199@qq.com
     * @Date: 2019/2/9 21:39
     * @Return: a
    */
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            scheduler.pauseJob(jobKey);
        }
    }


    /**
     * @Description: 恢复所有JOB
     * @author : Hussein
     * @E-mail：43138199@qq.com
     * @Date: 2019/2/9 21:44
     * @Return: a
    */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            scheduler.resumeJob(jobKey);
        }
    }

    /**
     * @Description: 删除指定JOB
     * @author : Hussein
     * @E-mail：43138199@qq.com
     * @Date: 2019/2/9 21:47
     * @Return: a
    */
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail != null) {
            scheduler.deleteJob(jobKey);
        }
    }

    private void startJob1(Scheduler scheduler) throws SchedulerException {
       JobDetail jobDetail = JobBuilder.newJob(TestJobA.class).withIdentity("job1", "hussein").build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job1", "hussein")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    private void startJob2(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(TestJobB.class).withIdentity("job2", "hussein").build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("3/7 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job2", "hussein")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}
