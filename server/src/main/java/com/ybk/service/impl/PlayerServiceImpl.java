package com.ybk.service.impl;

import com.ybk.context.BaseContext;
import com.ybk.dto.PlayerDTO;
import com.ybk.entity.Leader;
import com.ybk.entity.Player;
import com.ybk.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerService playerService;

    @Override
    public void save(PlayerDTO playerDTO) {
        Player player = new Player();
        Long leaderId =  BaseContext.getCurrentId();
        player.setName(playerDTO.getName());
    }
}
