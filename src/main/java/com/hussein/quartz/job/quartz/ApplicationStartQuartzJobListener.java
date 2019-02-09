package com.hussein.quartz.job.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @Description: 监听spring容器加载完毕事件，初始化启动JOB
 * @author: Hussein
 * @E-mail: 43138199@qq.com
 * @date: 2019/2/9
 * @time: 22:16
 * 2019/2/9 22:16 Hussein create
 * @version:
 */
@Configuration
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private QuartzScheduler quartzScheduler;

    /**
     * @Description: 初始化启动JOB
     * @author : Hussein
     * @E-mail：43138199@qq.com
     * @Date: 2019/2/9 22:21
     * @Return: a
    */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            quartzScheduler.startJob();
            System.out.println("启动JOB");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }
}
