package com.ybk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamAssignmentDTO {
    private Long matchId; // 比赛ID
    private Long teamId; // 队伍ID
    private String teamName; // 队伍名称
    private String leaderName; // 领队姓名
    private String introduction; // 队伍介绍
}
