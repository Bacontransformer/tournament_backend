package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * MatchB: 1234 趣味比赛
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_b")
public class MatchB {
    @TableId("match_b_id")
    private Integer matchBId; // 主键 自增
    private Integer eventId; // 逻辑关联活动id 非空
    private Integer status; // 比赛状态 默认为0
    private Integer sectionScore; // 每小节的获胜分数 x，一共有四个小节 非空
    private Integer currentSection; // 当前小节 默认为1
    private Integer teamAId; // teamA的编号 非空
    private Integer teamBId; // teamB的编号 非空
    private Integer winnerTeamId; // 获胜队伍编号 可为空
    private Integer refereeId; // 裁判的id 非空
    private Integer substituteRefereeId; // 替补裁判的id 可为空
    private String refereeName; // 裁判的名字 非空
    private String substituteRefereeName; // 替补裁判的名字 可为空
    private String teamADepartment; // teamA的部门 可为空
    private String teamBDepartment; // teamB的部门 可为空
    private String winnerTeamDepartment; // 胜者队伍的部门 可为空
    private Integer venueNumber; // 场地编号 非空
    private Integer teamAPlayerId1; // teamA的1号选手 可为空
    private String teamAPlayerName1; // teamA的1号选手的名字 可为空
    private Integer teamAPlayerId2; // teamA的2号选手 可为空
    private String teamAPlayerName2; // teamA的2号选手的名字 可为空
    private Integer teamAPlayerId3; // teamA的3号选手 可为空
    private String teamAPlayerName3; // teamA的3号选手的名字 可为空
    private Integer teamAPlayerId4; // teamA的4号选手 可为空
    private String teamAPlayerName4; // teamA的4号选手的名字 可为空
    private Integer teamBPlayerId1; // teamB的1号选手 可为空
    private String teamBPlayerName1; // teamB的1号选手的名字 可为空
    private Integer teamBPlayerId2; // teamB的2号选手 可为空
    private String teamBPlayerName2; // teamB的2号选手的名字 可为空
    private Integer teamBPlayerId3; // teamB的3号选手 可为空
    private String teamBPlayerName3; // teamB的3号选手的名字 可为空
    private Integer teamBPlayerId4; // teamB的4号选手 可为空
    private String teamBPlayerName4; // teamB的4号选手的名字 可为空
    private Integer teamASubstitutePlayerId1; // teamA替补选手1 可为空
    private String teamASubstitutePlayerName1; // teamA替补选手1的名字 可为空
    private Integer teamASubstitutePlayerId2; // teamA替补选手2 可为空
    private String teamASubstitutePlayerName2; // teamA替补选手2的名字 可为空
    private Integer teamASubstitutePlayerId3; // teamB替补选手3 可为空
    private String teamASubstitutePlayerName3; // teamA替补选手3的名字 可为空
    private Integer teamASubstitutePlayerId4; // teamB替补选手4 可为空
    private String teamASubstitutePlayerName4; // teamA替补选手4的名字 可为空
    private Integer teamBSubstitutePlayerId1; // teamB替补选手1 可为空
    private String teamBSubstitutePlayerName1; // teamB替补选手1的名字 可为空
    private Integer teamBSubstitutePlayerId2; // teamB替补选手2 可为空
    private String teamBSubstitutePlayerName2; // teamB替补选手2的名字 可为空
    private Integer teamBSubstitutePlayerId3; // teamB替补选手3 可为空
    private String teamBSubstitutePlayerName3; // teamB替补选手3的名字 可为空
    private Integer teamBSubstitutePlayerId4; // teamB替补选手4 可为空
    private String teamBSubstitutePlayerName4; // teamB替补选手4的名字 可为空
    private Integer teamAScore; // teamA的总分数 默认为0
    private Integer teamBScore; // teamB的总分数 默认为0
    private Integer maxSubstitutePlayer; // 最大替补人数 默认为10
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则） 默认为0
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则） 默认为1000
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime beginTime; // 比赛开始时间 非空
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
