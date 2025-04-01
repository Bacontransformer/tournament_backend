package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 宣布比赛开始
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeginMatchDTO {
    private Integer matchId;
    private Integer teamId;
}
