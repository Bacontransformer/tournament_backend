package com.ybk.controller.leader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ybk.context.BaseContext;
import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.ClearMatchAPlayerDTO;
import com.ybk.dto.match.ClearMatchBPlayerDTO;
import com.ybk.dto.match.MatchAPlayerDTO;
import com.ybk.dto.match.MatchBPlayerDTO;
import com.ybk.entity.MatchB;
import com.ybk.entity.MatchMode;
import com.ybk.entity.Player;
import com.ybk.mapper.MatchModeMapper;
import com.ybk.mapper.PlayerMapper;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.*;
import com.ybk.vo.LeaderMatchBVO;
import com.ybk.vo.LeaderMatchModeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/leader-match")
@Api(tags = "leader比赛相关接口")
public class LeaderMatchController {
    @Autowired
    private MatchAService matchAService;

    @Autowired
    private MatchBService matchBService;

    @Autowired
    private PlayerMapper playerMapper;

    /**
     * 查询所有球员
     * @return
     */
    @ApiOperation(value = "查询所有球员")
    @GetMapping("/player-info")
    public Result<List<Player>> pagePlayer() {
        log.info("查询所有球员");
        QueryWrapper<Player> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("leader_id", BaseContext.getCurrentId());
        return Result.success(playerMapper.selectList(queryWrapper));
    }

    /**
     * 分页查询该领队的match-mode
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "分页查询该领队的match-mode")
    @PostMapping("/page-a")
    public Result<PageResult> leaderPageMatchMode(@RequestBody PageQueryDTO pageQueryDTO){
        log.info("分页查询该领队的match-mode:{}", pageQueryDTO);
        return Result.success(matchAService.leaderPageMatchMode(pageQueryDTO));
    }

    /**
     * 根据matchModeId查询某一matchMode
     * @param matchModeId
     * @return
     */
    @ApiOperation(value = "根据matchModeId查询某一matchMode")
    @PostMapping("/get-a/{matchModeId}")
    public Result<LeaderMatchModeVO> getMatchMode(@PathVariable Integer matchModeId){
        log.info("根据matchModeId查询某一matchMode:{}", matchModeId);
        return Result.success(matchAService.LeaderGetMatchMode(matchModeId));
    }

    /**
     * 设置MatchA上场球员
     * @param matchAPlayerDTO
     * @return
     */
    @ApiOperation(value = "设置MatchA上场球员")
    @PostMapping("/set-a")
    public Result setMatchAPlayer(@RequestBody MatchAPlayerDTO matchAPlayerDTO){
        log.info("设置上场球员:{}", matchAPlayerDTO);
        matchAService.setMatchAPlayer(matchAPlayerDTO);
        return Result.success();
    }

    /**
     * 清空MatchA某一模式上场球员
     * @param clearMatchAPlayerDTO
     * @return
     */
    @ApiOperation(value = "清空MatchA上场球员")
    @PostMapping("/delete-a")
    public Result deleteMatchAPlayer(@RequestBody ClearMatchAPlayerDTO clearMatchAPlayerDTO){
        log.info("删除上场球员:{}", clearMatchAPlayerDTO);
        matchAService.deleteMatchAPlayer(clearMatchAPlayerDTO);
        return Result.success();
    }

    /**
     * 设置MatchB上场球员
     * @param matchBPlayerDTO
     * @return
     */
    @ApiOperation(value = "设置MatchB上场球员")
    @PostMapping("/set-b")
    public Result setMatchBPlayer(@RequestBody MatchBPlayerDTO matchBPlayerDTO){
        log.info("设置上场球员:{}", matchBPlayerDTO);
        matchBService.setMatchBPlayer(matchBPlayerDTO);
        return Result.success();
    }

    /**
     * 分页查询分页查询该领队的matchB
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "分页查询该领队的matchB")
    @PostMapping("/page-b")
    public Result<PageResult> leaderPageMatchB(@RequestBody PageQueryDTO pageQueryDTO){
        log.info("分页查询该领队的matchB:{}", pageQueryDTO);
        return Result.success(matchBService.leaderPageMatchB(pageQueryDTO));
    }

    /**
     * 删除matchB上场球员
     * @param clearMatchBPlayerDTO
     * @return
     */
    @ApiOperation(value = "删除matchB上场球员")
    @PostMapping("/delete-b")
    public Result deleteMatchBPlayer(@RequestBody ClearMatchBPlayerDTO clearMatchBPlayerDTO){
        log.info("删除上场球员:{}", clearMatchBPlayerDTO);
        matchBService.deleteMatchBPlayer(clearMatchBPlayerDTO);
        return Result.success();
    }

    /**
     * 根据matchBId查询某一matchB
     * @param matchBId
     * @return
     */
    @ApiOperation(value = "根据matchBId查询某一matchB")
    @GetMapping("/get-b/{matchBId}")
    public Result<LeaderMatchBVO> getMatchB(@PathVariable Integer matchBId){
        log.info("根据matchBId查询某一matchB:{}", matchBId);
        return Result.success(matchBService.leaderGetMatchB(matchBId));
    }
}
