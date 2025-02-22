package com.ybk.service;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.*;
import com.ybk.entity.MatchB;
import com.ybk.result.PageResult;

public interface MatchBService {
    void save(MatchBDTO matchBDTO);

    void update(MatchBDTO matchBDTO);

    void delete(Long matchId);

    void setMatchBPlayer(MatchBPlayerDTO matchBPlayerDTO);

    MatchB getDoingMatchBDetail(Long matchBId);

    PageResult getUnStartMatchBBrief(PageQueryDTO pageQueryDTO);

    PageResult getDoingMatchBBrief(PageQueryDTO pageQueryDTO);

    void endMatchB(Long matchBId);

    void beginMatchB(Long matchBId);

    PageResult getRefereeMatchB(PageQueryDTO pageQueryDTO);

    void matchBScore(MatchBScoreDTO scoreDTO);
}
