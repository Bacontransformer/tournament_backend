package com.ybk.service.impl;

import com.ybk.entity.Team;
import com.ybk.mapper.TeamMapper;
import com.ybk.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamMapper teamMapper;
}
