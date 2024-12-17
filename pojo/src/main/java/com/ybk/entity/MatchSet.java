package com.ybk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MatchSet: 每一局的比分
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchSet {
    private Integer setNumber; // 第几局
    private Long teamAScore;
    private Long teamBScore;
    private Long winnerTeamId; // 当前局的获胜队
}