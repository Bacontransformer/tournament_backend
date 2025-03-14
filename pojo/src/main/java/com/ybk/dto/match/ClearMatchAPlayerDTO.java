package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClearMatchAPlayerDTO {
    private Integer matchModeId;
    private Integer teamId;
}
