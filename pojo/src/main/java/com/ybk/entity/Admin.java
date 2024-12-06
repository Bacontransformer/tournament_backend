package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统管理员
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin")
public class Admin {
    @TableId("admin_id")
    private Integer adminId;
    private String password;
    private String username;
    private String name;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
