package com.ybk.controller.admin;

import com.ybk.dto.match.*;
import com.ybk.entity.*;
import com.ybk.mapper.*;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private MatchAMapper matchAMapper;

    @Autowired
    private MatchBMapper matchBMapper;

    @Autowired
    private RefereeMapper refereeMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private MatchModeMapper matchModeMapper;

    /**
     * 查询所有的team
     * @return
     */
    @ApiOperation(value = "查询所有的team")
    @GetMapping("/team-info")
    public Result<List<Team>> pageTeam() {
        log.info("查询所有的team");
        List<Team> teams = teamMapper.selectList(null);
        return Result.success(teams);
    }

    /**
     * 查询所有的referee
     * @return
     */
    @ApiOperation(value = "查询所有的referee")
    @GetMapping("/referee-info")
    public Result<List<Referee>> pageReferee() {
        log.info("查询所有的referee");
        List<Referee> referees = refereeMapper.selectList(null);
        return Result.success(referees);
    }


    /**
     * 比赛A条件分页查询
     * @param matchQueryDTO
     * @return
     */
    @ApiOperation(value = "比赛A条件分页查询")
    @PostMapping("/page-a")
    public Result<PageResult> pageMatchA(@RequestBody MatchQueryDTO matchQueryDTO) {
        log.info("比赛A条件分页查询");
        PageResult pageResult = matchAService.queryPage(matchQueryDTO);
        return Result.success(pageResult);
    }


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
     * 比赛A删除
     * @param matchAId
     * @return
     */
    @ApiOperation(value = "比赛A删除")
    @PostMapping("/delete-a")
    public Result deleteMatchA(Integer matchAId) {
        log.info("比赛A删除:{}",matchAId);
        matchAService.delete(matchAId);
        return Result.success();
    }

    /**
     * 根据ID查询某个比赛A
     * @param matchAId
     * @return
     */
    @ApiOperation(value = "根据id查询比赛A")
    @GetMapping("/match-a/{matchAId}")
    public Result<MatchA> getMatchA(@PathVariable Integer matchAId) {
        log.info("查询某一个比赛A:{}",matchAId);
        MatchA matchA = matchAMapper.selectById(matchAId);
        return Result.success(matchA);
    }

    /**
     *  分页查询比赛A模式
     * @param matchModeQueryDTO
     * @return
     */
    @ApiOperation(value = "比赛A各种模式分页查询")
    @PostMapping("/page-a-mode")
    public Result<PageResult> pageMatchAMode(@RequestBody MatchModeQueryDTO matchModeQueryDTO) {
        log.info("比赛A各种模式分页查询");
        PageResult pageResult = matchAService.pageMatchAMode(matchModeQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据matchModeId查询matchMode
     * @param matchModeId
     * @return
     */
    @ApiOperation(value = "根据id查询比赛A模式")
    @GetMapping("/match-a-mode/{matchModeId}")
    public Result<MatchMode> getMatchAMode(@PathVariable Integer matchModeId) {
        log.info("根据matchModeId查询matchMode:{}",matchModeId);
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        return Result.success(matchMode);
    }

    /**
     * 比赛A模式修改
     * @param matchAModeDTO
     * @return
     */
    @ApiOperation(value = "比赛A各种模式修改")
    @PostMapping("/update-a-mode")
    public Result updateMatchAMode(@RequestBody MatchAModeDTO matchAModeDTO) {
        log.info("比赛A模式修改:{}",matchAModeDTO);
        matchAService.updateMode(matchAModeDTO);
        return Result.success();
    }

    /**
     * 比赛A模式创建
     * @param matchAModeDTO
     * @return
     */
    @ApiOperation(value = "比赛A各种模式创建")
    @PostMapping("/save-a-mode")
    public Result saveMatchAMode(@RequestBody MatchAModeDTO matchAModeDTO) {
        log.info("比赛A模式创建:{}",matchAModeDTO);
        matchAService.saveMode(matchAModeDTO);
        return Result.success();
    }

    /**
     * 比赛A模式删除
     * @param matchModeId
     * @return
     */
    @ApiOperation(value = "比赛A模式删除")
    @PostMapping("/delete-a-mode")
    public Result deleteMatchAMode(Integer matchModeId) {
        log.info("比赛A模式删除:{}",matchModeId);
        matchAService.deleteMode(matchModeId);
        return Result.success();
    }

    /**
     * 根据ID查询某个比赛B
     * @param matchBId
     * @return
     */
    @ApiOperation(value = "根据id查询比赛B")
    @GetMapping("/match-b/{matchBId}")
    public Result<MatchB> getMatchB(@PathVariable Integer matchBId){
        log.info("查询某一个比赛B:{}",matchBId);
        MatchB matchB = matchBMapper.selectById(matchBId);
        return Result.success(matchB);
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
     * 比赛B删除
     * @param matchId
     * @return
     */
    @ApiOperation(value = "比赛B删除")
    @PostMapping("/delete-b")
    public Result deleteMatchB(Integer matchId) {
        log.info("比赛B删除:{}",matchId);
        matchBService.delete(matchId);
        return Result.success();
    }

    /**
     * 比赛B条件分页查询
     * @param matchQueryDTO
     * @return
     */
    @ApiOperation(value = "比赛B条件分页查询")
    @PostMapping("/page-b")
    public Result<PageResult> pageMatchB(@RequestBody MatchQueryDTO matchQueryDTO) {
        log.info("比赛B条件分页查询");
        PageResult pageResult = matchBService.queryPage(matchQueryDTO);
        return Result.success(pageResult);
    }
}
