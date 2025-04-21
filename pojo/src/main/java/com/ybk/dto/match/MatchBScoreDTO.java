package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchBScoreDTO {
    private Integer matchBId;
    private Integer teamAScore;
    private Integer teamBScore;
}
