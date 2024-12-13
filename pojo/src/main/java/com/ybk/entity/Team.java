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
@TableName("team")
public class Team {
    @TableId("team_id")
    private Integer teamId;
    private Integer leaderId;
    private String name;
    private String LeaderName;
    private String introduction; // 数据库中可以为空
    private String department;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
