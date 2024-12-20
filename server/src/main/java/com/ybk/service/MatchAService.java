package com.ybk.service;

import com.ybk.dto.match.MatchADTO;
import com.ybk.dto.match.AssignmentDTO;
import com.ybk.dto.match.RegistrationPageDTO;
import com.ybk.result.PageResult;

public interface MatchAService {
    void save(MatchADTO matchADTO);

    void update(MatchADTO matchADTO);

    void delete(Long matchId);

    PageResult pageTeam(RegistrationPageDTO registrationPageDTO);

    void setMatchAPlayer(AssignmentDTO playerAssignmentDTO);

    void updateMatchAPlayer(AssignmentDTO assignmentDTO);

    void deleteMatchAPlayer(Long assignmentId);
}
