package com.ybk.service;

import com.ybk.dto.PageQueryDTO;
import com.ybk.dto.match.MatchADTO;
import com.ybk.dto.match.AssignmentDTO;
import com.ybk.dto.match.RegistrationPageDTO;
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
}
