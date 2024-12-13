package com.ybk.controller.leader;

import com.ybk.dto.LeaderDTO;
import com.ybk.dto.PlayerDTO;
import com.ybk.result.Result;
import com.ybk.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/player")
@Api(tags = "队员相关接口")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @ApiOperation("队员创建")
    @PostMapping("/save")
    public Result savePlayer(@RequestBody PlayerDTO playerDTO){
        log.info("队员创建：{}", playerDTO);
        // playerService.save(playerDTO);
        return Result.success();
    }
}
