package com.ybk.controller.visitor;

import com.ybk.constant.StatusConstant;
import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.VisitorQueryDTO;
import com.ybk.entity.MatchA;
import com.ybk.entity.MatchB;
import com.ybk.entity.MatchMode;
import com.ybk.exception.BaseException;
import com.ybk.mapper.MatchBMapper;
import com.ybk.mapper.MatchModeMapper;
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
    private MatchModeMapper matchModeMapper;

    @Autowired
    private MatchBService matchBService;

    @Autowired
    private MatchBMapper matchBMapper;

    //***********************************************************************************************

    /**
     * 某个 DOING MatchMode
     * @param matchModeId
     * @return
     */
    @GetMapping("/match-a/doing/{matchModeId}")
    public Result<MatchMode> getDoingMatchADetail(@PathVariable Integer matchModeId) {
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if (!matchMode.getStatus().equals(StatusConstant.DOING)) {
            throw new BaseException("查无此比赛");
        }
        return Result.success(matchMode);
    }

    /**
     * 分页 DOING MatchMode
     * @param visitorQueryDTO
     * @return
     */
    @PostMapping("/match-a/doing/page")
    public Result<PageResult> getDoingMatchABrief(@RequestBody VisitorQueryDTO visitorQueryDTO) {
        PageResult pageResult = matchAService.getDoingMatchABrief(visitorQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 某个 UNSTART MatchMode
     * @param matchModeId
     * @return
     */
    @GetMapping("/match-a/un-start/{matchModeId}")
    public Result<MatchMode> getUnStartMatchADetail(@PathVariable Integer matchModeId) {
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if (!matchMode.getStatus().equals(StatusConstant.UNSTART)) {
            throw new BaseException("查无此比赛");
        }
        return Result.success(matchMode);
    }

    /**
     * 分页 UNSTART MatchMode
     * @return
     */
    @PostMapping("/match-a/un-start/page")
    public Result<PageResult> getUnStartMatchABrief(@RequestBody VisitorQueryDTO visitorQueryDTO) {
        PageResult pageResult = matchAService.getUnStartMatchABrief(visitorQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 某个 END MatchMode
     * @param matchModeId
     * @return
     */
    @GetMapping("/match-a/end/{matchModeId}")
    public Result<MatchMode> getEndMatchADetail(@PathVariable Integer matchModeId) {
        MatchMode matchMode = matchModeMapper.selectById(matchModeId);
        if (!matchMode.getStatus().equals(StatusConstant.END)) {
            throw new BaseException("查无此比赛");
        }
        return Result.success(matchMode);
    }

    /**
     * 分页 END MatchMode
     * @param visitorQueryDTO
     * @return
     */
    @PostMapping("/match-a/end/page")
    public Result<PageResult> getEndMatchABrief(@RequestBody VisitorQueryDTO visitorQueryDTO) {
        PageResult pageResult = matchAService.getEndMatchABrief(visitorQueryDTO);
        return Result.success(pageResult);
    }

    //************************************************************************************************

    /**
     * 某个 DOING MatchB
     * @param matchBId
     * @return
     */
    @GetMapping("/match-b/doing/{matchBId}")
    public Result<MatchB> getDoingMatchBDetail(@PathVariable Integer matchBId) {
        MatchB matchB = matchBMapper.selectById(matchBId);
        if (!matchB.getStatus().equals(StatusConstant.DOING)) {
            throw new BaseException("查无此比赛");
        }
        return Result.success(matchB);
    }

    /**
     * 分页 DOING MatchB
     * @param visitorQueryDTO
     * @return
     */
    @PostMapping("/match-b/doing/page")
    public Result<PageResult> getDoingMatchBBrief(@RequestBody VisitorQueryDTO visitorQueryDTO) {
        PageResult pageResult = matchBService.getDoingMatchBBrief(visitorQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 某个 UNSTART MatchB
     * @param matchBId
     * @return
     */
    @GetMapping("/match-b/un-start/{matchBId}")
    public Result<MatchB> getUnStartMatchBDetail(@PathVariable Integer matchBId) {
        MatchB matchB = matchBMapper.selectById(matchBId);
        if (!matchB.getStatus().equals(StatusConstant.UNSTART)) {
            throw new BaseException("查无此比赛");
        }
        return Result.success(matchB);
    }

    /**
     * 分页 UNSTART MatchB
     * @param visitorQueryDTO
     * @return
     */
    @PostMapping("/match-b/un-start/page")
    public Result<PageResult> getUnStartMatchBBrief(@RequestBody VisitorQueryDTO visitorQueryDTO) {
        PageResult pageResult = matchBService.getUnStartMatchBBrief(visitorQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 某个 END MatchB
     * @param matchBId
     * @return
     */
    @GetMapping("/match-b/end/{matchBId}")
    public Result<MatchB> getEndMatchBDetail(@PathVariable Integer matchBId) {
        MatchB matchB = matchBMapper.selectById(matchBId);
        if (!matchB.getStatus().equals(StatusConstant.END)) {
            throw new BaseException("查无此比赛");
        }
        return Result.success(matchB);
    }

    /**
     * 分页 END MatchB
     * @param visitorQueryDTO
     * @return
     */
    @PostMapping("/match-b/end/page")
    public Result<PageResult> getEndMatchBBrief(@RequestBody VisitorQueryDTO visitorQueryDTO) {
        PageResult pageResult = matchBService.getEndMatchBBrief(visitorQueryDTO);
        return Result.success(pageResult);
    }
}