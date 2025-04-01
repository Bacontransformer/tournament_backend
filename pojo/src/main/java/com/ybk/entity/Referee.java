package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 裁判
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("referee")
public class Referee {
    @TableId("referee_id")
    private Integer refereeId; // 主键 自增
    private String password; // 密码 不可空
    private String username; // 用户名 不可空 唯一
    private String name; // 姓名 不可空
    private String department; // 部门 可空
    private String phone; // 电话号码 可空 唯一
    private Boolean isPassed; // 是否通过审核 默认为false
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
