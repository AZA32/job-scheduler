package com.example.jobscheduler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.jobscheduler.model.entity.SysJob;


public interface ISysJobService extends IService<SysJob> {
    /**
     * @param job
     * @return
     */
    String addJob(SysJob job);

    /**
     * @param jobId
     * @return
     */
    String startJob(Integer jobId);

    /**
     * @param job
     * @return
     */
    String updateJob(SysJob job);

    /**
     * @param jobId
     * @return
     */
    String stopJob(Integer jobId);

    String execute(Integer jobId) throws Exception;
}
