package com.ybk.service.impl;

import com.ybk.mapper.MatchSetMapper;
import com.ybk.service.MatchSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchSetServiceImpl implements MatchSetService {
    @Autowired
    private MatchSetMapper matchSetMapper;
}
