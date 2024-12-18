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
    private Long matchBId; // 比赛编号
    private Long eventId; // 关联到活动表
    private Integer sectionScore; // 每小节的获胜分数 x，一共有四个小节
    private Long teamAId; // teamA的编号
    private Long teamBId; // teamB的编号
    private LinkedList<Player> teamAPlayers; // teamA的1、2、3、4号选手
    private LinkedList<Player> teamBPlayers; // teamB的1、2、3、4号选手
    private List<Player> teamASubstitutePlayers; // teamA替补选手
    private List<Player> teamBSubstitutePlayers; // teamB替补选手
    private Integer teamAScore; // teamA的总分数
    private Integer teamBScore; // teamB的总分数
    private List<Boolean> scoreList; // 0代表 teamA 1代表 teamB
    private Integer maxSubstitutePlayer; // 最大替补人数
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则）
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则）
    private LocalDateTime beginTime; // 比赛开始时间
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
