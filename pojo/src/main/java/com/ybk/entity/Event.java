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
    private Long eventId; // 主键 自增
    private String name; // 活动名称 非空
    private String introduction; // 活动介绍 可为空
    private String stadium; // 体育馆 非空
    private Integer requiredAreaCount; // 需要的最少场地数 默认为1
    private Integer requiredRefereeCount; // 需要的最少裁判数 默认为1
    private LocalDate beginTime; // 活动开始日期 非空
    private LocalDate endTime; // 活动结束日期 非空
    private String matchType; // 赛事类型 传统比赛 趣味赛 非空
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
