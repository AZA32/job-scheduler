package com.example.jobscheduler.config;

import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class JobSchedulerConfig {

    /**
     * @param builder
     * @return
     * 底层调用ThreadPoolTaskScheduler.createExecutor()创建JDK ScheduledThreadPoolExecutor线程池
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(TaskSchedulerBuilder builder){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = builder.build();
        threadPoolTaskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        //将取消后的任务从队列中清除
        threadPoolTaskScheduler.setRemoveOnCancelPolicy(true);
        return threadPoolTaskScheduler;
    }

}
