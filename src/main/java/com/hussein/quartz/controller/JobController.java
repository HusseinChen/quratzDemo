package com.hussein.quartz.controller;

import com.hussein.quartz.job.quartz.QuartzScheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: JOB调度API
 * @author: Hussein
 * @E-mail: 43138199@qq.com
 * @date: 2019/2/9
 * @time: 22:25
 * 2019/2/9 22:25 Hussein create
 * @version: 1.0.0
 */
@RestController
@RequestMapping(path = "/job")
public class JobController {

    @Autowired
    private QuartzScheduler quartzScheduler;

    @GetMapping("/startAll")
    public void startQuartzJob() {
        try {
            quartzScheduler.startJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/info")
    public String getQuartzJob(@RequestParam String name, @RequestParam String group) {
        String info = null;
        try {
            info = quartzScheduler.getJobInfo(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return info;
    }

    @GetMapping("/modify")
    public boolean modifyQuartzJob(@RequestParam String name, @RequestParam String group, @RequestParam String cron) {
        boolean flag = true;
        try {
            flag = quartzScheduler.modifyJob(name, group, cron);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @GetMapping("/pauseAll")
    public void pauseAllJob() {
        try {
            quartzScheduler.pauseAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/pause")
    public void pauseJob(@RequestParam String name, @RequestParam String group) {
        try {
            quartzScheduler.pauseJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/delete")
    public void deleteJob(@RequestParam String name, @RequestParam String group) {
        try {
            quartzScheduler.deleteJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
