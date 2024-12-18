package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * leader对某一场Match某一比赛类型的队员任免数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerAssignmentDTO {
    private Long matchId; // 比赛ID
    private Long teamId; // 队伍ID
    private Long playerId; // 选手ID
    private Integer typeOrder; // 比赛类型序号
}
