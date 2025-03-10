package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class MatchB{
    @TableId("match_b_id")
    private Long matchBId; // 主键 自增
    private Long eventId; // 逻辑关联活动id 非空
    private Integer status; // 比赛状态 默认为0
    private Integer sectionScore; // 每小节的获胜分数 x，一共有四个小节 非空
    private Integer currentSection; // 当前小节 默认为1
    private Long teamAId; // teamA的编号 非空
    private Long teamBId; // teamB的编号 非空
    private Long winnerTeamId; // 获胜队伍编号 可为空
    private Long refereeId; // 裁判的id 非空
    private Long substituteRefereeId; // 替补裁判的id 可为空
    private String teamADepartment; // teamA的部门 可为空
    private String teamBDepartment; // teamB的部门 可为空
    private String winnerTeamDepartment; // 胜者队伍的部门 可为空
    private Integer venueNumber; // 场地编号 非空
    private Long teamAPlayerId1; // teamA的1号选手 可为空
    private Long teamAPlayerId2; // teamA的2号选手 可为空
    private Long teamAPlayerId3; // teamA的3号选手 可为空
    private Long teamAPlayerId4; // teamA的4号选手 可为空
    private Long teamBPlayerId1; // teamB的1号选手 可为空
    private Long teamBPlayerId2; // teamB的2号选手 可为空
    private Long teamBPlayerId3; // teamB的3号选手 可为空
    private Long teamBPlayerId4; // teamB的4号选手 可为空
    private Long teamASubstitutePlayerId1; // teamA替补选手1 可为空
    private Long teamASubstitutePlayerId2; // teamA替补选手2 可为空
    private Long teamASubstitutePlayerId3; // teamB替补选手3 可为空
    private Long teamASubstitutePlayerId4; // teamB替补选手4 可为空
    private Long teamBSubstitutePlayerId1; // teamB替补选手1 可为空
    private Long teamBSubstitutePlayerId2; // teamB替补选手2 可为空
    private Long teamBSubstitutePlayerId3; // teamB替补选手3 可为空
    private Long teamBSubstitutePlayerId4; // teamB替补选手4 可为空
    private Long currentTeamAPlayerId1; // 当前teamA的1号选手 可为空
    private Long currentTeamAPlayerId2; // 当前teamA的2号选手 可为空
    private Long currentTeamBPlayerId1; // 当前teamB的1号选手 可为空
    private Long currentTeamBPlayerId2; // 当前teamB的2号选手 可为空
    private Integer teamAScore; // teamA的总分数 默认为0
    private Integer teamBScore; // teamB的总分数 默认为0
    private Integer maxSubstitutePlayer; // 最大替补人数 默认为10
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则） 默认为0
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则） 默认为1000
    private LocalDateTime beginTime; // 比赛开始时间 非空
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
