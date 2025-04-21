package com.ybk.service;

import com.ybk.dto.role.AdminLoginDTO;
import com.ybk.entity.Admin;

public interface AdminService {
    Admin login(AdminLoginDTO adminLoginDTO);
}
