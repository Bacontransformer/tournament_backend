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
    // 活动名称
    private String name;
    // 活动描述
    private String introduction;
    // 活动体育馆
    private String stadium;
    // 需要的最大场地数
    private Integer requiredAreaCount;
    // 需要的最大裁判数
    private Integer requiredRefereeCount;
    // 活动开始日期
    private LocalDate beginTime;
    // 活动结束日期
    private LocalDate endTime;
    // 赛事类型 混团 1234
    private String matchType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
