package com.ybk.controller.leader;

import com.ybk.constant.JwtClaimsConstant;
import com.ybk.dto.LeaderDTO;
import com.ybk.dto.LeaderLoginDTO;
import com.ybk.entity.Leader;
import com.ybk.properties.JwtProperties;
import com.ybk.result.Result;
import com.ybk.service.LeaderService;
import com.ybk.util.JwtUtil;
import com.ybk.vo.LeaderLoginVo;
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
@RequestMapping("/leader")
@Api(tags = "领队相关接口")
public class LeaderController {
    @Autowired
    private LeaderService leaderService;
    @Autowired
    private JwtProperties jwtProperties;

    @ApiOperation(value="领队登录")
    @PostMapping("/login")
    public Result<LeaderLoginVo> login(@RequestBody LeaderLoginDTO leaderLoginDTO){
        log.info("领队登录：{}", leaderService);

        Leader leader = leaderService.login(leaderLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.LEADER_ID, leader.getLeaderId());
        String token = JwtUtil.createJWT(
                jwtProperties.getLeaderSecretKey(),
                jwtProperties.getLeaderTtl(),
                claims);

        LeaderLoginVo leaderLoginVo = LeaderLoginVo.builder()
                .leaderId(leader.getLeaderId())
                .userName(leader.getUsername())
                .name(leader.getName())
                .token(token)
                .build();

        return Result.success(leaderLoginVo);
    }

    @ApiOperation(value = "领队创建")
    @PostMapping("/save")
    public Result saveLeader(@RequestBody LeaderDTO leaderDto){
        String passwordFirst = leaderDto.getPasswordFirst();
        String passwordSecond = leaderDto.getPasswordSecond();
        if (passwordFirst != null && passwordSecond != null) {
            if (!passwordFirst.equals(passwordSecond)){
                return Result.error("两次输入的密码不一致");
            }
        }
        log.info("领队创建：{}", leaderDto);
        leaderService.save(leaderDto);
        return Result.success();
    }


}