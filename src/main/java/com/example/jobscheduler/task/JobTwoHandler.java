package com.example.jobscheduler.task;

import com.example.jobscheduler.task.base.BaseHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Hex
 * @date 13/6/2022 上午10:03
 */
@Slf4j
public class JobTwoHandler implements BaseHandler {
    @Override
    public void execute(String dataMap) {
        log.info("执行JobTwoHandler,参数:{}", dataMap);
    }
}
