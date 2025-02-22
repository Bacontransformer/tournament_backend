package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchBPlayerDTO {
    private Long matchBId; // 比赛编号
    private Long teamId;
    private Long teamAPlayerId1; // teamA的1号选手
    private Long teamAPlayerId2; // teamA的2号选手
    private Long teamAPlayerId3; // teamA的3号选手
    private Long teamAPlayerId4; // teamA的4号选手
    private Long teamBPlayerId1; // teamB的1号选手
    private Long teamBPlayerId2; // teamB的2号选手
    private Long teamBPlayerId3; // teamB的3号选手
    private Long teamBPlayerId4; // teamB的4号选手
    private Long teamASubstitutePlayerId1; // teamA替补选手1
    private Long teamASubstitutePlayerId2; // teamA替补选手2
    private Long teamASubstitutePlayerId3; // teamA替补选手3
    private Long teamASubstitutePlayerId4; // teamA替补选手4
    private Long teamBSubstitutePlayerId1; // teamB替补选手1
    private Long teamBSubstitutePlayerId2; // teamB替补选手2
    private Long teamBSubstitutePlayerId3; // teamB替补选手3
    private Long teamBSubstitutePlayerId4; // teamB替补选手4
}
