package com.ybk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderMatchBVO {
    private Boolean isTeamA; // 是否为teamA
    private Integer matchBId; // 主键
    private Integer eventId; // 逻辑关联活动id
    private Integer status; // 比赛状态 默认为0
    private Integer sectionScore; // 每小节的获胜分数 x，一共有四个小节 非空
    private Integer currentSection; // 当前小节 默认为1
    private Integer teamAId; // teamA的编号
    private Integer teamBId; // teamB的编号
    private Integer winnerTeamId; // 获胜队伍编号
    private Integer refereeId; // 裁判的id 非空
    private Integer substituteRefereeId; // 替补裁判的id
    private String refereeName; // 裁判的名字
    private String substituteRefereeName; // 替补裁判的名字
    private String teamADepartment; // teamA的部门
    private String teamBDepartment; // teamB的部门
    private String winnerTeamDepartment; // 胜者队伍的部门
    private Integer venueNumber; // 场地编号
    private Integer teamAPlayerId1; // teamA的1号选手
    private String teamAPlayerName1;
    private Integer teamAPlayerId2; // teamA的2号选手
    private String teamAPlayerName2;
    private Integer teamAPlayerId3; // teamA的3号选手
    private String teamAPlayerName3;
    private Integer teamAPlayerId4; // teamA的4号选手
    private String teamAPlayerName4;
    private Integer teamBPlayerId1; // teamB的1号选手
    private String teamBPlayerName1;
    private Integer teamBPlayerId2; // teamB的2号选手
    private String teamBPlayerName2;
    private Integer teamBPlayerId3; // teamB的3号选手
    private String teamBPlayerName3;
    private Integer teamBPlayerId4; // teamB的4号选手
    private String teamBPlayerName4;
    private Integer teamASubstitutePlayerId1; // teamA替补选手1
    private String teamASubstitutePlayerName1;
    private Integer teamASubstitutePlayerId2; // teamA替补选手2
    private String teamASubstitutePlayerName2;
    private Integer teamASubstitutePlayerId3; // teamB替补选手3
    private String teamASubstitutePlayerName3;
    private Integer teamASubstitutePlayerId4; // teamB替补选手4
    private String teamASubstitutePlayerName4;
    private Integer teamBSubstitutePlayerId1; // teamB替补选手1
    private String teamBSubstitutePlayerName1;
    private Integer teamBSubstitutePlayerId2; // teamB替补选手2
    private String teamBSubstitutePlayerName2;
    private Integer teamBSubstitutePlayerId3; // teamB替补选手3
    private String teamBSubstitutePlayerName3;
    private Integer teamBSubstitutePlayerId4; // teamB替补选手4
    private String teamBSubstitutePlayerName4;
    private Integer currentTeamAPlayerId1; // 当前teamA的1号选手
    private String currentTeamAPlayerName1;
    private Integer currentTeamAPlayerId2; // 当前teamA的2号选手
    private String currentTeamAPlayerName2;
    private Integer currentTeamBPlayerId1; // 当前teamB的1号选手
    private String currentTeamBPlayerName1;
    private Integer currentTeamBPlayerId2; // 当前teamB的2号选手
    private String currentTeamBPlayerName2;
    private Integer teamAScore; // teamA的总分数
    private Integer teamBScore; // teamB的总分数
    private Integer maxSubstitutePlayer; // 最大替补人数
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则）
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则）
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime beginTime; // 比赛开始时间
}
