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

    /**
     * 分页查询领队
     *
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "分页查询待审批的领队信息")
    @PostMapping("/page-leader")
    public Result<PageResult> pageLeader(@RequestBody PageQueryDTO pageQueryDTO) {
        log.info("分页查询待审批的领队信息");
        PageResult pageResult = leaderService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 分页查询裁判
     *
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "分页查询待审批的裁判信息")
    @PostMapping("/page-referee")
    public Result<PageResult> pageReferee(@RequestBody PageQueryDTO pageQueryDTO) {
        log.info("分页查询待审批的裁判信息");
        PageResult pageResult = refereeService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量通过裁判
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "通过待审批的裁判信息")
    @PostMapping("/pass-referee")
    public Result passReferee(@RequestBody List<Long> ids) {
        log.info("通过待审批的裁判信息");
        refereeService.passReferee(ids);
        return Result.success();
    }

    /**
     * 批量通过领队
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "通过待审批的领队信息")
    @PostMapping("/pass-leader")
    public Result passLeader(@RequestBody List<Long> ids) {
        log.info("通过待审批的领队信息");
        leaderService.passLeader(ids);
        return Result.success();
    }
}
