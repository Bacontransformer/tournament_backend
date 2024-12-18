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
 * MatchA: Configurable Traditional Match
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_a")
public class MatchA {
    @TableId("match_a_id")
    private Long matchAId; // 比赛的id
    private Long eventId; // 活动的id
    private Long teamAId; // 队伍A的id
    private Long teamBId; // 队伍B的id
    private LinkedList<String> modes; // 比赛模式,例如男单、女单等,通过有序列表来存储,有先后顺序
    private Integer roundCount; // 每一个比赛模式的局数
    private Integer winScore; // 单局获胜分数
    private Map<Integer,List<Player>> teamAPlayers; // 每个比赛模式teamA对应的选手
    private Map<Integer,List<Player>> teamBPlayers; // 每个比赛模式teamB对应的选手
    private List<Player> teamASubstitutePlayers; // teamA替补选手
    private List<Player> teamBSubstitutePlayers; // teamB替补选手
    private Map<Integer,Map<Integer, MatchSet>> matchSets; // 每个比赛模式对应的每一局的比分记录
    private Integer maxParticipationTimes; // 单个选手的最大可参加比赛模式的数量
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则）
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则）
    private Integer maxSubstitutePlayer; // 最大替补人数
    private LocalDateTime beginTime; // 比赛开始时间
    public LocalDateTime createTime;
    public LocalDateTime updateTime;
}