package com.example.jobscheduler.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName(value = "sys_job", autoResultMap = true)
public class SysJob {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    private String name;

    /**
     * 时间表达式
     */
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    private String cron;

    /**
     * 类全路径
     */
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    private String classPath;

    /**
     * 传递map参数
     */
    private String dataMap;

    /**
     * 状态:1启用 0停用
     */
    private Integer status;

    /**
     * 任务功能描述
     */
    @TableField("`describe`")
    private String describe;

}
