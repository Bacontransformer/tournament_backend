package com.ybk.dto.match;

import lombok.Data;

@Data
public class RegistrationPageDTO {
    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

    // 活动ID
    private Long eventId;
}
