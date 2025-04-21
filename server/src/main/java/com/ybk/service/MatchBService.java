package com.ybk.service;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.*;
import com.ybk.entity.MatchB;
import com.ybk.result.PageResult;
import com.ybk.vo.LeaderMatchBVO;

public interface MatchBService {
    void save(MatchBDTO matchBDTO);

    void update(MatchBDTO matchBDTO);

    void delete(Integer matchId);

    void setMatchBPlayer(MatchBPlayerDTO matchBPlayerDTO);

    PageResult getUnStartMatchBBrief(VisitorQueryDTO visitorQueryDTO);

    PageResult getDoingMatchBBrief(VisitorQueryDTO visitorQueryDTO);

    void endMatchB(Integer matchBId);

    void beginMatchB(Integer matchBId);

    PageResult getRefereeMatchB(PageQueryDTO pageQueryDTO);

    void matchBScore(MatchBScoreDTO scoreDTO);

    PageResult queryPage(MatchQueryDTO matchQueryDTO);

    PageResult leaderPageMatchB(PageQueryDTO pageQueryDTO);

    void deleteMatchBPlayer(ClearMatchBPlayerDTO clearMatchBPlayerDTO);

    LeaderMatchBVO leaderGetMatchB(Integer matchBId);

    PageResult getEndMatchBBrief(VisitorQueryDTO visitorQueryDTO);
}
