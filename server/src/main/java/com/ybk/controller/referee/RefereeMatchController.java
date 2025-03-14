package com.ybk.controller.referee;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.BeginMatchDTO;
import com.ybk.dto.match.EndMatchDTO;
import com.ybk.dto.match.MatchAScoreDTO;
import com.ybk.dto.match.MatchBScoreDTO;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.*;
import io.swagger.annotations.Api;
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

    /**
     * 查看判分的matchMode信息
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("referee-match-mode")
    public Result<PageResult> getRefereeMatchMode(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchAService.getRefereeMatchMode(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 查看判分的matchB信息
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("referee-match-b")
    public Result<PageResult> getRefereeMatchB(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchBService.getRefereeMatchB(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 对matchMode的某一局的分数变化
     * @param scoreDTO
     * @return
     */
    @PostMapping("/match-a-score")
    public Result matchAScore(@RequestBody MatchAScoreDTO scoreDTO) {
        matchAService.matchAScore(scoreDTO);
        return Result.success();
    }

    /**
     * 对matchB某一回合的分数变化
     * @param scoreDTO
     * @return
     */
    @PostMapping("/match-b-score")
    public Result matchBScore(@RequestBody MatchBScoreDTO scoreDTO) {
        matchBService.matchBScore(scoreDTO);
        return Result.success();
    }

    /**
     * 宣布matchMode开始
     * @param matchModeId
     * @return
     */
    @PostMapping("/begin-matchA")
    public Result beginMatchA(@RequestBody Integer matchModeId) {
        matchAService.beginMatchA(matchModeId);
        return Result.success();
    }

    /**
     * 宣布matchB开始
     * @param matchBId
     * @return
     */
    @PostMapping("/begin-matchB")
    public Result beginMatchB(@RequestBody Integer matchBId) {
        matchBService.beginMatchB(matchBId);
        return Result.success();
    }

    /**
     * 对matchA宣布比赛结束
     * @param matchModeId
     * @return
     */
    @PostMapping("/end-matchA")
    public Result endMatchA(@RequestBody Integer matchModeId) {
        matchAService.endMatchA(matchModeId);
        return Result.success();
    }

    /**
     * 对matchB宣布比赛结束
     * @param matchBId
     * @return
     */
    @PostMapping("/end-matchB")
    public Result endMatchB(@RequestBody Integer matchBId) {
        matchBService.endMatchB(matchBId);
        return Result.success();
    }
}
