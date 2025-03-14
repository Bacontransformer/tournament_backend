package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchAPlayerDTO {
    private Integer assignmentId;
    private Integer matchAId;
    private Integer matchModeId;
    private Integer teamId; // 队伍ID
    private Integer playerId; // 选手ID
    private String mode;
    private Boolean isSubstitute; // 是否替补
}
