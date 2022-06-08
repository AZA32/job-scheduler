package com.example.jobscheduler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jobscheduler.entity.SysJob;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hex
 * @date 8/6/2022 下午8:51
 */
@Repository
public interface SysJobMapper extends BaseMapper<SysJob> {

}
