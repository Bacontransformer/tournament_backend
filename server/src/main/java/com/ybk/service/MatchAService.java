package com.ybk.service;

import com.ybk.dto.match.MatchADTO;

public interface MatchAService {
    void save(MatchADTO matchADTO);

    void update(MatchADTO matchADTO);

    void delete(Long matchId);
}
