package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * referee对MatchA或者MatchB的某一个比赛类型某一局某一分的打分数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchScoreDTO {
    private Long matchId; // 比赛ID
    private Long refereeId; // 裁判id
    private Integer typeOrder; // 比赛类型序号
    private Integer matchRound; // 当前局数
    private Integer roundTurn; // 第几回合
    private Boolean pointTeam; // 得分队伍，0表示teamA，1表示teamB
}
