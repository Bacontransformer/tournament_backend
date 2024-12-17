package com.ybk.controller.referee;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/score")
@Api(tags = "计分相关接口")
public class ScoreController {
    @Autowired
    private MatchService matchService;
}
