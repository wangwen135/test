package com.wwh.quartz;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class T2 implements Job {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws SchedulerException {

        // Grab the Scheduler instance from the Factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // and start it off
        scheduler.start();

        // .usingJobData("xx", 1l)

        // define the job and tie it to our MyJob class
        JobDetail job = JobBuilder.newJob(T2.class).withIdentity("job1", "group1").usingJobData("name", "xxxx112233").build();

        String cronExpression = "*/1 * * * * ? *";
        CronExpression ce = null;
        try {
            ce = new CronExpression(cronExpression);
        } catch (Exception e) {
            System.out.println("表达式错误");
        }

        // Trigger the job to run now, and then repeat every 40 seconds

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow().withSchedule(CronScheduleBuilder.cronSchedule(ce)).build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        scheduler.shutdown(true);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("Hello World!  MyJob is executing.");
        System.out.println("name = " + name);

    }

}
