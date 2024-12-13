package com.ybk.service;

import com.ybk.dto.AdminLoginDTO;
import com.ybk.entity.Admin;

public interface AdminService {
    Admin login(AdminLoginDTO adminLoginDTO);
}
