package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchBPlayerDTO {
    private Integer matchBId; // 比赛编号
    private Integer teamAPlayerId1; // teamA的1号选手
    private Integer teamAPlayerId2; // teamA的2号选手
    private Integer teamAPlayerId3; // teamA的3号选手
    private Integer teamAPlayerId4; // teamA的4号选手
    private Integer teamBPlayerId1; // teamB的1号选手
    private Integer teamBPlayerId2; // teamB的2号选手
    private Integer teamBPlayerId3; // teamB的3号选手
    private Integer teamBPlayerId4; // teamB的4号选手
    private Integer teamASubstitutePlayerId1; // teamA替补选手1
    private Integer teamASubstitutePlayerId2; // teamA替补选手2
    private Integer teamASubstitutePlayerId3; // teamA替补选手3
    private Integer teamASubstitutePlayerId4; // teamA替补选手4
    private Integer teamBSubstitutePlayerId1; // teamB替补选手1
    private Integer teamBSubstitutePlayerId2; // teamB替补选手2
    private Integer teamBSubstitutePlayerId3; // teamB替补选手3
    private Integer teamBSubstitutePlayerId4; // teamB替补选手4
}
