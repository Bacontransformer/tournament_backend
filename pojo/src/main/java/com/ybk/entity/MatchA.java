package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * MatchA: Configurable Traditional Match
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_a")
public class MatchA extends Match {
    private List<String> modes; // 比赛模式，例如男单、女单等
    private Integer maxPlayerParticipation; // 单个选手的最大上场次数
    private Integer roundCount; // 每局的局数
    private Integer winScore; // 单局获胜分数
    private List<MatchSet> matchSets; // 每局的比分明细
}