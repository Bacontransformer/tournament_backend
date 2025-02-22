package com.ybk.controller;

import com.ybk.dto.PageQueryDTO;
import com.ybk.entity.MatchA;
import com.ybk.entity.MatchB;
import com.ybk.entity.MatchMode;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.MatchAService;
import com.ybk.service.MatchBService;
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

    @Autowired
    private MatchBService matchBService;

    /**
     * 获取DOING的某个MatchMode的比赛信息
     * @param matchModeId
     * @return
     */
    @GetMapping("/matchA-detail/{matchAId}")
    public Result<MatchMode> getDoingMatchADetail(@RequestBody Long matchModeId) {
        return Result.success(matchAService.getDoingMatchADetail(matchModeId));
    }

    /**
     * 获取所有UNSTART的MatchMode的信息
     * @return
     */
    @GetMapping("/un-start-matchA-brief")
    public Result<PageResult> getMatchABrief(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchAService.getUnStartMatchABrief(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取所有DOING的MatchA的信息
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("/doing-matchA-brief")
    public Result<PageResult> getDoingMatchABrief(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchAService.getDoingMatchABrief(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取DOING的某个MatchB的详细比赛信息
     * @param matchBId
     * @return
     */
    @GetMapping("/matchB-detail/{matchBId}")
    public Result<MatchB> getDoingMatchBDetail(@PathVariable Long matchBId) {
        return Result.success(matchBService.getDoingMatchBDetail(matchBId));
    }

    /**
     * 获取所有UNSTART的MatchB的简略信息
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("/un-start-matchB-brief")
    public Result<PageResult> getMatchBBrief(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchBService.getUnStartMatchBBrief(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取所有DOING的MatchB的简略信息
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("/doing-matchB-brief")
    public Result<PageResult> getDoingMatchBBrief(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchBService.getDoingMatchBBrief(pageQueryDTO);
        return Result.success(pageResult);
    }
}
