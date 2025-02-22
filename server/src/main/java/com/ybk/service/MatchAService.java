package com.ybk.service;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.*;
import com.ybk.entity.MatchA;
import com.ybk.entity.MatchMode;
import com.ybk.result.PageResult;

public interface MatchAService {
    void save(MatchADTO matchADTO);

    void update(MatchADTO matchADTO);

    void delete(Long matchId);

    void setMatchAPlayer(MatchAPlayerDTO matchAPlayerDTO);

    void deleteMatchAPlayer(ClearMatchAPlayerDTO clearMatchAPlayerDTO);

    MatchMode getDoingMatchADetail(Long matchModeId);

    PageResult getUnStartMatchABrief(PageQueryDTO pageQueryDTO);

    PageResult getDoingMatchABrief(PageQueryDTO pageQueryDTO);

    void endMatchA(Long matchModeId);

    PageResult getRefereeMatchMode(PageQueryDTO pageQueryDTO);

    void beginMatchA(Long matchModeId);

    void matchAScore(MatchAScoreDTO scoreDTO);

    void saveMode(MatchAModeDTO matchAModeDTO);

    void deleteMode(Long matchModeId);
}
