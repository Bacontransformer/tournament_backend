package com.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 具体的比赛场地
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("venue")
public class Venue {
    @TableId("venue_id")
    private Integer venueId;
    private String place; // 场地位置
    private Integer venueNumber; // 场地编码
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
