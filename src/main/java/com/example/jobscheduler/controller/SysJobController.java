package com.example.jobscheduler.controller;

import com.example.jobscheduler.model.entity.SysJob;
import com.example.jobscheduler.service.ISysJobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class SysJobController {
    private final ISysJobService iSysJobService;

    public SysJobController(ISysJobService iSysJobService) {
        this.iSysJobService = iSysJobService;
    }

    @GetMapping("/start/{jobId}")
    public String start(@PathVariable Integer jobId) {
        return iSysJobService.startJob(jobId);
    }

    @GetMapping("/execute/{jobId}")
    public String execute(@PathVariable Integer jobId) throws Exception {
        return iSysJobService.execute(jobId);
    }

    @PostMapping("/addJob")
    public String addJob(@RequestBody SysJob job) {
        return iSysJobService.addJob(job);
    }

    @GetMapping("/getJobs")
    public List<SysJob> getJobs() {
        return iSysJobService.list();
    }

    @PostMapping("/updateJob")
    public String updateJob(@RequestBody SysJob job){
        return iSysJobService.updateJob(job);
    }

    @GetMapping("/stop/{jobId}")
    public String stopJob(@PathVariable Integer jobId) {
        return iSysJobService.stopJob(jobId);
    }
}
