package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("team_registration")
public class TeamRegistration {
    private Long registrationId; // 主键ID
    private Long teamId; // 队伍ID
    private Long eventId; // 活动ID
    private String teamName; // 队伍名称
    private String eventName; // 活动名称
    private Date registrationTime; // 报名时间
    private String remark; // 备注
}
