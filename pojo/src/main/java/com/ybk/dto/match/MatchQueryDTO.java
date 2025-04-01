package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchQueryDTO {
    private Integer eventId;
    private String department;
    private int page;
    private int pageSize;
}
