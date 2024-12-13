package com.ybk.controller.admin;

import com.ybk.service.MatchService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/match")
@Api(tags = "比赛相关接口")
public class MatchController {
    @Autowired
    private MatchService matchService;
}
