package com.ybk.service.impl;

import com.ybk.mapper.MatchMapper;
import com.ybk.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    private MatchMapper matchMapper;
}
