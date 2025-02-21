package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * MatchSet: 每一局的比分
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_mode")
public class MatchMode {
    @TableId("match_mode_id")
    private Long matchModeId;
    private Long matchAId;
    private String mode;
    private Integer venueNumber; // 不同模式对应的场地编号
    private Long refereeId; // 不同模式下的裁判id
    private Long teamAId; // a队id
    private Long teamBId; // b队id
    private Long modeWinnerTeamId; // 该模式获胜的队伍id
    private LocalDateTime beginTimes; // 比赛开始时间
    private Long teamAPlayer1; // a队参赛队员1号
    private Long teamAPlayer2; // a队参赛队员2号(不一定有)
    private Long teamBPlayer1; // b队参赛队员1号
    private Long teamBPlayer2; // b队参赛队员2号(不一定有)
    private Long teamASubstitutePlayer1; // a队替补1号
    private Long teamASubstitutePlayer2; // a队替补2号(不一定有)
    private Long teamBSubstitutePlayer1; // b队替补1号
    private Long teamBSubstitutePlayer2; // b队替补2号(不一定有)
    private Integer teamARoundScore1; // teamA在某个模式下第1局的得分
    private Integer teamARoundScore2; // teamA在某个模式下第2局的得分(不一定有第二局)
    private Integer teamARoundScore3; // teamA在某个模式下第3局的得分(不一定有第三局)
    private Integer teamBRoundScore1; // teamB在某个模式下第1局的得分
    private Integer teamBRoundScore2; // teamB在某个模式下第2局的得分(不一定有第二局)
    private Integer teamBRoundScore3; // teamB在某个模式下第3局的得分(不一定有第三局)
    private Integer teamAGameScore; // A队某个模式的获胜局数
    private Integer teamBGameScore; // B队某个模式的获胜局数
    private Integer currentGame; // 某个模式当前第几局
    private Integer currentRound; // 某个模式当前第几轮
}