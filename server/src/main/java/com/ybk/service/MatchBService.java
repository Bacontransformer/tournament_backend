package com.ybk.service;

import com.ybk.dto.match.MatchBDTO;

public interface MatchBService {
    void save(MatchBDTO matchBDTO);

    void update(MatchBDTO matchBDTO);

    void delete(Long matchId);
}
