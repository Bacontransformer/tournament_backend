package com.ybk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchBDTO {
    private Long eventId; // 所属活动ID
    private String matchName; // 比赛名称
    private Integer sectionScore; // 每个小节获胜分数
    private Integer totalSections; // 总小节数
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选）
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选）
    private Long teamAId; // 队伍A ID
    private Long teamBId; // 队伍B ID
    private String beginTime; // 比赛开始时间（格式化字符串）
}
