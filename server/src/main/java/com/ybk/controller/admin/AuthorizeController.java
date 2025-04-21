package com.ybk.controller.admin;

import com.ybk.dto.PageQueryDTO;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.LeaderService;
import com.ybk.service.RefereeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/authorize")
@Api(tags = "审批相关接口")
public class AuthorizeController {
    @Autowired
    private LeaderService leaderService;

    @Autowired
    private RefereeService refereeService;

    /**
     * 分页查询领队
     *
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "分页查询待审批的领队信息")
    @PostMapping("/leader/page")
    public Result<PageResult> pageLeader(@RequestBody PageQueryDTO pageQueryDTO) {
        log.info("分页查询待审批的领队信息");
        PageResult pageResult = leaderService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 通过领队
     *
     * @param leaderId
     * @return
     */
    @ApiOperation(value = "通过待审批的领队信息")
    @PostMapping("/leader/pass/{leaderId}")
    public Result passLeader(@PathVariable Integer leaderId) {
        log.info("通过待审批的领队信息");
        leaderService.passLeader(leaderId);
        return Result.success();
    }

    /**
     * 通过裁判
     *
     * @param refereeId
     * @return
     */
    @ApiOperation(value = "通过待审批的裁判信息")
    @PostMapping("/referee/pass/{refereeId}")
    public Result passReferee(@PathVariable Integer refereeId) {
        log.info("通过待审批的裁判信息:{}",refereeId);
        refereeService.passReferee(refereeId);
        return Result.success();
    }

    /**
     * 分页查询裁判
     *
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "分页查询待审批的裁判信息")
    @PostMapping("/referee/page")
    public Result<PageResult> pageReferee(@RequestBody PageQueryDTO pageQueryDTO) {
        log.info("分页查询待审批的裁判信息");
        PageResult pageResult = refereeService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }
}
