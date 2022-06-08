package com.example.jobscheduler.monitor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Hex
 * @date 8/6/2022 下午10:47
 */
@Component
@ConditionalOnBean(name = "threadPoolTaskScheduler")
@Endpoint(id = "thread-pool")
public class ThreadPoolMonitor implements ApplicationContextAware {

    private ScheduledThreadPoolExecutor scheduledExecutor;

    @ReadOperation
    public Map<String,Object> threadPoolMetric() {
        Map<String,Object> metricMap=new HashMap<>();
        metricMap.put("初始线程数",scheduledExecutor.getPoolSize());
        metricMap.put("核心线程数",scheduledExecutor.getCorePoolSize());
        metricMap.put("执行的任务数量",scheduledExecutor.getActiveCount());
        metricMap.put("已完成任务数量",scheduledExecutor.getCompletedTaskCount());
        metricMap.put("任务总数",scheduledExecutor.getTaskCount());
        metricMap.put("队列里缓存的任务数量",scheduledExecutor.getQueue().size());
        metricMap.put("池中存在的最大线程数",scheduledExecutor.getLargestPoolSize());
        metricMap.put("最大允许的线程数",scheduledExecutor.getMaximumPoolSize());
        metricMap.put("线程空闲时间",scheduledExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS));
        return metricMap;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.scheduledExecutor = applicationContext.getBean(ThreadPoolTaskScheduler.class).getScheduledThreadPoolExecutor();
    }
}
