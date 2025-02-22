package com.ybk.controller.leader;

import com.ybk.dto.match.AssignmentDTO;
import com.ybk.dto.match.ClearMatchAPlayerDTO;
import com.ybk.dto.match.MatchAPlayerDTO;
import com.ybk.dto.match.MatchBPlayerDTO;
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
@RequestMapping("/leader-match")
@Api(tags = "leader比赛相关接口")
public class LeaderMatchController {
    @Autowired
    private MatchAService matchAService;

    @Autowired
    private MatchBService matchBService;

    /**
     * 设置MatchA上场球员
     * @param matchAPlayerDTO
     * @return
     */
    @ApiOperation(value = "设置MatchA上场球员")
    @PostMapping("/set-match-a-player")
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
    @ApiOperation(value = "删除MatchA上场球员")
    @PostMapping("/delete-match-a-player")
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
    @PostMapping("/set-match-b-player")
    public Result setMatchBPlayer(@RequestBody MatchBPlayerDTO matchBPlayerDTO){
        log.info("设置上场球员:{}", matchBPlayerDTO);
        matchBService.setMatchBPlayer(matchBPlayerDTO);
        return Result.success();
    }

}
