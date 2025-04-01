package com.ybk.controller.leader;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.role.PlayerDTO;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/player")
@Api(tags = "队员相关接口")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    /**
     * 分页查询所有队员信息
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "分页查询所有队员信息")
    @GetMapping("/page-player")
    public Result<PageResult> pagePlayer(@RequestBody PageQueryDTO pageQueryDTO) {
        log.info("分页查询所有队员信息");
        PageResult pageResult = playerService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增球员
     *
     * @param playerDTO
     * @return
     */
    @ApiOperation("队员创建")
    @PostMapping("/save")
    public Result savePlayer(@RequestBody PlayerDTO playerDTO) {
        log.info("队员创建：{}", playerDTO);
        playerService.save(playerDTO);
        return Result.success();
    }

    /**
     * 修改球员
     *
     * @param playerDTO
     * @return
     */
    @ApiOperation("队员修改")
    @PostMapping("/update")
    public Result updatePlayer(@RequestBody PlayerDTO playerDTO) {
        log.info("队员修改：{}", playerDTO);
        playerService.update(playerDTO);
        return Result.success();
    }

    /**
     * 批量删除球员
     *
     * @param ids
     * @return
     */
    @ApiOperation("队员删除")
    @PostMapping("/delete")
    public Result deletePlayer(List<Integer> ids) {
        log.info("队员删除：{}", ids);
        playerService.delete(ids);
        return Result.success();
    }
}
