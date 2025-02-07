package com.ybk.controller.leader;

import com.ybk.dto.match.AssignmentDTO;
import com.ybk.dto.match.RegistrationDTO;
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

    @Autowired
    private MatchSetService matchSetService;

    /**
     * 设置MatchA上场球员
     * @param assignmentDTO
     * @return
     */
    @ApiOperation(value = "设置MatchA上场球员")
    @PostMapping("/set-match-a-player")
    public Result setMatchAPlayer(@RequestBody AssignmentDTO assignmentDTO){
        log.info("设置上场球员:{}", assignmentDTO);
        matchAService.setMatchAPlayer(assignmentDTO);
        return Result.success();
    }

    /**
     * 删除MatchA上场球员
     * @param assignmentId
     * @return
     */
    @ApiOperation(value = "删除MatchA上场球员")
    @PostMapping("/delete-match-a-player")
    public Result deleteMatchAPlayer(Long assignmentId){
        log.info("删除上场球员:{}", assignmentId);
        matchAService.deleteMatchAPlayer(assignmentId);
        return Result.success();
    }

    /**
     * 修改MatchA上场球员
     * @param assignmentDTO
     * @return
     */
    @ApiOperation(value = "修改MatchA上场球员")
    @PostMapping("/update-match-a-player")
    public Result updateMatchAPlayer(@RequestBody AssignmentDTO assignmentDTO){
        log.info("修改上场球员:{}", assignmentDTO);
        matchAService.updateMatchAPlayer(assignmentDTO);
        return Result.success();
    }

    /**
     * 设置MatchB上场球员
     * @param assignmentDTO
     * @return
     */
    @ApiOperation(value = "设置MatchB上场球员")
    @PostMapping("/set-match-b-player")
    public Result setMatchBPlayer(@RequestBody AssignmentDTO assignmentDTO){
        log.info("设置上场球员:{}", assignmentDTO);
        matchBService.setMatchBPlayer(assignmentDTO);
        return Result.success();
    }

    /**
     * 修改MatchB上场球员
     * @param assignmentDTO
     * @return
     */
    @ApiOperation(value = "修改MatchB上场球员")
    @PostMapping("/update-match-b-player")
    public Result updateMatchBPlayer(@RequestBody AssignmentDTO assignmentDTO){
        log.info("修改上场球员:{}", assignmentDTO);
        matchBService.updateMatchBPlayer(assignmentDTO);
        return Result.success();
    }

    /**
     * 删除MatchB上场球员
     * @param assignmentId
     * @return
     */
    @ApiOperation(value = "删除MatchB上场球员")
    @PostMapping("/delete-match-b-player")
    public Result deleteMatchBPlayer(Long assignmentId){
        log.info("删除上场球员:{}", assignmentId);
        matchBService.deleteMatchBPlayer(assignmentId);
        return Result.success();
    }

}
