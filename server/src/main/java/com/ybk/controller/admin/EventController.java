package com.ybk.controller.admin;

import com.ybk.dto.match.EventDTO;
import com.ybk.dto.PageQueryDTO;
import com.ybk.entity.Event;
import com.ybk.mapper.EventMapper;
import com.ybk.result.PageResult;
import com.ybk.result.Result;
import com.ybk.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/event")
@Api(tags = "活动相关接口")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    /**
     * 活动创建
     *
     * @param eventDTO
     * @return
     */
    @ApiOperation(value = "活动创建")
    @PostMapping("/save")
    public Result saveEvent(@RequestBody EventDTO eventDTO) {
        log.info("活动创建:{}", eventDTO);
        eventService.save(eventDTO);
        return Result.success();
    }

    /**
     * 活动修改
     *
     * @param eventDTO
     * @return
     */
    @ApiOperation(value = "活动修改")
    @PostMapping("/update")
    public Result updateEvent(@RequestBody EventDTO eventDTO) {
        log.info("活动修改:{}", eventDTO);
        eventService.update(eventDTO);
        return Result.success();
    }

    @ApiOperation(value = "查询某一id的活动详情")
    @GetMapping("/{eventId}")
    public Result<Event> getEvent(@PathVariable Integer eventId) {
        log.info("查询某一id的活动详情:{}", eventId);
        Event event = eventMapper.selectById(eventId);
        return Result.success(event);
    }

    /**
     * 活动删除
     * @param id
     * @return
     */
    @ApiOperation(value = "活动删除")
    @PostMapping("/delete")
    public Result deleteEvent( Integer id) {
        log.info("活动删除:{}", id);
        eventService.delete(id);
        return Result.success();
    }

    /**
     * 分页查询所有活动信息
     * @param pageQueryDTO
     * @return
     */
    @ApiOperation(value = "分页查询活动信息")
    @PostMapping("/page-event")
    public Result<PageResult> pageEvent(@RequestBody PageQueryDTO pageQueryDTO) {
        log.info("分页查询所有活动信息");
        PageResult pageResult = eventService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }
}
