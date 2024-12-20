package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 队伍
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("team")
public class Team {
    @TableId("team_id")
    private Long teamId;
    private Long leaderId;
    private String name;
    private String LeaderName;
    private String introduction; // 数据库中可以为空
    private String department;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
