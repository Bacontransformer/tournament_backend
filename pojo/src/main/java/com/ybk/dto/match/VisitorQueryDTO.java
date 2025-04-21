package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorQueryDTO {
    private int page;
    private int pageSize;
    private String name; // 球员名字
    private String department; // 部门
}
