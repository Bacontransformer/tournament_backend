package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * MatchB: 1234 趣味比赛
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_b")
public class MatchB extends Match {
    private Integer sectionScore; // 每小节的获胜分数 x
    private Integer totalSections; // 总小节数 (固定为4)
    private List<MatchBSection> sections; // 每个小节的详细信息
    private Integer minTeamAgeSum; // 最小队伍年龄和（可选规则）
    private Integer maxTeamAgeSum; // 最大队伍年龄和（可选规则）
}
