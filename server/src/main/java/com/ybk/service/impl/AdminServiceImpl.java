package com.ybk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ybk.dto.role.AdminLoginDTO;
import com.ybk.entity.Admin;
import com.ybk.exception.AccountNotFoundException;
import com.ybk.exception.PasswordErrorException;
import com.ybk.mapper.AdminMapper;
import com.ybk.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUsername();
        String password = adminLoginDTO.getPassword();
        //1、根据用户名查询数据库中的数据
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Admin admin = adminMapper.selectOne(wrapper);
        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (admin == null) {
            //账号不存在
            throw new AccountNotFoundException("用户名不存在");
        }
        //密码比对
        // 对前端传过来的明文密码进行处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(admin.getPassword())) {
            //密码错误
            throw new PasswordErrorException("密码错误");
        }
        //3、返回实体对象
        return admin;
    }
}
