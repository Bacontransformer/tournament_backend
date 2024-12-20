package com.ybk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * MatchSet: 每一局的比分
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchSet {
    private Long teamAScore; // teamA当局总得分
    private Long teamBScore; // teamB当局总得分
    private List<Boolean> teamScoreList; // 得分情况 0代表teamA得分，1代表teamB得分
    private Long winnerTeamId; // 当前局的获胜队
}