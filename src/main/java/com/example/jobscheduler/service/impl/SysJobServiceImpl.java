package com.example.jobscheduler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jobscheduler.mapper.SysJobMapper;
import com.example.jobscheduler.model.entity.SysJob;
import com.example.jobscheduler.service.ISysJobService;
import com.example.jobscheduler.thread.DispatcherJobThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob>
        implements ISysJobService, CommandLineRunner {
    private final SysJobMapper sysJobMapper;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final Map<Integer, ScheduledFuture<?>> scheduleMap = new ConcurrentHashMap<>();


    public SysJobServiceImpl(ThreadPoolTaskScheduler threadPoolTaskScheduler, SysJobMapper sysJobMapper) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.sysJobMapper = sysJobMapper;
    }

    @Override
    public String addJob(SysJob job) {
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", job.getName());
        SysJob sysJob = sysJobMapper.selectOne(queryWrapper);
        if (sysJob != null) {
            return null;
        }
        int insert = sysJobMapper.insert(job);
        if (insert > 0) {
            if (job.getStatus() == 1) {
                ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(new DispatcherJobThread(job), new CronTrigger(job.getCron()));
                scheduleMap.put(job.getId(), schedule);
            }
            return "添加成功";
        }
        return "失败";
    }

    @Override
    public String startJob(Integer jobId) {
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", jobId);
        SysJob sysJob = sysJobMapper.selectOne(queryWrapper);
        if (sysJob.getStatus() == 1) {
            return "任务已启用，无需重复启动";
        }
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("status", 1);
        updateWrapper.eq("id", sysJob.getId());
        int update = sysJobMapper.update(sysJob, updateWrapper);
        if (update > 0) {
            ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(new DispatcherJobThread(sysJob), new CronTrigger(sysJob.getCron()));
            scheduleMap.put(sysJob.getId(), schedule);
            return "成功";
        }
        return "失败";
    }

    @Override
    public String updateJob(SysJob job) {
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", job.getId());
        SysJob sysJob = sysJobMapper.selectOne(queryWrapper);
        BeanUtils.copyProperties(sysJob, job);
        if (sysJobMapper.updateById(job) > 0) {
            if (scheduleMap.containsKey(job.getId())) {
                ScheduledFuture<?> scheduledFuture = scheduleMap.get(job.getId());
                scheduledFuture.cancel(true);
            }
            if (job.getStatus() == 1) {
                ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(new DispatcherJobThread(job), new CronTrigger(job.getCron()));
                scheduleMap.put(sysJob.getId(), schedule);
            }
            return "成功";
        }
        return "失败";
    }

    @Override
    public String stopJob(Integer jobId) {
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", jobId);
        SysJob sysJob = sysJobMapper.selectOne(queryWrapper);
        if (sysJob.getStatus() == 0) {
            return "任务已停用，无需重复关闭";
        }
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("status", 0);
        updateWrapper.eq("id", sysJob.getId());
        int update = sysJobMapper.update(sysJob, updateWrapper);
        if (update > 0) {
            if (scheduleMap.containsKey(jobId)) {
                ScheduledFuture<?> scheduledFuture = scheduleMap.get(jobId);
                scheduledFuture.cancel(true);
                scheduleMap.remove(jobId);
            }
            return "成功";
        }
        return "失败";
    }

    @Override
    public String execute(Integer jobId) throws Exception {
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", jobId);
        SysJob sysJob = sysJobMapper.selectOne(queryWrapper);
        if (sysJob == null) {
            return "当前任务不存在";
        }
        ScheduledFuture<?> scheduledFuture = scheduleMap.get(jobId);
        Future<?> future = threadPoolTaskScheduler.submit(new DispatcherJobThread(sysJob));
        Object o = future.get();
        System.out.println(o);
        return "执行成功";
    }

    @Override
    public void run(String... args) throws Exception {
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        sysJobMapper.selectList(queryWrapper).forEach(job -> {
            ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(new DispatcherJobThread(job), new CronTrigger(job.getCron()));
            scheduleMap.put(job.getId(), schedule);
        });
    }
}
