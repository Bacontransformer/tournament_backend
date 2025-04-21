package com.ybk.dto.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchAScoreDTO {
    private Integer matchAId;
    private Integer matchModeId;
    private Integer teamARoundScore1;
    private Integer teamARoundScore2;
    private Integer teamARoundScore3;
    private Integer teamBRoundScore1;
    private Integer teamBRoundScore2;
    private Integer teamBRoundScore3;
}
