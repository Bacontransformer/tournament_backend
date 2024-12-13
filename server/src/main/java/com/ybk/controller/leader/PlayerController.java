package com.ybk.controller.leader;

import com.ybk.service.PlayerService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/player")
@Api(tags = "队员相关接口")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
}
