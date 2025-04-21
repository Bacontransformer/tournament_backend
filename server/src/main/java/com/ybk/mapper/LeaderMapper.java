package com.ybk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybk.entity.Leader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LeaderMapper extends BaseMapper<Leader> {
}
