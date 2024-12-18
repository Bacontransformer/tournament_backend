package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 领队
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("leader")
public class Leader {
    @TableId(value = "leader_id", type = IdType.AUTO)
    private Long leaderId;
    private String password;
    private String name;
    private String username;
    private String department;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isPassed;
}
