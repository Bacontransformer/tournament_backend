package com.ybk.dto.match;

import com.ybk.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * admin编辑MatchB的数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchBDTO {
    private Long eventId; // 关联到活动表
    private Integer sectionScore; // 每小节的获胜分数 x，一共有四个小节
    private Long teamAId; // teamA的编号
    private Long teamBId; // teamB的编号
    private Integer maxSubstitutePlayer; // 最大替补人数
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则）
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则）
    private LocalDateTime beginTime; // 比赛开始时间
}
