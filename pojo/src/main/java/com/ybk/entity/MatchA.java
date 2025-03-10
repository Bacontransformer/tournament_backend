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
    private Long matchAId; // 主键 自增
    private Long eventId; // 逻辑关联活动id 非空
    private Long teamAId; // 队伍a的id 非空
    private Long teamBId; // 队伍b的id 非空
    private Long winnerTeamId; // 最终获胜队伍id 可为空
    private String teamADepartment; // 队伍a的部门 可为空
    private String teamBDepartment; // 队伍b的部门 可为空
    private String winnerTeamDepartment; // 最终获胜队伍部门 可为空
    private Integer gameCount; // 赛制局数目（1或者3） 非空
    private Integer winScore; // 获胜基础比分（通常是21分） 非空
    private Integer maxParticipationTimes; // 同一个人最多参加次数 默认唯10
    private Integer minTeamAgeSum; // 最少团队年龄 默认为0
    private Integer maxTeamAgeSum; // 最大团队年龄 默认为1000
    private Integer maxSubstitutePlayer; // 最大替补人数 默认为10
    private Integer teamAModeScore; // A队模式大比分 默认唯0
    private Integer teamBModeScore; // B队模式大比分 默认为0
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}