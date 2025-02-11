package com.ybk.controller;

import com.ybk.dto.PageQueryDTO;
import com.ybk.entity.MatchA;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.MatchAService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/common")
@Api(tags = "公共信息相关接口")
public class CommonController {

    @Autowired
    private MatchAService matchAService;

    /**
     * 获取"进行中"的某个MatchA的详细比赛信息
     * @param matchAId
     * @return
     */
    @GetMapping("/matchA-detail/{matchAId}")
    public Result<MatchA> getDoingMatchADetail(@PathVariable Long matchAId) {
        return Result.success(matchAService.getDoingMatchADetail(matchAId));
    }

    /**
     * 获取所有未开始的MatchA的简略信息
     * @return
     */
    @GetMapping("/un-start-matchA-brief")
    public Result<PageResult> getMatchABrief(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchAService.getUnStartMatchABrief(pageQueryDTO);
        return Result.success(pageResult);
    }

    // 获取所有正在进行的MatchA的简略信息
    @GetMapping("/doing-matchA-brief")
    public Result<PageResult> getDoingMatchABrief(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchAService.getDoingMatchABrief(pageQueryDTO);
        return Result.success(pageResult);
    }


}
