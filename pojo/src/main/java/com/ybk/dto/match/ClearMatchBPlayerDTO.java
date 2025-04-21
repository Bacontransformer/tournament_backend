package com.ybk.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClearMatchBPlayerDTO {
    private Integer matchBId;
    private Boolean isTeamA;
}
