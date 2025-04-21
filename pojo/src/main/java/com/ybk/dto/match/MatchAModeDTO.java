package com.ybk.dto.match;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Integer refereeId; // 裁判id
    private Integer substituteRefereeId; // 替补裁判id
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime beginTime; // 比赛时间
    private Integer venueNumber; // 场地编号
}
