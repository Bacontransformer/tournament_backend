package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchAScoreDTO {
    private Integer matchAId;
    private Integer matchModeId;
    private String mode; // 比赛模式
    private Integer CurrentGame; // 第几局
    private Integer teamId; // 分数变化的队伍id
    private Integer plusOrMinus; // 加分或者减分
}
