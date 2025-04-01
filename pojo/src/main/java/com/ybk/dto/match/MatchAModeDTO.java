package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * admin创建比赛模式有关的DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchAModeDTO {
    private Integer matchAId; // 关联的matchA
    private Integer matchModeId;
    private String mode; // 比赛模式
    private LocalDateTime beginTime; // 比赛时间
    private Integer venueNumber; // 场地编号
}
