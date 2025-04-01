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
    private Integer adminId; // 主键 自增
    private String password; // 密码 非空
    private String username; // 用户名 非空
    private String name; // 姓名 非空
    private String phone; // 电话号码 非空
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
