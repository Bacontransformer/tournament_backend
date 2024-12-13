package com.ybk.controller.admin;

import com.ybk.dto.AuthorizeQueryDTO;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.LeaderService;
import com.ybk.service.RefereeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value="分页查询待审批的领队信息")
    @PostMapping("/page-leader")
    public Result<PageResult> pageLeader(@RequestBody AuthorizeQueryDTO authorizeQueryDTO){
        log.info("分页查询待审批的领队信息");
        PageResult pageResult = leaderService.pageQuery(authorizeQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation(value="分页查询待审批的裁判信息")
    @PostMapping("/page-referee")
    public Result<PageResult> pageReferee(@RequestBody AuthorizeQueryDTO authorizeQueryDTO){
        log.info("分页查询待审批的裁判信息");
        PageResult pageResult = refereeService.pageQuery(authorizeQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation(value="通过待审批的裁判信息")
    @PostMapping("/pass-referee")
    public Result<PageResult> passReferee(@RequestBody List<Integer> ids){
        log.info("通过待审批的裁判信息");
        refereeService.passReferee(ids);
        return Result.success();
    }

    @ApiOperation(value="通过待审批的领队信息")
    @PostMapping("/pass-leader")
    public Result<PageResult> passLeader(@RequestBody List<Integer> ids){
        log.info("通过待审批的领队信息");
        leaderService.passLeader(ids);
        return Result.success();
    }
}
