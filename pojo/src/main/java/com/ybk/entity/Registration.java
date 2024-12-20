package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 球队报名记录
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("registration")
public class Registration {
    @TableId("registration_id")
    private Long registrationId; // 主键ID
    private Long teamId; // 队伍ID
    private Long eventId; // 活动ID
    private String teamName; // 队伍名称
    private String eventName; // 活动名称
    private Date registrationTime; // 报名时间
    private String comment; // 备注
}
