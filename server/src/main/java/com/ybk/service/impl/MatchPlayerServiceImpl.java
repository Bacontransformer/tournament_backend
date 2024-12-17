package com.ybk.service.impl;

import com.ybk.mapper.MatchPlayerMapper;
import com.ybk.service.MatchPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchPlayerServiceImpl implements MatchPlayerService {
    @Autowired
    private MatchPlayerMapper matchPlayerMapper;
}
