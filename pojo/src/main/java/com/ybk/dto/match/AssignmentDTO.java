package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * leader对某一场Match某一比赛类型的队员任免数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {
    private Long assignmentId;
    private Long matchId; // 比赛ID
    private Long teamId; // 队伍ID
    private Long playerId; // 选手ID
    private Integer matchType; // 比赛类型（0=MatchA, 1=MatchB）
    private Integer typeOrder; // 比赛类型序号
    private Boolean isSubstitute;  // 是否为替补
}
