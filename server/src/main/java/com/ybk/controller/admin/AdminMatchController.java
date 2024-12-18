package com.ybk.controller.admin;

import com.ybk.dto.match.MatchADTO;
import com.ybk.result.Result;
import com.ybk.service.*;
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
@RequestMapping("/admin-match")
@Api(tags = "admin比赛相关接口")
public class AdminMatchController {
    @Autowired
    private MatchAService matchAService;

    @Autowired
    private MatchBService matchBService;

    @Autowired
    private MatchSetService matchSetService;


    /**
     * 比赛A创建
     * @param matchADTO
     * @return
     */
    @ApiOperation(value = "比赛A创建")
    @PostMapping("/save-a")
    public Result saveMatchA(@RequestBody MatchADTO matchADTO) {
        log.info("比赛A创建:{}",matchADTO);
        matchAService.save(matchADTO);
        return Result.success();
    }
}
