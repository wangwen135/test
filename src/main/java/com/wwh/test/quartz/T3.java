package com.wwh.test.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class T3 implements Job {

    private String name;

    private SingleTool tool;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SingleTool getTool() {
        return tool;
    }

    public void setTool(SingleTool tool) {
        this.tool = tool;
    }

    public static void main(String[] args) throws SchedulerException {

        SingleTool st = new SingleTool("测试单例");

        DirectSchedulerFactory schedulerFactory = DirectSchedulerFactory.getInstance();

        schedulerFactory.createVolatileScheduler(3);

        Scheduler scheduler = schedulerFactory.getScheduler();

        // Grab the Scheduler instance from the Factory
        // Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // and start it off
        scheduler.start();

        // .usingJobData("xx", 1l)

        // define the job and tie it to our MyJob class
        JobDetail job = JobBuilder.newJob(T3.class).withIdentity("job1").usingJobData("name", "xxxx112233").build();

        // 这里需要设置任务的ID

        job.getJobDataMap().put("tool", st);

        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1").startNow().withSchedule(CronScheduleBuilder.cronSchedule("*/3 * * * * ? *"))
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);

        // 业务上能修改任务调度时间
        // 不管怎样，先删除任务吧

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JobKey jobKey = new JobKey("job1");
        scheduler.deleteJob(jobKey);// 删除job和关联的Trigger

        System.out.println("删除job和关联的Trigger");

        System.out.println("重新添加");

        JobDetail job2 = JobBuilder.newJob(T3.class).withIdentity("job2").usingJobData("name", "xxxx112233").build();
        job2.getJobDataMap().put("tool", st);
        Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2").startNow().withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ? *"))
                .build();
        scheduler.scheduleJob(job2, trigger2);

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("停止");

        scheduler.shutdown(true);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("Hello World!  MyJob is executing.");
        System.out.println("name = " + name);
        System.out.println(tool.getP());

    }

}
