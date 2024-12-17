package com.ybk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MatchBSection: 趣味比赛的小节详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchBSection {
    private Integer sectionNumber; // 小节编号
    private Long teamAScore; // 小节得分 - Team A
    private Long teamBScore; // 小节得分 - Team B
    private Long nextTeamAScoreToWin; // 本节需要达到的分数
}
