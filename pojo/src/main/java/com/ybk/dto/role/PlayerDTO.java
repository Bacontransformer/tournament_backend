package com.ybk.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private String gender;
    private String name;
    private Integer age;
    private String department;
    private String phone;
    private String role; // 队员角色（如：队长、副队长、队员）
    private Boolean isActive; // 是否活跃
}
