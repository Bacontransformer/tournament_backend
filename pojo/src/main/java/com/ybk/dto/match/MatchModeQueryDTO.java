package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchModeQueryDTO {
    private Integer matchAId;
    private int page;
    private int pageSize;
}
