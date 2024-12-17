package com.ybk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRegistrationDTO {
    private Long teamId; // 队伍ID
    private String teamName; // 队伍名称
    private Long eventId; // 活动ID
    private String eventName; // 活动名称
    private String remark; // 备注（可选）
}
