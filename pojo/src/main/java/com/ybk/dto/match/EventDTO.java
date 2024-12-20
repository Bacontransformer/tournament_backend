package com.ybk.dto.match;

import lombok.Data;

import java.time.LocalDate;

/**
 * admin编辑Event的数据传输对象
 */
@Data
public class EventDTO {
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
}
