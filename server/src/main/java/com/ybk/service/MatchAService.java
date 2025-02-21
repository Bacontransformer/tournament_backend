package com.ybk.service;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.*;
import com.ybk.entity.MatchA;
import com.ybk.result.PageResult;

public interface MatchAService {
    void save(MatchADTO matchADTO);

    void update(MatchADTO matchADTO);

    void delete(Long matchId);

    void setMatchAPlayer(AssignmentDTO playerAssignmentDTO);

    void updateMatchAPlayer(AssignmentDTO assignmentDTO);

    void deleteMatchAPlayer(Long assignmentId);

    MatchA getDoingMatchADetail(Long matchAId);

    PageResult getUnStartMatchABrief(PageQueryDTO pageQueryDTO);

    PageResult getDoingMatchABrief(PageQueryDTO pageQueryDTO);

    void endMatchA(EndMatchDTO endMatchDTO);

    PageResult getRefereeMatchABrief(PageQueryDTO pageQueryDTO);

    void beginMatchA(BeginMatchDTO beginMatchDTO);

    void matchAScore(MatchAScoreDTO scoreDTO);
}
