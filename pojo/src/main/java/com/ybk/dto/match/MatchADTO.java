package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * admin编辑MatchA的数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchADTO {
    private Long matchAId;
    private Long eventId; // 活动的id
    private Long teamAId; // 队伍A的id
    private Long teamBId; // 队伍B的id
    private Integer venueNumber; // 场地编号
    private LinkedList<String> modes; // 比赛模式,例如男单、女单等,通过有序列表来存储,有先后顺序
    private Integer gameCount; // 每一个比赛模式的局数
    private Integer winScore; // 单局获胜分数
    private Integer maxParticipationTimes; // 单个选手的最大可参加比赛模式的数量
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则）
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则）
    private Integer maxSubstitutePlayer; // 最大替补人数
    private LocalDateTime beginTime; // 比赛开始时间
}
