package com.example.jobscheduler.task;

import com.example.jobscheduler.task.base.BaseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Hex
 * @date 10/6/2022 下午4:03
 */
@Slf4j
@Component
public class JobOneHandler implements BaseHandler {
    @Override
    public void execute(String dataMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OneData oneData = objectMapper.readValue(dataMap, OneData.class);
            log.info("JobOneHandler执行,{}", oneData.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @NoArgsConstructor
    static class OneData{

        private String message;
    }
}
