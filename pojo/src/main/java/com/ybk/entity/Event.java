package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 赛事
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("event")
public class Event {
    @TableId("event_id")
    private Long eventId;
    private String name;
    private String introduction;
    // 比赛场地
    private String place;
    private Integer required_area_count;
    private Integer required_referee_count;
    // 比赛开始日期
    private LocalDate begin_time;
    // 比赛截止日期
    private LocalDate end_time;
    // 比赛类型
    private String match_type;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
