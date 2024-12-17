package com.ybk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerAssignmentDTO {
    private Long matchId; // 比赛ID
    private Long teamId; // 队伍ID
    private Long playerId; // 选手ID
    private String playerName; // 选手姓名
    private String role; // 选手角色（如1号、2号、队长等）
    private Boolean isCaptain; // 是否为队长
}
