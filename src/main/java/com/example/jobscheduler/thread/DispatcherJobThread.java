package com.example.jobscheduler.thread;

import com.example.jobscheduler.model.entity.SysJob;
import com.example.jobscheduler.task.base.BaseHandler;
import com.example.jobscheduler.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DispatcherJobThread extends Thread {

    private final SysJob job;

    public DispatcherJobThread(SysJob job) {
        this.job = job;
    }

    @Override
    public void run() {
        Class<BaseHandler> aClass = null;
        try {
            aClass = (Class<BaseHandler>) Class.forName(job.getClassPath());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        BaseHandler baseJob = BeanUtil.getBean(aClass);
        baseJob.execute(job.getDataMap());
    }

}
