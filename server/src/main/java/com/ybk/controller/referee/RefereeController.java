package com.ybk.controller.referee;

import com.ybk.constant.JwtClaimsConstant;
import com.ybk.dto.role.RefereeDTO;
import com.ybk.dto.role.RefereeLoginDTO;
import com.ybk.entity.Referee;
import com.ybk.properties.JwtProperties;
import com.ybk.result.Result;
import com.ybk.service.RefereeService;
import com.ybk.util.JwtUtil;
import com.ybk.vo.RefereeLoginVO;
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
@RequestMapping("/referee")
@Api(tags = "裁判相关接口")
public class RefereeController {
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RefereeService refereeService;

    /**
     * 裁判登录
     *
     * @param refereeLoginDTO
     * @return
     */
    @ApiOperation(value = "裁判登录")
    @PostMapping("/login")
    public Result<RefereeLoginVO> login(@RequestBody RefereeLoginDTO refereeLoginDTO) {
        log.info("裁判登录：{}", refereeLoginDTO);

        Referee referee = refereeService.login(refereeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.REFEREE_ID, referee.getRefereeId());
        String token = JwtUtil.createJWT(
                jwtProperties.getRefereeSecretKey(),
                jwtProperties.getRefereeTtl(),
                claims);

        RefereeLoginVO refereeLoginVO = RefereeLoginVO.builder()
                .refereeId(referee.getRefereeId())
                .userName(referee.getUsername())
                .name(referee.getName())
                .token(token)
                .build();

        return Result.success(refereeLoginVO);
    }

    /**
     * 裁判创建
     *
     * @param refereeDTO
     * @return
     */
    @ApiOperation(value = "裁判创建")
    @PostMapping("/save")
    public Result saveReferee(@RequestBody RefereeDTO refereeDTO) {
        String passwordFirst = refereeDTO.getPasswordFirst();
        String passwordSecond = refereeDTO.getPasswordSecond();
        if (passwordFirst != null && passwordSecond != null) {
            if (!passwordFirst.equals(passwordSecond)) {
                return Result.error("两次输入的密码不一致");
            }
        }
        log.info("领队创建：{}", refereeDTO);
        refereeService.save(refereeDTO);
        return Result.success();
    }

    /**
     * 裁判修改
     *
     * @param refereeDTO
     * @return
     */
    @ApiOperation(value = "裁判修改")
    @PostMapping("/update")
    public Result updateReferee(@RequestBody RefereeDTO refereeDTO) {
        String passwordFirst = refereeDTO.getPasswordFirst();
        String passwordSecond = refereeDTO.getPasswordSecond();
        if (passwordFirst != null && passwordSecond != null) {
            if (!passwordFirst.equals(passwordSecond)) {
                return Result.error("两次输入的密码不一致");
            }
        }
        log.info("裁判修改：{}", refereeDTO);
        refereeService.udpate(refereeDTO);
        return Result.success();
    }
}
