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
    private Long playerId;
    private Long teamId;
    private Long leaderId;
    private String gender;
    private String name;
    private Integer age;
    private String department;
    private String phone;
    private String role; // 队员角色（如：队长、副队长、队员）
    private Boolean isActive; // 是否活跃
    private LocalDateTime joinTime; // 加入队伍时间
}
