package com.ybk.service.impl;

import com.ybk.dto.match.MatchADTO;
import com.ybk.entity.MatchA;
import com.ybk.mapper.MatchAMapper;
import com.ybk.service.MatchAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchAServiceImpl implements MatchAService {
    @Autowired
    private MatchAMapper matchAMapper;

    @Override
    public void save(MatchADTO matchADTO) {

        MatchA matchA = MatchA.builder()
                .roundCount(matchADTO.getRoundCount())
                .winScore(matchADTO.getWinScore())
                .build();

    }
}
