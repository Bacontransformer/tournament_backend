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
    private Integer matchBId;
    private Integer eventId; // 关联到活动表
    private Integer venueNumber; // 场地编号
    private Integer sectionScore; // 每小节的获胜分数 x，一共有四个小节
    private Integer teamAId; // teamA的编号
    private Integer teamBId; // teamB的编号
    private Integer refereeId; // 裁判的id
    private Integer maxSubstitutePlayer; // 最大替补人数
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则）
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则）
    private LocalDateTime beginTime; // 比赛开始时间
}
