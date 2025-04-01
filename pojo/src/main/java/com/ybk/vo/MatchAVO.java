package com.ybk.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchAVO {
    private Integer matchAId;
    private Integer eventId;
    private Integer teamAId;
    private Integer teamBId;
    private Integer winnerTeamId; // 最终获胜队伍id
    private String teamADepartment;
    private String teamBDepartment;
    private String winnerTeamDepartment; // 最终获胜队伍部门
    private Integer status; // 0:未开始，1:进行中，2:已结束
    private Integer gameCount; // 赛制局数目（1或者3）
    private Integer winScore; // 获胜基础比分（通常是21分）
    private Integer maxParticipationTimes; // 同一个人最多参加次数
    private Integer minTeamAgeSum; // 最少团队年龄
    private Integer maxTeamAgeSum; // 最大团队年龄
    private Integer maxSubstitutePlayer; // 最大替补人数
    private Integer teamAModeScore; // A队模式大比分
    private Integer teamBModeScore; // B队模式大比分

    private List<String> modes; // 比赛模式（比如男单1，男单2，男双1，男双2）

    private Map<String, Integer> venueNumber; // 不同模式对应的场地编号
    private Map<String,Integer> refereeId; // 不同模式下的裁判id
    private Map<String, LocalDateTime> beginTimes; // 比赛开始时间
    private Map<String, Integer> teamAPlayer1; // a队参赛队员1号
    private Map<String, Integer> teamAPlayer2; // a队参赛队员2号(不一定有)
    private Map<String, Integer> teamBPlayer1; // b队参赛队员1号
    private Map<String, Integer> teamBPlayer2; // b队参赛队员2号(不一定有)
    private Map<String, Integer> teamASubstitutePlayer1; // a队替补1号
    private Map<String, Integer> teamASubstitutePlayer2; // a队替补2号(不一定有)
    private Map<String, Integer> teamBSubstitutePlayer1; // b队替补1号
    private Map<String, Integer> teamBSubstitutePlayer2; // b队替补2号(不一定有)
    private Map<String, Integer> teamARoundScore1; // teamA在某个模式下第1局的得分
    private Map<String, Integer> teamARoundScore2; // teamA在某个模式下第2局的得分(不一定有第二局)
    private Map<String, Integer> teamARoundScore3; // teamA在某个模式下第3局的得分(不一定有第三局)
    private Map<String, Integer> teamBRoundScore1; // teamB在某个模式下第1局的得分
    private Map<String, Integer> teamBRoundScore2; // teamB在某个模式下第2局的得分(不一定有第二局)
    private Map<String, Integer> teamBRoundScore3; // teamB在某个模式下第3局的得分(不一定有第三局)
    private Map<String, Integer> teamAGameScore; // A队某个模式的获胜局数
    private Map<String, Integer> teamBGameScore; // B队某个模式的获胜局数
    private Map<String, Integer> currentGame; // 某个模式当前第几局
    private Map<String, Integer> currentRound; // 某个模式当前第几轮

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
