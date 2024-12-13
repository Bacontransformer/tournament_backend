package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("team_member")
public class TeamMember {
    @TableId("id")
    private Integer id; // 主键，自增
    private Integer teamId; // 队伍ID
    private Integer playerId; // 队员ID
    private String role; // 队员角色（如：队长、副队长、队员）
    private Boolean isActive; // 是否活跃
    private LocalDateTime joinTime; // 加入队伍时间
}

