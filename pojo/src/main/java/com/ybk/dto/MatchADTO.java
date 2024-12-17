package com.ybk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchADTO {
    private Long eventId; // 所属活动ID
    private String matchName; // 比赛名称
    private String modes; // 比赛模式（如男单、女双等）
    private Integer maxPlayerParticipation; // 单个选手最大参赛次数
    private Integer roundCount; // 比赛局数
    private Integer winScore; // 单局获胜分数
    private Long teamAId; // 队伍A ID
    private Long teamBId; // 队伍B ID
    private String beginTime; // 比赛开始时间（格式化字符串）
}
