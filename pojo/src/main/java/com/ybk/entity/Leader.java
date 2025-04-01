package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 领队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("leader")
public class Leader {
    @TableId(value = "leader_id", type = IdType.AUTO)
    private Integer leaderId; // 主键 自增
    private Integer teamId; // 逻辑关联队伍id 非空
    private String username; // 用户名 非空 唯一
    private String phone; // 手机号 非空 唯一
    private String password; // 密码 非空
    private String name; // 姓名 非空
    private String teamName; // 队伍名 非空
    private String gender; // 性别 非空
    private Integer age; // 年龄 非空
    private String department; // 部门 非空
    private Boolean isPassed; // 是否被审核通过 默认唯false
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
