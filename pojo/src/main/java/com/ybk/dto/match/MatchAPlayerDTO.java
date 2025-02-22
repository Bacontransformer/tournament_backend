package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchAPlayerDTO {
    private Long assignmentId;
    private Long matchAId;
    private Long matchModeId;
    private Long teamId; // 队伍ID
    private Long playerId; // 选手ID
    private String mode;
    private Boolean isSubstitute; // 是否替补
}
