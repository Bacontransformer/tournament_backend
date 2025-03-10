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
    private Long teamId; // 主键 自增
    private Long leaderId; // 逻辑管理领队id 非空
    private String name; // 队伍名 非空
    private String LeaderName; // 领队名 可空
    private String introduction; // 简介 可空
    private String department; // 部门 非空
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
