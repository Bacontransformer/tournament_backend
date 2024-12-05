package com.ybk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderDTO {
    private String passwordFirst;
    private String passwordSecond;
    private String name;
    private String username;
    private String department;
    private String phone;
}
