package com.ybk.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefereeDTO {
    private String passwordFirst;
    private String passwordSecond;
    private String name;
    private String username;
    private String department;
    private String phone;
}
