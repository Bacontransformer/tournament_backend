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
    private Integer matchModeId; // 主键 自增
    private Integer matchAId; // 逻辑关联match-a的id 不可空
    private String mode; // 模式 不可空
    private Integer venueNumber; // 场地编号 不可空
    private Integer refereeId; // 裁判id 不可空
    private Integer substituteRefereeId; // 替补裁判id 可空
    private Integer teamAId; // a队id  不可空
    private Integer teamBId; // b队id  不可空
    private Integer modeWinnerTeamId; // 该模式获胜的队伍id 可空
    private LocalDateTime beginTime; // 比赛开始时间 不可空
    private Integer status; // 0:未开始，1:进行中，2:已结束 默认为0
    private Integer teamAPlayer1; // a队参赛队员1号 可空
    private Integer teamAPlayer2; // a队参赛队员2号 可空
    private Integer teamBPlayer1; // b队参赛队员1号 可空
    private Integer teamBPlayer2; // b队参赛队员2号 可空
    private Integer teamASubstitutePlayer1; // a队替补1号 可空
    private Integer teamASubstitutePlayer2; // a队替补2号 可空
    private Integer teamBSubstitutePlayer1; // b队替补1号 可空
    private Integer teamBSubstitutePlayer2; // b队替补2号 可空
    private Integer teamARoundScore1; // teamA第1局的得分 默认为0
    private Integer teamARoundScore2; // teamA第2局的得分 可为空
    private Integer teamARoundScore3; // teamA第3局的得分 可为空
    private Integer teamBRoundScore1; // teamB第1局的得分 默认为0
    private Integer teamBRoundScore2; // teamB第2局的得分 可为空
    private Integer teamBRoundScore3; // teamB第3局的得分 可为空
    private Integer teamAGameScore; // A队某个模式的获胜局数 默认为0
    private Integer teamBGameScore; // B队某个模式的获胜局数 默认为0
    private Integer currentGame; // 当前第几局 默认为1
    private Integer currentRound; // 当前第几轮 默认为1
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}