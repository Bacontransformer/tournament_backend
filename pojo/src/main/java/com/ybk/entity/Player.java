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
@TableName("player")
public class Player {
    @TableId("player_id")
    private Integer playerId;
    private Integer teamId;
    private String gender;
    private String name;
    private String department;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
