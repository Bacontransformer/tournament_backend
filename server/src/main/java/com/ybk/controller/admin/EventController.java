package com.ybk.controller.admin;

import com.ybk.service.EventService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/event")
@Api(tags = "活动相关接口")
public class EventController {
    @Autowired
    private EventService eventService;
}
