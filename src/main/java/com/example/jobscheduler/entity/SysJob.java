package com.example.jobscheduler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value ="sys_job")
public class SysJob implements Serializable{

        /**
         * ID
         */
        @TableId(type = IdType.AUTO)
        private Integer id;

        /**
         * 任务名称
         */
        private String name;

        /**
         * 时间表达式
         */
        private String cron;

        /**
         * 类全路径
         */
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
        private String describe;

        @TableField(exist = false)
        private static final long serialVersionUID = 1L;

}
