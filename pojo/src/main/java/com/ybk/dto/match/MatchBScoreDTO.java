package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchBScoreDTO {
    private Long matchBId;
    private Long teamId; // 分数变化的队伍id
    private Integer plusOrMinus; // 加分或者减分
}
