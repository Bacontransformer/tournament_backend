package com.ybk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderMatchModeVO {
    private Integer matchModeId; // 主键 自增
    private Integer matchAId; // 逻辑关联match-a的id 不可空
    private String mode; // 模式 不可空
    private Integer venueNumber; // 场地编号 不可空
    private Integer refereeId; // 裁判id 不可空
    private String refereeName; // 裁判姓名 不可空
    private Integer substituteRefereeId; // 替补裁判id 可空
    private String substituteRefereeName; // 替补裁判姓名 可空
    private Integer teamAId; // a队id  不可空
    private String teamADepartment;
    private Integer teamBId; // b队id  不可空
    private String teamBDepartment;
    private Integer modeWinnerTeamId; // 该模式获胜的队伍id 可空
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime beginTime; // 比赛开始时间 不可空
    private Integer status; // 0:未开始，1:进行中，2:已结束 默认为0
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
