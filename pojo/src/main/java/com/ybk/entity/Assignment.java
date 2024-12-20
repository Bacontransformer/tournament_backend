package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 队员上场记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("assignment")
public class Assignment {
    @TableId("assignment_id")
    private Long assignmentId;          // 球员上场记录ID
    private Long matchId;               // 比赛ID
    private Integer matchType;          // 比赛类型（0=MatchA, 1=MatchB）
    private Integer typeOrder;          // 比赛模式序号
    private Long teamId;                // 队伍ID
    private Long playerId;              // 球员ID
    private Boolean isSubstitute;       // 是否为替补
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
}
