package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchAPlayerDTO {
    private Integer matchModeId; // 主键 自增
    private Integer matchAId; // 逻辑关联match-a的id 不可空
    private Integer teamAPlayer1; // a队参赛队员1号 不可空
    private String teamAPlayer1Name; // a队参赛队员1号姓名 可空
    private Integer teamAPlayer2; // a队参赛队员2号 可空
    private String teamAPlayer2Name; // a队参赛队员2号姓名 可空
    private Integer teamBPlayer1; // b队参赛队员1号 不可空
    private String teamBPlayer1Name; // b队参赛队员1号姓名 可空
    private Integer teamBPlayer2; // b队参赛队员2号 可空
    private String teamBPlayer2Name; // b队参赛队员2号姓名 可空
    private Integer teamASubstitutePlayer1; // a队替补1号 可空
    private String teamASubstitutePlayer1Name; // a队替补1号姓名 可空
    private Integer teamASubstitutePlayer2; // a队替补2号 可空
    private String teamASubstitutePlayer2Name; // a队替补2号姓名 可空
    private Integer teamBSubstitutePlayer1; // b队替补1号 可空
    private String teamBSubstitutePlayer1Name; // b队替补1号姓名 可空
    private Integer teamBSubstitutePlayer2; // b队替补2号 可空
    private String teamBSubstitutePlayer2Name; // b队替补2号姓名 可空
    private Boolean isTeamA; // 裁判是否是teamA? true:是，false:否
}
