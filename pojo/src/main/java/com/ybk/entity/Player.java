package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 队员
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("player")
public class Player {
    @TableId("player_id")
    private Integer playerId; // 主键 自增
    private Integer teamId; // 逻辑关联队伍id 非空
    private Integer leaderId; // 逻辑关联领队id 非空
    private String gender; // 性别 非空
    private String name; // 姓名 非空
    private Integer age; // 年龄 非空
    private String department; // 部门 可空
    private String phone; // 电话 可空
    private String role; // 队员角色（如：队长、副队长、队员） 可空
    private Boolean isActive; // 是否活跃 不可空
    private LocalDateTime joinTime; // 加入队伍时间 可空
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
