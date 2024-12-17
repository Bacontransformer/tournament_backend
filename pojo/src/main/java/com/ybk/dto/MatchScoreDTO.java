package com.ybk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchScoreDTO {
    private Long matchId; // 比赛ID
    private Integer setNumber; // 当前局数
    private Integer teamAScore; // 队伍A的分数
    private Integer teamBScore; // 队伍B的分数
    private Long scoringTeamId; // 当前得分的队伍ID
    private String refereeName; // 裁判姓名
    private String updateTime; // 更新时间（格式化字符串）
}
