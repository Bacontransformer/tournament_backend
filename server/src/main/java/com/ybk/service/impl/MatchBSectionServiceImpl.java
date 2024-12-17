package com.ybk.service.impl;

import com.ybk.mapper.MatchBSectionMapper;
import com.ybk.service.MatchBSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchBSectionServiceImpl implements MatchBSectionService {
    @Autowired
    private MatchBSectionMapper matchBSectionMapper;
}
