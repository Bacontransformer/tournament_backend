package com.ybk.controller.admin;

import com.ybk.dto.match.MatchADTO;
import com.ybk.dto.match.MatchBDTO;
import com.ybk.dto.match.RegistrationPageDTO;
import com.ybk.result.PageResult;
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

    /**
     * 比赛B创建
     * @param matchBDTO
     * @return
     */
    @ApiOperation(value = "比赛B创建")
    @PostMapping("/save-b")
    public Result saveMatchB(@RequestBody MatchBDTO matchBDTO) {
        log.info("比赛B创建:{}",matchBDTO);
        matchBService.save(matchBDTO);
        return Result.success();
    }

    /**
     * 比赛A修改
     * @param matchADTO
     * @return
     */
    @ApiOperation(value = "比赛A修改")
    @PostMapping("/update-a")
    public Result updateMatchA(@RequestBody MatchADTO matchADTO) {
        log.info("比赛A修改:{}",matchADTO);
        matchAService.update(matchADTO);
        return Result.success();
    }

    /**
     * 比赛B修改
     * @param matchBDTO
     * @return
     */
    @ApiOperation(value = "比赛B修改")
    @PostMapping("/update-b")
    public Result updateMatchB(@RequestBody MatchBDTO matchBDTO) {
        log.info("比赛B修改:{}",matchBDTO);
        matchBService.update(matchBDTO);
        return Result.success();
    }

    /**
     * 比赛A删除
     * @param matchId
     * @return
     */
    @ApiOperation(value = "比赛A删除")
    @PostMapping("/delete-a")
    public Result deleteMatchA(@RequestBody Long matchId) {
        log.info("比赛A删除:{}",matchId);
        matchAService.delete(matchId);
        return Result.success();
    }

    /**
     * 比赛B删除
     * @param matchId
     * @return
     */
    @ApiOperation(value = "比赛B删除")
    @PostMapping("/delete-b")
    public Result deleteMatchB(@RequestBody Long matchId) {
        log.info("比赛B删除:{}",matchId);
        matchBService.delete(matchId);
        return Result.success();
    }

    /**
     * 查询报名的参赛队伍
     * @param registrationPageDTO
     * @return
     */
    @ApiOperation(value = "查询报名的参赛队伍")
    @PostMapping("/page-team")
    public Result<PageResult> pageTeam(@RequestBody RegistrationPageDTO registrationPageDTO) {
        log.info("查询报名的参赛队伍:{}", registrationPageDTO);
        PageResult pageResult = matchAService.pageTeam(registrationPageDTO);
        return Result.success(pageResult);
    }
}
