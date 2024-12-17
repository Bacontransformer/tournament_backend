package com.ybk.service.impl;

import com.ybk.service.MatchBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchBServiceImpl implements MatchBService {
    @Autowired
    private MatchBService matchBService;
}
