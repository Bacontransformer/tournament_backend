package com.ybk.controller.admin;

import com.ybk.constant.JwtClaimsConstant;
import com.ybk.dto.AdminLoginDTO;
import com.ybk.entity.Admin;
import com.ybk.properties.JwtProperties;
import com.ybk.result.Result;
import com.ybk.service.AdminService;
import com.ybk.util.JwtUtil;
import com.ybk.vo.AdminLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员相关接口")
public class AdminController {
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AdminService adminService;

    @ApiOperation(value="管理员登录")
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO){
        log.info("管理员登录：{}", adminLoginDTO);

        Admin admin = adminService.login(adminLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ID, admin.getAdminId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .adminId(admin.getAdminId())
                .userName(admin.getUsername())
                .name(admin.getName())
                .token(token)
                .build();

        return Result.success(adminLoginVO);
    }


}
