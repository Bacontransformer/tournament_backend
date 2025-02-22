package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * MatchA: 传统比赛【可配置】
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_a")
public class MatchA {
    @TableId("match_a_id")
    private Long matchAId;
    private Long eventId;
    private Long teamAId;
    private Long teamBId;
    private Long winnerTeamId; // 最终获胜队伍id
    private String teamADepartment;
    private String teamBDepartment;
    private String winnerTeamDepartment; // 最终获胜队伍部门
    private Integer gameCount; // 赛制局数目（1或者3）
    private Integer winScore; // 获胜基础比分（通常是21分）
    private Integer maxParticipationTimes; // 同一个人最多参加次数
    private Integer minTeamAgeSum; // 最少团队年龄
    private Integer maxTeamAgeSum; // 最大团队年龄
    private Integer maxSubstitutePlayer; // 最大替补人数
    private Integer teamAModeScore; // A队模式大比分
    private Integer teamBModeScore; // B队模式大比分
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}