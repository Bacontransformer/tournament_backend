package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("referee")
public class Referee {
    @TableId("refereeId")
    private Integer refereeId;
    private String password;
    private String username;
    private String name;
    private String department;
    private String phone;
    private Boolean isPassed;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
