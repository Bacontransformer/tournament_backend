package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * admin编辑MatchA的数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchADTO {
    private Integer matchAId;
    private Integer eventId; // 活动的id
    private Integer teamAId; // 队伍A的id
    private Integer teamBId; // 队伍B的id
    private Integer gameCount; // 每一个比赛模式的局数
    private Integer winScore; // 单局获胜分数
    private Integer maxParticipationTimes; // 单个选手的最大可参加比赛模式的数量
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则）
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则）
    private Integer maxSubstitutePlayer; // 最大替补人数
}
