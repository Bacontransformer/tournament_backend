package com.ybk.service.impl;

import com.ybk.mapper.TeamRegistrationMapper;
import com.ybk.service.TeamRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamRegistrationServiceImpl implements TeamRegistrationService {
    @Autowired
    private TeamRegistrationMapper teamRegistrationMapper;
}
