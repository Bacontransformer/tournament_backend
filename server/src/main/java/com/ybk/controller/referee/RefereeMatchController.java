package com.ybk.controller.referee;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.BeginMatchDTO;
import com.ybk.dto.match.EndMatchDTO;
import com.ybk.dto.match.MatchAScoreDTO;
import com.ybk.dto.match.MatchBScoreDTO;
import com.ybk.entity.MatchB;
import com.ybk.entity.MatchMode;
import com.ybk.mapper.MatchBMapper;
import com.ybk.mapper.MatchModeMapper;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private MatchModeMapper matchModeMapper;

    @Autowired
    private MatchBMapper matchBMapper;

    /**
     * 根据id查看matchMode信息
     * @param matchModeId
     * @return
     */
    @ApiOperation(value = "根据id查看matchMode信息")
    @GetMapping("/get-a/{matchModeId}")
    public Result<MatchMode> getMatchModeById(@PathVariable Integer matchModeId){
        log.info("根据matchModeId查询某一matchMode:{}", matchModeId);
        return Result.success(matchModeMapper.selectById(matchModeId));
    }

    /**
     * 查看判分的matchMode信息
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "查看判分的所有matchMode信息")
    @PostMapping("/page-a")
    public Result<PageResult> getRefereeMatchMode(@RequestBody PageQueryDTO pageQueryDTO) {
        log.info("查看判分的matchMode信息");
        PageResult pageResult = matchAService.getRefereeMatchMode(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 对matchMode的某一局的分数变化
     * @param scoreDTO
     * @return
     */
    @ApiOperation(value = "对matchMode的某一局的分数变化")
    @PostMapping("/score-a")
    public Result matchAScore(@RequestBody MatchAScoreDTO scoreDTO) {
        log.info("对matchMode的某一局的分数变化");
        matchAService.matchAScore(scoreDTO);
        return Result.success();
    }

    /**
     * 宣布matchMode开始
     * @param matchModeId
     * @return
     */
    @ApiOperation(value = "宣布matchMode开始")
    @PostMapping("/begin-a/{matchModeId}")
    public Result beginMatchA(@PathVariable Integer matchModeId) {
        log.info("宣布matchMode开始");
        matchAService.beginMatchA(matchModeId);
        return Result.success();
    }

    /**
     * 对matchMode宣布比赛结束
     * @param matchModeId
     * @return
     */
    @ApiOperation(value = "对matchA宣布比赛结束")
    @PostMapping("/end-a/{matchModeId}")
    public Result endMatchA(@PathVariable Integer matchModeId) {
        log.info("对matchA宣布比赛结束");
        matchAService.endMatchA(matchModeId);
        return Result.success();
    }

    /**
     * 对matchB某一回合的分数变化
     * @param scoreDTO
     * @return
     */
    @ApiOperation(value = "对matchB某一回合的分数变化")
    @PostMapping("/score-b")
    public Result matchBScore(@RequestBody MatchBScoreDTO scoreDTO) {
        log.info("对matchB某一回合的分数变化");
        matchBService.matchBScore(scoreDTO);
        return Result.success();
    }

    /**
     * 根据id查看matchB信息
     * @param matchBId
     * @return
     */
    @ApiOperation(value = "根据id查看matchB信息")
    @GetMapping("/get-b/{matchBId}")
    public Result<MatchB> getMatchB(@PathVariable Integer matchBId){
        log.info("根据matchBId查询某一matchB:{}",matchBId);
        return Result.success(matchBMapper.selectById(matchBId));
    }

    /**
     * 查看判分的matchB信息
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "查看判分的matchB信息")
    @PostMapping("/page-b")
    public Result<PageResult> getRefereeMatchB(@RequestBody PageQueryDTO pageQueryDTO) {
        log.info("查看判分的matchB信息");
        PageResult pageResult = matchBService.getRefereeMatchB(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 宣布matchB开始
     * @param matchBId
     * @return
     */
    @ApiOperation(value = "宣布matchB开始")
    @PostMapping("/begin-b/{matchBId}")
    public Result beginMatchB(@PathVariable Integer matchBId) {
        log.info("宣布matchB开始");
        matchBService.beginMatchB(matchBId);
        return Result.success();
    }

    /**
     * 对matchB宣布比赛结束
     * @param matchBId
     * @return
     */
    @ApiOperation(value = "对matchB宣布比赛结束")
    @PostMapping("/end-b/{matchBId}")
    public Result endMatchB(@PathVariable Integer matchBId) {
        log.info("对matchB宣布比赛结束");
        matchBService.endMatchB(matchBId);
        return Result.success();
    }
}