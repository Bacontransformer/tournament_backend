package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Match Player Assignment: 为比赛设置参赛队员
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_player")
public class MatchPlayer {
    @TableId("assignment_id")
    private Long assignmentId;
    private Long matchId; // 所属比赛ID
    private Long teamId; // 所属队伍ID
    private Long playerId; // 上场选手ID
    private String role; // 选手角色（如1号、2号等）
    private Boolean isCaptain; // 是否队长
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
