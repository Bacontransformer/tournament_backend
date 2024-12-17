package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Base Match Class
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("match")
public class Match {
    @TableId("match_id")
    private Long matchId;
    private Long eventId; // 关联到活动
    private String matchType; // MatchA or MatchB
    private LocalDateTime beginTime;
    private Integer totalRounds; // 比赛总局数
    private Long teamAId;
    private Long teamBId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}