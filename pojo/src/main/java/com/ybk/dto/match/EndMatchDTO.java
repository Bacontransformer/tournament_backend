package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 判决比赛结束
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndMatchDTO {
    private Long matchId;
    private Long teamId;
}
