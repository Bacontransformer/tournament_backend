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
     * 查看判分的matchA简略信息
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("referee-match-a-brief")
    public Result<PageResult> getRefereeMatchABrief(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchAService.getRefereeMatchABrief(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 查看判分的matchB简略信息
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("referee-match-b-brief")
    public Result<PageResult> getRefereeMatchBBrief(@RequestBody PageQueryDTO pageQueryDTO) {
        PageResult pageResult = matchBService.getRefereeMatchBBrief(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 对matchA某一模式的某一轮的分数变化
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
     * 宣布matchA开始
     * @param beginMatchDTO
     * @return
     */
    @PostMapping("/begin-matchA")
    public Result beginMatchA(@RequestBody BeginMatchDTO beginMatchDTO) {
        matchAService.beginMatchA(beginMatchDTO);
        return Result.success();
    }

    /**
     * 宣布matchB开始
     * @param beginMatchDTO
     * @return
     */
    @PostMapping("/begin-matchB")
    public Result beginMatchB(@RequestBody BeginMatchDTO beginMatchDTO) {
        matchBService.beginMatchB(beginMatchDTO);
        return Result.success();
    }

    /**
     * 对matchA宣布比赛结束
     * @param endMatchDTO
     * @return
     */
    @PostMapping("/end-matchA")
    public Result endMatchA(@RequestBody EndMatchDTO endMatchDTO) {
        matchAService.endMatchA(endMatchDTO);
        return Result.success();
    }

    /**
     * 对matchB宣布比赛结束
     * @param endMatchDTO
     * @return
     */
    @PostMapping("/end-matchB")
    public Result endMatchB(@RequestBody EndMatchDTO endMatchDTO) {
        matchBService.endMatchB(endMatchDTO);
        return Result.success();
    }
}
