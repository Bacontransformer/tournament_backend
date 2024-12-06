package com.ybk.service.impl;

import com.ybk.mapper.LeaderMapper;
import com.ybk.service.LeaderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaderManageServiceImpl implements LeaderManageService {
    @Autowired
    private LeaderMapper leaderMapper;
}
