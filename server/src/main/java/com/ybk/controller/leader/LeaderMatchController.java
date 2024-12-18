package com.ybk.controller.leader;

import com.ybk.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/leader-match")
@Api(tags = "leader比赛相关接口")
public class LeaderMatchController {
    @Autowired
    private MatchAService matchAService;

    @Autowired
    private MatchBService matchBService;

    @Autowired
    private MatchSetService matchSetService;

    @Autowired
    private RegistrationService registrationService;
}
