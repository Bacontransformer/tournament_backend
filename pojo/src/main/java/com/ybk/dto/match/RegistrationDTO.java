package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * leader的比赛报名数据传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private Long teamId; // 队伍ID
    private Long eventId; // 活动ID
    private String teamName; // 队伍名称
    private String comment; // 备注（可选）
}
