package com.ybk.controller.leader;

import com.ybk.service.TeamService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/team")
@Api(tags = "队伍相关接口")
public class TeamController {
    @Autowired
    private TeamService teamService;
}
