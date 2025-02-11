package com.ybk.controller.referee;

import com.ybk.entity.MatchA;
import com.ybk.result.Result;
import com.ybk.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/referee-match")
@Api(tags = "referee比赛相关接口")
public class RefereeMatchController {
    @Autowired
    private MatchAService matchAService;

    @Autowired
    private MatchBService matchBService;

    @Autowired
    private MatchSetService matchSetService;

    // 查看自己审理的所有比赛，按照时间从近到远进行排序
//    @GetMapping("/a/{refereeId}")
//    @ApiOperation(value = "查看审理的matchA")
//    public Result<MatchA> getRefereeMatchA(@PathVariable Long refereeId) {
//        return Result.success(matchAService.getRefereeMatchA(refereeId));
//    }

}
