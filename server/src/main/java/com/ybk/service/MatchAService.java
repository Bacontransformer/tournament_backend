package com.ybk.service;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.*;
import com.ybk.entity.MatchA;
import com.ybk.entity.MatchMode;
import com.ybk.result.PageResult;
import com.ybk.vo.LeaderMatchModeVO;

public interface MatchAService {
    void save(MatchADTO matchADTO);

    void update(MatchADTO matchADTO);

    void delete(Integer matchId);

    void setMatchAPlayer(MatchAPlayerDTO matchAPlayerDTO);

    void deleteMatchAPlayer(ClearMatchAPlayerDTO clearMatchAPlayerDTO);

    PageResult getUnStartMatchABrief(VisitorQueryDTO visitorQueryDTO);

    PageResult getDoingMatchABrief(VisitorQueryDTO visitorQueryDTO);

    void endMatchA(Integer matchModeId);

    PageResult getRefereeMatchMode(PageQueryDTO pageQueryDTO);

    void beginMatchA(Integer matchModeId);

    void matchAScore(MatchAScoreDTO scoreDTO);

    void saveMode(MatchAModeDTO matchAModeDTO);

    void deleteMode(Integer matchModeId);

    PageResult queryPage(MatchQueryDTO matchQueryDTO);

    void updateMode(MatchAModeDTO matchAModeDTO);

    PageResult pageMatchAMode(MatchModeQueryDTO matchModeQueryDTO);

    PageResult leaderPageMatchMode(PageQueryDTO pageQueryDTO);

    LeaderMatchModeVO LeaderGetMatchMode(Integer matchModeId);

    PageResult getEndMatchABrief(VisitorQueryDTO visitorQueryDTO);
}
