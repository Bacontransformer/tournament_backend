package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 单场比赛
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("match")
public class Match {
    @TableId("match_id")
    private Integer matchId;
    private String place;
    private Integer venue_id;
    // 比赛开始时间
    private LocalDateTime begin_time;
    // 比赛的局数
    private Integer match_round;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
